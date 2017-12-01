package com.example.darre.cis357_project.model.event_registry;

import com.squareup.moshi.Json;

public class Location {

    @Json(name = "type")
    private String type;
    @Json(name = "label")
    private Label label;
    @Json(name = "country")
    private Country country;

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}