package pe.edu.pucp.perugopf.data.repository.impl;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import pe.edu.pucp.perugopf.data.entities.Comment;
import pe.edu.pucp.perugopf.data.entities.Event;
import pe.edu.pucp.perugopf.data.entities.EventDetail;
import pe.edu.pucp.perugopf.data.network.JsonPlaceHolderApi;
import pe.edu.pucp.perugopf.data.repository.IEventRepository;

public class EventRepositoryImpl implements IEventRepository {

    private final JsonPlaceHolderApi jsonPlaceHolderApi;

    @Inject
    public EventRepositoryImpl(JsonPlaceHolderApi jsonPlaceHolderApi) {
        this.jsonPlaceHolderApi = jsonPlaceHolderApi;
    }

    @Override
    public Observable<List<Event>> getEvents() {
        return jsonPlaceHolderApi.getEventsRx();
    }

    @Override
    public Observable<EventDetail> getEventDetail(int eventId) {
        return Observable.zip(jsonPlaceHolderApi.getEvent(eventId), jsonPlaceHolderApi.getComments(eventId),
                new BiFunction<Event, List<Comment>, EventDetail>() {
                    @Override
                    public EventDetail apply(Event event, List<Comment> comments) throws Exception {
                        return new EventDetail(event, comments);
                    }
                }
        );
    }

}
