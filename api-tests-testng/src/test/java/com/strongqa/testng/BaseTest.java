package com.strongqa.testng;

import com.google.gson.Gson;
import com.strongqa.api.client.ApiLibrary;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public abstract class BaseTest {
  public static final Gson GSON = ApiLibrary.GSON;

  public static final RequestSpecification BASE_SPEC = new RequestSpecBuilder()
      .setBaseUri(ApiLibrary.BASE_URL)
      .addHeader(ApiLibrary.API_AUTH_HEADER.getName(),
          ApiLibrary.API_AUTH_HEADER.getValue())
      .addHeader("Accept", "application/json")
      .setContentType("application/json")
      .build();

}
