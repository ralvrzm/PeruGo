package pe.edu.pucp.perugopf.domain.event_detail_interactor;

import io.reactivex.Observable;
import pe.edu.pucp.perugopf.data.entities.EventDetail;

public interface IEventDetailInteractor {
    Observable<EventDetail> getEventDetail(int eventId);
}
