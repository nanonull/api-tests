package com.strongqa.gatling

import com.strongqa.api.client.ApiLibrary
import io.gatling.core.Predef._
import io.gatling.http.Predef.http

class GetCategoriesSimulation extends Simulation {
  val api = new ApiLibrary()
  
  setUp(scenario(this.getClass.getSimpleName)
      .exec(
        repeat(5, "n") {
          exec(SessionFunctionBuilder(this.getClass.getSimpleName, session => {
            val categories = api.getCategories
            assert(categories.size() > 0, "Empty categories")
            session
          })).exitHereIfFailed
        }
      ).inject(atOnceUsers(1))
      .protocols(http.baseUrl(ApiLibrary.BASE_URL)))
}
