package com.example.darre.cis357_project.model.event_registry;

import com.squareup.moshi.Json;

public class EventResponse {

    @Json(name = "events")
    private Events events;

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

}