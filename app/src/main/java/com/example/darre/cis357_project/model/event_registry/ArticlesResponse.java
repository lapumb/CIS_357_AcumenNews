package com.example.darre.cis357_project.model.event_registry;

import com.squareup.moshi.Json;

public class ArticlesResponse {

    @Json(name = "articles")
    private Articles result;

    public Articles getResult() {
        return result;
    }

    public void setResult(Articles result) {
        this.result = result;
    }

}