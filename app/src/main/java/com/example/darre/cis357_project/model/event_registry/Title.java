package com.example.darre.cis357_project.model.event_registry;

import com.squareup.moshi.Json;

public class Title {

    @Json(name = "eng")
    private String eng;

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

}