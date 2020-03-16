package com.strongqa.spock

import org.apache.http.HttpResponse
import org.apache.http.util.EntityUtils

trait ApacheHttpExt {
    String getBody(HttpResponse response) {
        EntityUtils.toString(response.entity)
    }
}