package pe.edu.pucp.perugopf.presentation.activities.main;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import pe.edu.pucp.perugopf.data.entities.Event;
import pe.edu.pucp.perugopf.domain.main_interactor.IMainInteractor;

public class MainPresenter implements IMainContract.IPresenter {
    IMainContract.IView view;
    Disposable disposable;

    @Inject
    protected IMainInteractor mainInteractor;

    @Inject
    public MainPresenter() {
    }

    @Override
    public void attachView(IMainContract.IView view) {
        this.view = view;
    }
    @Override
    public void detachView() {
        view = null;
        if(disposable != null) {
            disposable.dispose();
        }
    }
    @Override
    public boolean isViewAttached() {
        return view != null;
    }
    @Override
    public void getAllEvent() {
        view.showProgressBar();
        mainInteractor.getAllEvent()
                .subscribe(new Observer<List<Event>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }
                    @Override
                    public void onNext(List<Event> events) {
                        if(isViewAttached()) {
                            view.getAllEventSuccess(events);
                            view.hideProgressBar();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if(isViewAttached()) {
                            view.showError(e.getMessage());
                            view.hideProgressBar();
                        }
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }
}
