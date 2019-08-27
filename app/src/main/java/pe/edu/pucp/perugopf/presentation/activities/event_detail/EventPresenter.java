package pe.edu.pucp.perugopf.presentation.activities.event_detail;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import pe.edu.pucp.perugopf.data.entities.EventDetail;
import pe.edu.pucp.perugopf.domain.event_detail_interactor.IEventDetailInteractor;

public class EventPresenter implements
        IEventDetailContract.IPresenter {

    IEventDetailContract.IView view;
    private final IEventDetailInteractor interactor;
    private Disposable disposable;

    @Inject
    public EventPresenter(IEventDetailInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void attachView(IEventDetailContract.IView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        if(disposable != null){
            disposable.dispose();
        }
    }

    @Override
    public boolean isViewAttached() {
        return this.view != null;
    }

    @Override
    public void getEvent(int eventId) {
        view.showProgressBar();
        interactor.getEventDetail(eventId)
                .subscribe(new Observer<EventDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(EventDetail eventDetail) {
                        if(isViewAttached()) {
                            view.hideProgressBar();
                            view.getEventDetailSuccess(eventDetail);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(isViewAttached()) {
                            view.hideProgressBar();
                            view.showError(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
