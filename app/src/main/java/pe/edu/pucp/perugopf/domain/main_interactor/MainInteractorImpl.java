package pe.edu.pucp.perugopf.domain.main_interactor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import pe.edu.pucp.perugopf.data.entities.Event;
import pe.edu.pucp.perugopf.data.repository.IEventRepository;

public class MainInteractorImpl implements IMainInteractor{

    private final IEventRepository eventRepository;
    private final Scheduler uiThread;
    private final Scheduler executorThread;

    @Inject
    public MainInteractorImpl(IEventRepository eventRepository, Scheduler uiThread, Scheduler executorThread) {
        this.eventRepository = eventRepository;
        this.uiThread = uiThread;
        this.executorThread = executorThread;
    }

    @Override
    public Observable<List<Event>> getAllEvent() {
        return eventRepository.getEvents()
                .observeOn(uiThread)
                .subscribeOn(executorThread);
    }
}
