package pe.edu.pucp.perugopf.domain.event_detail_firestore_interactor;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;

public interface IEventDetailFirestoreInteractor {
    void getEvent(String uid, OnCompleteListener<DocumentSnapshot> onComplete);
}
