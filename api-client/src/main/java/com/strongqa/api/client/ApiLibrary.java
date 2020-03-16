package com.strongqa.api.client;

import com.google.gson.reflect.TypeToken;
import com.strongqa.api.client.models.article.Article;
import com.strongqa.api.client.models.article.ArticleCreateRequest;
import com.strongqa.api.client.models.category.Category;
import java.io.IOException;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

public class ApiLibrary {

  public static final String baseUrl = System.getenv("BASE_URL");
  public static final String apiToken = System.getenv("API_TOKEN");
  public static final Header apiAuthHeader = new BasicHeader("Authorization",
      "Token token=" + apiToken);

  public Request buildGetArticles() {
    return Request.Get(baseUrl + "/api/v1/articles")
        .addHeader("Content-Type", "application/json")
        .addHeader(apiAuthHeader);
  }

  public List<Article> getArticles() throws IOException {
    HttpResponse response = buildGetArticles().execute().returnResponse();
    return Utils.GSON.fromJson(EntityUtils.toString(response.getEntity()),
        new TypeToken<List<Article>>() {
        }.getType());
  }

  public Request buildPostArticle(ArticleCreateRequest request) throws IOException {
    return Request.Post(baseUrl + "/api/v1/articles")
        .addHeader("Content-Type", "application/json")
        .addHeader(apiAuthHeader)
        .body(new StringEntity(Utils.GSON.toJson(request)));
  }

  public Article postArticle(ArticleCreateRequest request) throws IOException {
    HttpResponse response = buildPostArticle(request).execute().returnResponse();
    return Utils.GSON.fromJson(EntityUtils.toString(response.getEntity()), Article.class);
  }

  public List<Category> getCategories() throws IOException {
    HttpResponse response = Request.Get(baseUrl + "/api/v1/categories")
        .addHeader("Content-Type", "application/json")
        .addHeader(apiAuthHeader)
        .execute().returnResponse();

    return Utils.GSON.fromJson(EntityUtils.toString(response.getEntity()),
        new TypeToken<List<Category>>() {
        }.getType());
  }

}
