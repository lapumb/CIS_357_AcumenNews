package com.example.darre.cis357_project.model.event_registry;

import com.squareup.moshi.Json;

public class Concept {

    @Json(name = "uri")
    private String uri;
    @Json(name = "id")
    private String id;
    @Json(name = "type")
    private String type;
    @Json(name = "score")
    private Integer score;
    @Json(name = "label")
    private Label label;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

}