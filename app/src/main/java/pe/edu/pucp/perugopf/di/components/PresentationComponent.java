package pe.edu.pucp.perugopf.di.components;

import dagger.Component;
import pe.edu.pucp.perugopf.presentation.activities.main.MainActivity;
import pe.edu.pucp.perugopf.di.modules.PresentationModule;
import pe.edu.pucp.perugopf.di.scope.PerActivity;

@PerActivity
@Component(modules = PresentationModule.class, dependencies = ApplicationComponent.class)
public interface PresentationComponent {
    void inject(MainActivity mainActivity);
    void inject(EventDetailActivity eventDetailActivity);
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
    void inject(EventsFirestoreActivity eventsFirestoreActivity);
    void inject(CreateEventFirestoreActivity createEventFirestoreActivity);
    void inject(EventDetailFirestoreActivity eventDetailFirestoreActivity);
    void inject(MenuActivity menuActivity);
}
