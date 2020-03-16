package com.strongqa.spock.specs.rest

import com.strongqa.api.client.ApiLibrary
import com.strongqa.api.client.models.article.ArticleCreateRequest
import com.strongqa.spock.BaseSpecification
import spock.lang.Shared
import spock.lang.Unroll

class ArticlesSpec extends BaseSpecification {

  @Shared
  def api = new ApiLibrary()
  @Shared
  static ArticleCreateRequest articleExists
  @Shared
  static ArticleCreateRequest articleWithShortTitle

  def setupSpec() {
    def categories = api.getCategories()
    def articles = api.getArticles()

    articleExists = new ArticleCreateRequest()
    articleExists.title = articles[0].title
    articleExists.text = ""
    articleExists.categoryId = categories.first().id

    articleWithShortTitle = new ArticleCreateRequest()
    articleWithShortTitle.title = "abcd"
    articleWithShortTitle.text = ""
    articleWithShortTitle.categoryId = categories.first().id
  }

  @Unroll
  def "POST article with non-success response"() {
    when:
    def response = api.buildPostArticle(articleRequest as ArticleCreateRequest)
            .execute().returnResponse()

    then:
    response.statusLine.getReasonPhrase() == expectStatus
    response.statusLine.statusCode == expectStatusCode


    where:
    articleRequest        || expectStatus                             | expectStatusCode
    articleWithShortTitle || "Title too short"                        | 422
    articleExists         || "Article with such title already exists" | 422
  }

}