package pe.edu.pucp.perugopf.presentation.activities.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.R;
import pe.edu.pucp.perugopf.base.BaseActivity;
import pe.edu.pucp.perugopf.di.components.DaggerPresentationComponent;
import pe.edu.pucp.perugopf.di.modules.PresentationModule;
import pe.edu.pucp.perugopf.presentation.activities.events_firestore.EventsFirestoreActivity;
import pe.edu.pucp.perugopf.presentation.activities.main.MainActivity;
import pe.edu.pucp.perugopf.presentation.activities.rx_basic.BasicRxActivity;

public class MenuActivity extends BaseActivity {

    Button rxJavaBasicButton;
    Button retrofitButton;
    Button firestoreButton;

    @Inject
    FirebaseRemoteConfig remoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_menu;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        rxJavaBasicButton = findViewById(R.id.btn_rxbasic);
        retrofitButton = findViewById(R.id.btn_retrofit);
        firestoreButton = findViewById(R.id.btn_firebase);
        setListeners();
        setRemoteConfig();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerPresentationComponent.builder()
                .applicationComponent(getApplicationComponent())
                .presentationModule(new PresentationModule())
                .build().inject(this);
    }

    private void setListeners(){
        rxJavaBasicButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BasicRxActivity.class);
            startActivity(intent);
        });
        retrofitButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
        firestoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EventsFirestoreActivity.class);
            startActivity(intent);
        });
    }

    private void setRemoteConfig(){
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(120)
                .build();
        remoteConfig.setConfigSettingsAsync(configSettings);
        remoteConfig.setDefaults(R.xml.remote_config);
        remoteConfig.fetchAndActivate().addOnCompleteListener(task ->{
            if(task.isSuccessful()){
                updateToolBarTitle();
            }
        });
        updateToolBarTitle();
    }

    private void updateToolBarTitle(){
        setTitle(remoteConfig.getString("title"));
    }
}
