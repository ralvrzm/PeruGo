package pe.edu.pucp.perugopf.domain.event_detail_firestore_interactor;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import pe.edu.pucp.perugopf.data.entities.NewEvent;

public interface IEventDetailFirestoreInteractor {
    void getEvent(String uid, OnCompleteListener<DocumentSnapshot> onComplete);

    void updateEvent(NewEvent event, OnCompleteListener<DocumentReference> onComplete);
}
