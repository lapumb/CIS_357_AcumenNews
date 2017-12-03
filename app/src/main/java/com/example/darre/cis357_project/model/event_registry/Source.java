package com.example.darre.cis357_project.model.event_registry;

import com.squareup.moshi.Json;

public class Source {

    @Json(name = "id")
    private String id;
    @Json(name = "uri")
    private String uri;
    @Json(name = "title")
    private String title;

    public Source(String id, String uri, String title) {
        this.id = id;
        this.uri = uri;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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