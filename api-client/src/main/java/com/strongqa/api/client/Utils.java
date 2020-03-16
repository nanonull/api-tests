package com.strongqa.api.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;

public class Utils {

  public static final Gson GSON = new GsonBuilder()
      .setPrettyPrinting()
      .create();

}
