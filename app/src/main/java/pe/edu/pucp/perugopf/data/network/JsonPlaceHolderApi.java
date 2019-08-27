package pe.edu.pucp.perugopf.data.network;

import java.util.List;

import io.reactivex.Observable;
import pe.edu.pucp.perugopf.data.entities.Comment;
import pe.edu.pucp.perugopf.data.entities.Event;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
    @GET("events")
    Observable<List<Event>> getEventsRx();

    @GET("events/{id}")
    Observable<Event> getEvent(@Path("id") int eventId);

    @GET("event/{id}/comments")
    Observable<List<Comment>> getComments(@Path("id") int eventId);
}
