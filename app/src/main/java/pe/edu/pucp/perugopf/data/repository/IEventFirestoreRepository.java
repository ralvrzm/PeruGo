package pe.edu.pucp.perugopf.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import pe.edu.pucp.perugopf.data.entities.NewEvent;

public interface IEventFirestoreRepository {
    Query getAllEvents();

    void getEvent(String uid,  OnCompleteListener<DocumentSnapshot> onComplete);

    void createEvent(NewEvent post, OnCompleteListener<DocumentReference> onComplete);
}
