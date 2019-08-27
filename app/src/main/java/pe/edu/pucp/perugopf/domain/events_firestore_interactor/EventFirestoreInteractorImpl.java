package pe.edu.pucp.perugopf.domain.events_firestore_interactor;

import com.google.firebase.firestore.Query;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.data.repository.IEventFirestoreRepository;

public class EventFirestoreInteractorImpl implements IEventsFirestoreInteractor{

    private final IEventFirestoreRepository repository;

    @Inject
    public EventFirestoreInteractorImpl(IEventFirestoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Query getAllEvent() {
        return repository.getAllEvents();
    }
}
