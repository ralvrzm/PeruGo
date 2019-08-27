package pe.edu.pucp.perugopf.presentation.activities.event_detail;

import pe.edu.pucp.perugopf.data.entities.EventDetail;

public interface IEventDetailContract {
    interface IView{
        void showError(String errorMsg);
        void showProgressBar();
        void hideProgressBar();
        void getEventDetailSuccess(EventDetail event);
    }
    interface IPresenter{
        void attachView(IEventDetailContract.IView view);
        void detachView();
        boolean isViewAttached();
        void getEvent(int eventId);
    }
}
