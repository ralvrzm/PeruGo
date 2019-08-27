package pe.edu.pucp.perugopf.domain.event_detail_interactor;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import pe.edu.pucp.perugopf.data.entities.EventDetail;
import pe.edu.pucp.perugopf.data.repository.IEventRepository;

public class EventDetailInteractorImpl implements IEventDetailInteractor{

    private final IEventRepository eventRepository;
    private final Scheduler uiThread;
    private final Scheduler executorThread;

    @Inject
    public EventDetailInteractorImpl(IEventRepository eventRepository, Scheduler uiThread, Scheduler executorThread) {
        this.eventRepository = eventRepository;
        this.uiThread = uiThread;
        this.executorThread = executorThread;
    }

    @Override
    public Observable<EventDetail> getEventDetail(int eventId) {
        return eventRepository.getEventDetail(eventId)
                .observeOn(uiThread)
                .subscribeOn(executorThread);
    }
}
