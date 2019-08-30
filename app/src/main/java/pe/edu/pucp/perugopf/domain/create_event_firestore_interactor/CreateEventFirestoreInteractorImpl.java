package pe.edu.pucp.perugopf.domain.create_event_firestore_interactor;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.data.entities.NewEvent;
import pe.edu.pucp.perugopf.data.repository.IEventFirestoreRepository;

public class CreateEventFirestoreInteractorImpl implements ICreateEventFirestoreInteractor {

    private final IEventFirestoreRepository repository;

    @Inject
    public CreateEventFirestoreInteractorImpl(IEventFirestoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createEvent(NewEvent event, OnCompleteListener<DocumentReference> onComplete) {
        repository.createEvent(event, onComplete);
    }

    @Override
    public void updateEvent(String uid, OnCompleteListener<DocumentReference> onComplete) {
        repository.updateEvent(uid, onComplete);
    }
}