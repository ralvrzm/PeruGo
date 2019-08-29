package pe.edu.pucp.perugopf.di.components;

import dagger.Component;
import pe.edu.pucp.perugopf.di.modules.PresentationModule;
import pe.edu.pucp.perugopf.di.scope.PerActivity;
import pe.edu.pucp.perugopf.presentation.activities.create_event_firestore.CreateEventFirestoreActivity;
import pe.edu.pucp.perugopf.presentation.activities.event_detail.EventDetailActivity;
import pe.edu.pucp.perugopf.presentation.activities.event_detail_firestore.EventDetailFirestoreActivity;
import pe.edu.pucp.perugopf.presentation.activities.events_firestore.EventsFirestoreActivity;
import pe.edu.pucp.perugopf.presentation.activities.login.LoginActivity;
import pe.edu.pucp.perugopf.presentation.activities.main.MainActivity;
import pe.edu.pucp.perugopf.presentation.activities.register.RegisterActivity;

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
    //void inject(AdminEventsFirestoreActivity adminEventsFirestoreActivity);
}
