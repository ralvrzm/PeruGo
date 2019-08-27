package pe.edu.pucp.perugopf;

import android.app.Application;

import pe.edu.pucp.perugopf.di.components.ApplicationComponent;
import pe.edu.pucp.perugopf.di.components.DaggerApplicationComponent;
import pe.edu.pucp.perugopf.di.modules.ApplicationModule;

public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        inicializarApplicationComponent();
    }

    void inicializarApplicationComponent(){
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
