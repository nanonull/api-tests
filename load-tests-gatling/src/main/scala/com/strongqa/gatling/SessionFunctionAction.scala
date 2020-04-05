package com.strongqa.gatling

import io.gatling.commons.stats.{KO, OK}
import io.gatling.commons.util.Clock
import io.gatling.core.action.{Action, ExitableAction}
import io.gatling.core.session.Session
import io.gatling.core.stats.StatsEngine
import org.slf4j.{Logger, LoggerFactory}

final case class SessionFunctionAction(statsEngine: StatsEngine,
                                       clock: Clock,
                                       name: String,
                                       next: Action,
                                       callback: Session => Session) extends ExitableAction {
  
  override def execute(session: Session): Unit = {
    
    RunnableAction.LOG.info(s"Start action: $name")
    val start = System.currentTimeMillis
    
    val (status, finalSession, callbackError, message) = try {
      (OK, Option(callback(session)), None, None)
    } catch {
      case error: Throwable =>
        (KO, Option(session), Some(error), if (error.getMessage == null) Some("undefined") else
          Some(error.getMessage))
    }
    
    if (message.isDefined) {
      RunnableAction.LOG.error(s"Error in action [$name]: " + message.get,
        callbackError.orElse(Some(new UnknownError())));
    }
    
    val end = System.currentTimeMillis
    statsEngine.logResponse(session, name, start, end, status, None, message)
    if (callbackError.nonEmpty) {
      throw callbackError.get
    }
    next ! finalSession.get
  }
}

object RunnableAction {
  val LOG: Logger = LoggerFactory.getLogger(classOf[SessionFunctionAction])
}


