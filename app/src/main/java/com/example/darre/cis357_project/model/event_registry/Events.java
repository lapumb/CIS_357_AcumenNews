package com.example.darre.cis357_project.model.event_registry;

import java.util.List;
import com.squareup.moshi.Json;

public class Events {

    @Json(name = "results")
    private List<Event> results = null;
    @Json(name = "totalResults")
    private Integer totalResults;
    @Json(name = "page")
    private Integer page;
    @Json(name = "count")
    private Integer count;
    @Json(name = "pages")
    private Integer pages;

    public List<Event> getResults() {
        return results;
    }

    public void setResults(List<Event> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

}