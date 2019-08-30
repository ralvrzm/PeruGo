package pe.edu.pucp.perugopf.domain.create_event_firestore_interactor;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;

import pe.edu.pucp.perugopf.data.entities.NewEvent;

public interface ICreateEventFirestoreInteractor {
    void createEvent(NewEvent event, OnCompleteListener<DocumentReference> onComplete);

    void updateEvent(String uid, OnCompleteListener<DocumentReference> onComplete);
}
