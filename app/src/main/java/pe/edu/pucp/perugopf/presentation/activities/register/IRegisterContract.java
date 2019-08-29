package pe.edu.pucp.perugopf.presentation.activities.register;

public interface IRegisterContract {
    interface IView{
        void showError(String errorMsg);
        void showProgressDialog();
        void hideProgressDialog();
        void goToEvents();
    }
    interface IPresenter{
        void attachView(IView view);
        void detachView();
        boolean isViewAttached();
        void register(String username, String password);
    }
}
