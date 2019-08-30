package pe.edu.pucp.perugopf.data.repository.impl;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.data.entities.NewEvent;
import pe.edu.pucp.perugopf.data.repository.IEventFirestoreRepository;
import pe.edu.pucp.perugopf.presentation.utils.FirestoreConstants;

public class EventFirestoreRepositoryImpl implements IEventFirestoreRepository {

    private final FirebaseFirestore firestore;

    @Inject
    public EventFirestoreRepositoryImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Query getAllEvents() {
        return firestore.collection(FirestoreConstants.COLLECTION_EVENTS);
    }

    @Override
    public void getEvent(String uid, OnCompleteListener<DocumentSnapshot> onComplete) {
        firestore.collection(FirestoreConstants.COLLECTION_EVENTS).document(uid).get().addOnCompleteListener(onComplete);
    }


    @Override
    public void createEvent(NewEvent event, OnCompleteListener<DocumentReference> onComplete) {
        firestore.collection(FirestoreConstants.COLLECTION_EVENTS).add(event).addOnCompleteListener(onComplete);
    }

    @Override
    public void updateEvent(NewEvent event, OnCompleteListener<DocumentReference> onComplete) {
        //firestore.collection(FirestoreConstants.COLLECTION_EVENTS).document(event.getId()).update("indAprobado", event.getIndAprobado());
        firestore.collection(FirestoreConstants.COLLECTION_EVENTS).document(event.getId()).update("indAprobado", "-1");
    }

}
