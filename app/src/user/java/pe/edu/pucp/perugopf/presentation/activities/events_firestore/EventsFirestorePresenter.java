package pe.edu.pucp.perugopf.presentation.activities.events_firestore;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.data.entities.NewEvent;
import pe.edu.pucp.perugopf.domain.events_firestore_interactor.IEventsFirestoreInteractor;

public class EventsFirestorePresenter implements IEventsFirestoreContract.IPresenter {

    private IEventsFirestoreContract.IView view;
    private ListenerRegistration registration;
    private List<NewEvent> eventList;
    int index;

    @Inject
    protected IEventsFirestoreInteractor interactor;

    @Inject
    public EventsFirestorePresenter() {
        eventList = new ArrayList<>();
    }

    @Override
    public void attachView(IEventsFirestoreContract.IView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        if(registration != null){
            registration.remove();
        }
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void getAllEvent() {
        if(isViewAttached()) view.showProgressBar();
        registration = interactor.getAllEvent()
                .addSnapshotListener((snapshots, e) ->{
                    view.hideProgressBar();
                    if(e != null){
                        // Ocurrio un error
                        if(isViewAttached()) view.showError(e.getMessage());
                        return;
                    }
                    if(snapshots.size() == 0){
                        view.showNoEvent();
                    } else {
                        view.hideNoEvent();
                    }
                    for(DocumentChange doc: snapshots.getDocumentChanges()){
                        NewEvent event = doc.getDocument().toObject(NewEvent.class);
                        event.setId(doc.getDocument().getId());
                        if (event.getIndAprobado().equals("1")) {//TODO probar si esto está bien
                            switch (doc.getType()) {
                                case ADDED:
                                    eventList.add(event);
                                    if (isViewAttached()) view.refreshRecyclerView(eventList);
                                    break;
                                case REMOVED:
                                    index = getIndexEventList(event);
                                    if (index > -1 && isViewAttached()) {
                                        eventList.remove(index);
                                        view.refreshRecyclerView(eventList);
                                    }
                                    break;
                                case MODIFIED:
                                    index = getIndexEventList(event);
                                    if (index > -1 && isViewAttached()) {
                                        eventList.set(index, event);
                                        view.refreshRecyclerView(eventList);
                                    }
                                    break;
                            }
                        }
                    }
                });
    }

    private int getIndexEventList(NewEvent event){
        int index = 0;
        for(NewEvent p: eventList){
            if(p.getId() == event.getId()){
                return index;
            }
            index++;
        }
        return -1;
    }
}
