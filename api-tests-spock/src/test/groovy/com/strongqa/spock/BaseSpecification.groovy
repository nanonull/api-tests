package com.strongqa.spock

import com.google.gson.Gson
import com.strongqa.api.client.Utils
import spock.lang.Specification

abstract class BaseSpecification extends Specification implements ApacheHttpExt {

    public static final Gson GSON = Utils.GSON
}
