package pe.edu.pucp.perugopf.presentation.activities.event_detail_firestore;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.data.entities.NewEvent;
import pe.edu.pucp.perugopf.domain.event_detail_firestore_interactor.IEventDetailFirestoreInteractor;
import pe.edu.pucp.perugopf.presentation.activities.register.IRegisterContract;

public class EventDetailFirestorePresenter implements IEventDetailFirestoreContract.IPresenter {

    IEventDetailFirestoreContract.IView view;

    private IRegisterContract.IView viewReg;

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
        NewEvent event = new NewEvent();
        event.setId(id);
        event.setIndAprobado(newEstate);

        //TODO revisar esta parte ya que SI actualiza pero luego no muestra la lista de eventos,
        // NO ingresa a hacer lo del TASK, se agregó en el Activity para forzar pero se cae.
        interactor.updateEvent(event, task -> {
            if(isViewAttached()) {
                view.hideProgressDialog();
                if (task.isSuccessful()) {
                    view.onSuccessCreate();
                } else {
                    view.showError(task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void showEvents() {
        //TODO Ver qué está faltando agregar para que funcione.
        viewReg.goToEvents();
    }
}
