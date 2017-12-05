package com.example.darre.cis357_project.model.event_registry;

import com.squareup.moshi.Json;

public class SourceResult {

    @Json(name = "score")
    private Integer score;
    @Json(name = "uri")
    private String uri;
    @Json(name = "title")
    private String title;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}