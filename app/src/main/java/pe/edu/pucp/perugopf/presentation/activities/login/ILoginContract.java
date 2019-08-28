package pe.edu.pucp.perugopf.presentation.activities.login;

import com.google.firebase.auth.AuthCredential;

public interface ILoginContract {

    interface IView{
        void showError(String errorMsg);
        void showProgressDialog();
        void hideProgressDialog();
        void goToEvents();
        void onLoginResult(boolean isSuccess);
        void onUserLogged(boolean isSuccess);
    }
    interface IPresenter{
        void attachView(IView view);
        void detachView();
        boolean isViewAttached();
        void checkUserLogged();
        void login(String username, String password);
        void googleLogin(AuthCredential credential);
    }
}
