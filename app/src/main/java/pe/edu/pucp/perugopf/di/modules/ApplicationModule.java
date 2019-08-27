package pe.edu.pucp.perugopf.di.modules;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pe.edu.pucp.perugopf.data.network.JsonPlaceHolderApi;
import pe.edu.pucp.perugopf.data.repository.IEventFirestoreRepository;
import pe.edu.pucp.perugopf.data.repository.IEventRepository;
import pe.edu.pucp.perugopf.data.repository.impl.EventFirestoreRepositoryImpl;
import pe.edu.pucp.perugopf.data.repository.impl.EventRepositoryImpl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor){
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
    }

    @Singleton
    @Provides
    RxJava2CallAdapterFactory providesRxJava2CallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client, GsonConverterFactory converterFactory, RxJava2CallAdapterFactory adapterFactory){
        String baseUrl = "http://jsonplaceholder.typicode.com";
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .client(client)
                .build();
    }

    @Provides @Named("executor_thread")
    Scheduler provideExecutorThread() {
        return Schedulers.io();
    }

    @Provides @Named("ui_thread")
    Scheduler provideUiThread() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

    @Provides
    JsonPlaceHolderApi provideJsonPlaceHolderApi(Retrofit retrofit){
        return retrofit.create(JsonPlaceHolderApi.class);
    }

    @Provides @Singleton
    IEventRepository providePostRepository(EventRepositoryImpl eventRepositoryImpl){
        return eventRepositoryImpl;
    }

    @Provides @Singleton
    IEventFirestoreRepository provideEventFirestoreRepository(EventFirestoreRepositoryImpl eventFirestoreRepositoryImpl){
        return eventFirestoreRepositoryImpl;
    }

    @Provides @Singleton
    FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    @Provides @Singleton
    FirebaseFirestore provideFirestore(){ return FirebaseFirestore.getInstance(); }

    @Provides @Singleton
    FirebaseStorage provideStorage() { return FirebaseStorage.getInstance(); }

    @Provides @Singleton
    FirebaseRemoteConfig provideRemoteConfig(){
        return FirebaseRemoteConfig.getInstance();
    }
}
