package pe.edu.pucp.perugopf.domain.event_detail_firestore_interactor;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.data.entities.NewEvent;
import pe.edu.pucp.perugopf.data.repository.IEventFirestoreRepository;

public class EventDetailFirestoreInteractorImpl implements IEventDetailFirestoreInteractor {

    private final IEventFirestoreRepository repository;

    @Inject
    public EventDetailFirestoreInteractorImpl(IEventFirestoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getEvent(String uid, OnCompleteListener<DocumentSnapshot> onComplete) {
        repository.getEvent(uid, onComplete);
    }

    @Override
    public void updateEvent(NewEvent event, OnCompleteListener<DocumentReference> onComplete) {
        repository.updateEvent(event, onComplete);
    }
}