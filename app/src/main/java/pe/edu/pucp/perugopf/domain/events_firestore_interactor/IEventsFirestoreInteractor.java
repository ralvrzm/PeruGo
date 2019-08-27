package pe.edu.pucp.perugopf.domain.events_firestore_interactor;

import com.google.firebase.firestore.Query;

public interface IEventsFirestoreInteractor {
    Query getAllEvent();
}
