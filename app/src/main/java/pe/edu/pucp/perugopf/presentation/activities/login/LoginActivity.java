package pe.edu.pucp.perugopf.presentation.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.common.SignInButton;

import javax.inject.Inject;

import pe.edu.pucp.perugopf.R;
import pe.edu.pucp.perugopf.base.BaseActivity;
import pe.edu.pucp.perugopf.di.components.DaggerPresentationComponent;
import pe.edu.pucp.perugopf.di.modules.PresentationModule;
import pe.edu.pucp.perugopf.presentation.activities.menu.MenuActivity;
import pe.edu.pucp.perugopf.presentation.activities.register.RegisterActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends BaseActivity implements ILoginContract.IView{

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    SignInButton _googleButton;
    private static final int RC_SIGN_IN = 5000;

    private GoogleSignInClient googleSignInClient;
    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LoginTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        presenter.attachView(this);
        presenter.checkUserLogged();
        getWindow().setBackgroundDrawable(null);
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.btn_register);
        emailEditText = findViewById(R.id.et_username);
        passwordEditText = findViewById(R.id.et_password);
        _googleButton = (SignInButton)findViewById(R.id.google_button);
        _googleConfiguration();
        setListeners();
    }

    private void _googleConfiguration(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerPresentationComponent.builder()
                .applicationComponent(getApplicationComponent())
                .presentationModule(new PresentationModule())
                .build().inject(this);
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        showDialog("Cargando");
    }

    @Override
    public void hideProgressDialog() {
        hideDialog();
    }

    @Override
    public void goToMenu() {
        Intent intent = new Intent(getApplicationContext() , MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onLoginResult(boolean isSuccess) {
        if(isSuccess){
            /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);*/
            Intent intent = new Intent(getApplicationContext() , MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(LoginActivity.this,"ocurrio un error",Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public void onUserLogged(boolean isSuccess) {
        if(isSuccess){
            /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);*/
            Intent intent = new Intent(getApplicationContext() , MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onDetachedFromWindow() {
        presenter.detachView();
        super.onDetachedFromWindow();
    }

    private void setListeners(){
        loginButton.setOnClickListener(v -> {
            String username = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            presenter.login(username,password);
        });
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
        _googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _signIn();
            }
        });
    }

    private void _signIn(){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                if(account != null){
                    _authWithGoogleAccount(account);
                }
            }
        }
    }

    private void _authWithGoogleAccount (final GoogleSignInAccount account){
        final AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null );
        presenter.googleLogin(authCredential);
    }
}
