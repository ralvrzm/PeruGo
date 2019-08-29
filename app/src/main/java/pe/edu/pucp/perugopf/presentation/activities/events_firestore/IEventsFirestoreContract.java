package pe.edu.pucp.perugopf.presentation.activities.events_firestore;

import java.util.List;

import pe.edu.pucp.perugopf.data.entities.NewEvent;

public interface IEventsFirestoreContract {
    interface IView{
        void showError(String errorMsg);
        void showProgressBar();
        void hideProgressBar();
        void showNoEvent();
        void hideNoEvent();
        void refreshRecyclerView(List<NewEvent> eventList);
        void gotToDetailEvent(String eventUid);
    }
    interface IPresenter{
        void attachView(IView view);
        void detachView();
        boolean isViewAttached();
        void getAllEvent();
    }
}
