package com.strongqa.testng.rest;

import com.strongqa.api.client.ApiLibrary;
import com.strongqa.api.client.models.article.Article;
import com.strongqa.api.client.models.article.ArticleCreateRequest;
import com.strongqa.api.client.models.category.Category;
import com.strongqa.testng.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringEndsWith;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ArticlesTest extends BaseTest {
  ApiLibrary api = new ApiLibrary();
  private ArticleCreateRequest articleExists;
  private ArticleCreateRequest articleWithShortTitle;

  @BeforeClass(alwaysRun = true)
  void setupClass() throws IOException {
    List<Category> categories = api.getCategories();
    List<Article> articles = api.getArticles();

    articleExists = new ArticleCreateRequest();
    articleExists.setTitle(articles.get(0).getTitle());
    articleExists.setText("");
    articleExists.setCategoryId(categories.get(0).getId());

    articleWithShortTitle = new ArticleCreateRequest();
    articleWithShortTitle.setTitle("abcd");
    articleWithShortTitle.setText("");
    articleWithShortTitle.setCategoryId(categories.get(0).getId());
  }

  @DataProvider(name = "articles")
  public Object[][] data() {
    Object[][] rows = new Object[2][];
    rows[0] = new Object[]{articleWithShortTitle, "Title too short", 422};
    rows[1] = new Object[]{articleExists, "Article with such title already exists", 422};
    return rows;
  }

  @Test(dataProvider = "articles")
  public void postArticleWithNonSuccessResponse(ArticleCreateRequest request,
      String expectStatus,
      int expectStatusCode)  {

    ValidatableResponse response = RestAssured.given().spec(BASE_SPEC)
        .with().body(GSON.toJson(request))
        .post("/api/v1/articles")

        .then().assertThat()

        .statusLine(CoreMatchers.endsWith(expectStatus))
        .statusCode(expectStatusCode);
  }


}
