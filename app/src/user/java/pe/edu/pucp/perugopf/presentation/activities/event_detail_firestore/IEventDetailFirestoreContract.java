package pe.edu.pucp.perugopf.presentation.activities.event_detail_firestore;

import pe.edu.pucp.perugopf.data.entities.NewEvent;

public interface IEventDetailFirestoreContract {
    interface IView{
        void showError(String errorMsg);
        void showProgressBar();
        void hideProgressBar();
        void getEventDetailSuccess(NewEvent event);
    }
    interface IPresenter{
        void attachView(IView view);
        void detachView();
        boolean isViewAttached();
        void getEvent(String event_id);
    }
}
