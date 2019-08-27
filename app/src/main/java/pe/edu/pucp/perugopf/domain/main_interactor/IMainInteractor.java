package pe.edu.pucp.perugopf.domain.main_interactor;

import java.util.List;

import io.reactivex.Observable;
import pe.edu.pucp.perugopf.data.entities.Event;

public interface IMainInteractor {
    Observable<List<Event>> getAllEvent();
}
