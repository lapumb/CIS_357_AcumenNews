package com.example.darre.cis357_project.model.event_registry;

import com.squareup.moshi.Json;

public class Article {

    @Json(name = "id")
    private String id;
    @Json(name = "uri")
    private String uri;
    @Json(name = "lang")
    private String lang;
    @Json(name = "isDuplicate")
    private Boolean isDuplicate;
    @Json(name = "date")
    private String date;
    @Json(name = "time")
    private String time;
    @Json(name = "dateTime")
    private String dateTime;
    @Json(name = "sim")
    private Double sim;
    @Json(name = "url")
    private String url;
    @Json(name = "title")
    private String title;
    @Json(name = "body")
    private String body;
    @Json(name = "source")
    private Source source;
    @Json(name = "image")
    private String image;
    @Json(name = "eventUri")
    private String eventUri;
    @Json(name = "wgt")
    private Integer wgt;

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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean getIsDuplicate() {
        return isDuplicate;
    }

    public void setIsDuplicate(Boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Double getSim() {
        return sim;
    }

    public void setSim(Double sim) {
        this.sim = sim;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEventUri() {
        return eventUri;
    }

    public void setEventUri(String eventUri) {
        this.eventUri = eventUri;
    }

    public Integer getWgt() {
        return wgt;
    }

    public void setWgt(Integer wgt) {
        this.wgt = wgt;
    }

}