package com.example.darre.cis357_project.model.event_registry;

import com.squareup.moshi.Json;

public class Category {

    @Json(name = "uri")
    private String uri;
    @Json(name = "id")
    private Integer id;
    @Json(name = "label")
    private String label;
    @Json(name = "wgt")
    private Integer wgt;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getWgt() {
        return wgt;
    }

    public void setWgt(Integer wgt) {
        this.wgt = wgt;
    }

}