package com.example.darre.cis357_project.model.event_registry;

import com.squareup.moshi.Json;

public class ArticleCounts {

    @Json(name = "eng")
    private Integer eng;

    public Integer getEng() {
        return eng;
    }

    public void setEng(Integer eng) {
        this.eng = eng;
    }

}