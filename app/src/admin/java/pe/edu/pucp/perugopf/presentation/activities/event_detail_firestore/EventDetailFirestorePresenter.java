package pe.edu.pucp.perugopf.presentation.activities.event_detail_firestore;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.data.entities.NewEvent;
import pe.edu.pucp.perugopf.domain.event_detail_firestore_interactor.IEventDetailFirestoreInteractor;

public class EventDetailFirestorePresenter implements IEventDetailFirestoreContract.IPresenter {

    IEventDetailFirestoreContract.IView view;

    @Inject
    IEventDetailFirestoreInteractor interactor;

    @Inject
    public EventDetailFirestorePresenter() {
    }

    @Override
    public void attachView(IEventDetailFirestoreContract.IView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void getEvent(String id) {
        if(id == null || id.isEmpty()){
            view.showError("No hay id de Event");
            return;
        }
        view.showProgressBar();
        interactor.getEvent(id, task -> {
            if(isViewAttached()) {
                view.hideProgressBar();
                if (task.isSuccessful()) {
                    NewEvent event = task.getResult().toObject(NewEvent.class);
                    view.getEventDetailSuccess(event);

                } else {
                    view.showError(task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void updateEvent(String id, String newEstate) {
        if(id == null || id.isEmpty()){
            view.showError("No hay id de Event");
            return;
        }
        view.showProgressBar();
        interactor.getEvent(id, task -> {
            if(isViewAttached()) {
                view.hideProgressBar();
                if (task.isSuccessful()) {
                    NewEvent event = task.getResult().toObject(NewEvent.class);
                    event.setIndAprobado(newEstate);
                    view.getEventDetailSuccess(event);//TODO reemplazar para que llame la lista de eventos
                } else {
                    view.showError(task.getException().getMessage());
                }
            }
        });
    }
}
