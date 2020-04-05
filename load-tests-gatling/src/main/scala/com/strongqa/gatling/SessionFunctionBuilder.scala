package com.strongqa.gatling

import io.gatling.core.Predef.Session
import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.structure.ScenarioContext
import io.gatling.core.util.NameGen

/**
 * Execute callback with access to gatling.session and with entry in gatling report.
 * You may want use gatling.exec(sessionFunction) if you don't need entry in gatling report:
 * <br>exec(session => {
 * <br>  // custom code
 * <br>  session
 * <br>})
 * */
final case class SessionFunctionBuilder(actionName: String, callback: Session => Session)
  extends ActionBuilder() with NameGen {
  override def build(ctx: ScenarioContext, next: Action): Action = {
    SessionFunctionAction(ctx.coreComponents.statsEngine,
      ctx.coreComponents.clock,
      actionName,
      next,
      callback)
  }
}
