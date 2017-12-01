package com.example.darre.cis357_project.model.event_registry;

import com.squareup.moshi.Json;

public class Country {

    @Json(name = "type")
    private String type;
    @Json(name = "label")
    private Label label;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

}