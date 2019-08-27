package pe.edu.pucp.perugopf.presentation.activities.main;

import java.util.List;

import pe.edu.pucp.perugopf.data.entities.Event;

public interface IMainContract {
    interface IView{
        void showError(String errorMsg);
        void showProgressBar();
        void hideProgressBar();
        void getAllEventSuccess(List<Event> eventList);
        void gotToDetailEvent(int eventId);
    }
    interface IPresenter{
        void attachView(IView view);
        void detachView();
        boolean isViewAttached();
        void getAllEvent();
    }
}
