package pe.edu.pucp.perugopf.presentation.activities.create_event_firestore;

public interface ICreateEventFirestoreContract {
    interface IView {
        void showError(String errorMsg);
        void showProgressDialog();
        void hideProgressDialog();
        void onSuccessCreate();
    }

    interface IPresenter {
        void attachView(IView view);
        void detachView();
        boolean isViewAttached();
        void createEvent(String title, String content, String path, String direccion, String fecha, String latitud, String longitud);
    }
}
