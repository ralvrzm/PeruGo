package pe.edu.pucp.perugopf.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import pe.edu.pucp.perugopf.data.repository.IEventFirestoreRepository;
import pe.edu.pucp.perugopf.data.repository.IEventRepository;
import pe.edu.pucp.perugopf.di.scope.PerActivity;

@Module
public class PresentationModule {
    @PerActivity
    @Provides
    IMainInteractor provideMainInteractor(IEventRepository repository,
                                          @Named("ui_thread") Scheduler uiThread,
                                          @Named("executor_thread") Scheduler executorThread){
        return new MainInteractorImpl(repository, uiThread, executorThread);
    }

    @PerActivity
    @Provides
    IEventDetailInteractor provideEventDetailInteractor(IEventRepository repository,
                                                        @Named("ui_thread") Scheduler uiThread,
                                                        @Named("executor_thread") Scheduler executorThread){
        return new EventDetailInteractorImpl(repository,uiThread,executorThread);
    }

    @PerActivity
    @Provides
    IEventsFirestoreInteractor provideEventsFirestoreInteractor(IEventFirestoreRepository repository){
        return new EventFirestoreInteractorImpl(repository);
    }

    @PerActivity
    @Provides
    ICreateEventFirestoreInteractor provideCreateEventFirestoreInteractor(IEventFirestoreRepository repository){
        return new CreateEventFirestoreInteractorImpl(repository);
    }

    @PerActivity
    @Provides
    IEventDetailFirestoreInteractor provideEventDetailFirestoreInteractor(IEventFirestoreRepository repository){
        return new EventDetailFirestoreInteractorImpl(repository);
    }
}
