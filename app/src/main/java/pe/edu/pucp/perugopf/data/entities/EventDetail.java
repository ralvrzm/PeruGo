package pe.edu.pucp.perugopf.data.entities;

import java.util.List;

public class EventDetail {
    public Event event;
    public List<Comment> comments;

    public EventDetail(Event event, List<Comment> comments) {
        this.event = event;
        this.comments = comments;
    }
}
