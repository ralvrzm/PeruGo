package pe.edu.pucp.perugopf.di.components;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;
import pe.edu.pucp.perugopf.data.network.JsonPlaceHolderApi;
import pe.edu.pucp.perugopf.data.repository.IEventFirestoreRepository;
import pe.edu.pucp.perugopf.data.repository.IEventRepository;
import pe.edu.pucp.perugopf.di.modules.ApplicationModule;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Retrofit exposeRetrofit();
    Context exposeContext();
    IEventRepository exposeEventRepository();
    IEventFirestoreRepository exposeEventFirestoreRepository();
    JsonPlaceHolderApi exposeJsonPlaceHolderApi();
    FirebaseAuth exposeFirebaseAuth();
    FirebaseFirestore exposeFirestore();
    FirebaseStorage exposeStorage();
    FirebaseRemoteConfig exposeRemoteConfig();

    @Named("ui_thread") Scheduler uiThread();
    @Named("executor_thread") Scheduler executorThread();
}
