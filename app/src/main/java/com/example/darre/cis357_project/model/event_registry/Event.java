package com.example.darre.cis357_project.model.event_registry;

import java.util.List;
import com.squareup.moshi.Json;

public class Event {

    @Json(name = "id")
    private Integer id;
    @Json(name = "uri")
    private String uri;
    @Json(name = "concepts")
    private List<Concept> concepts = null;
    @Json(name = "eventDate")
    private String eventDate;
    @Json(name = "title")
    private Title title;
    @Json(name = "summary")
    private Summary summary;
    @Json(name = "location")
    private Location location;
    @Json(name = "categories")
    private List<Category> categories = null;
    @Json(name = "images")
    private List<String> images = null;
    @Json(name = "totalArticleCount")
    private Integer totalArticleCount;
    @Json(name = "articleCounts")
    private ArticleCounts articleCounts;
    @Json(name = "wgt")
    private Integer wgt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getTotalArticleCount() {
        return totalArticleCount;
    }

    public void setTotalArticleCount(Integer totalArticleCount) {
        this.totalArticleCount = totalArticleCount;
    }

    public ArticleCounts getArticleCounts() {
        return articleCounts;
    }

    public void setArticleCounts(ArticleCounts articleCounts) {
        this.articleCounts = articleCounts;
    }

    public Integer getWgt() {
        return wgt;
    }

    public void setWgt(Integer wgt) {
        this.wgt = wgt;
    }

}