package pe.edu.pucp.perugopf.data.repository;

import java.util.List;

import io.reactivex.Observable;
import pe.edu.pucp.perugopf.data.entities.Event;
import pe.edu.pucp.perugopf.data.entities.EventDetail;

public interface IEventRepository {
    Observable<List<Event>> getEvents();

    Observable<EventDetail> getEventDetail(int eventId);

    Observable<List<Event>> getEventsToApprove();
}
