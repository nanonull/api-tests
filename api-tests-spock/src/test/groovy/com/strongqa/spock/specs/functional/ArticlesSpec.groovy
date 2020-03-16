package com.strongqa.spock.specs.functional

import com.strongqa.api.client.ApiLibrary
import com.strongqa.api.client.Utils
import com.strongqa.api.client.models.article.Article
import com.strongqa.api.client.models.article.ArticleCreateRequest
import com.strongqa.spock.BaseSpecification
import spock.lang.Shared
import spock.lang.Stepwise

@Stepwise // execute test methods with test dependency
class ArticlesSpec extends BaseSpecification {

  def api = new ApiLibrary()

  @Shared
  Article createdArticle

  def "create article"() {
    given:
    def categories = api.getCategories()
    def request = new ArticleCreateRequest()
    request.title = "Article-${System.currentTimeMillis()}"
    request.text = "description-${System.nanoTime()}"
    request.categoryId = categories.first().id

    when:
    createdArticle = api.postArticle(request)

    then:
    createdArticle
    createdArticle.title == request.title
    createdArticle.text == request.text
  }

  def "find created article"() {
    when:
    def articlesResponse = api.getArticles()
    def foundArticle = articlesResponse.find {
      it.id == createdArticle.id }

    then:
    foundArticle
    createdArticle.title == foundArticle.title
    createdArticle.text == foundArticle.text
  }

}