package com.strongqa.gatling

import com.strongqa.api.client.ApiLibrary
import com.strongqa.api.client.models.article.CreateArticleRequest
import io.gatling.core.Predef._
import io.gatling.http.Predef.http

class CreateArticleSimulation extends Simulation {
  val api = new ApiLibrary()
  val categories = api.getCategories
  
  setUp(scenario(this.getClass.getSimpleName)
      .exec(
        repeat(5, "n") {
          exec(SessionFunctionBuilder(this.getClass.getSimpleName, session => {
            val request = new CreateArticleRequest()
            request.setTitle(s"Article-load-${System.nanoTime()}")
            request.setText(s"description-${System.nanoTime()}")
            request.setCategoryId(categories.get(0).getId)
            val createdArticle = api.postArticle(request)
            assert(createdArticle.getTitle == request.getTitle, "Title mismatch")
            session
          })).exitHereIfFailed
        }
      ).inject(atOnceUsers(1))
      .protocols(http.baseUrl(ApiLibrary.BASE_URL)))
}
