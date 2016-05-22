package pucsp.locar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Inicial extends AppCompatActivity {

    private LoginButton bt_facebook;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_inicial);

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                if (newProfile != null) {
                    SalvarSharedPreferences.setUserName(Inicial.this, newProfile.getName(), newProfile.getId(), "facebook");
                    Intent i = new Intent(Inicial.this, Principal.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        profileTracker.startTracking();
    }

    @Override
    protected void onResume() {
        super.onResume();

        callbackManager = CallbackManager.Factory.create();
        bt_facebook = (LoginButton) findViewById(R.id.bt_entrar_facebook);
        bt_facebook.setReadPermissions("public_profile", "email", "user_friends");

        bt_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_facebook.registerCallback(callbackManager, callback);
            }
        });

        if(SalvarSharedPreferences.getUserName(Inicial.this).length() > 0)
        {
            Intent i = new Intent(Inicial.this, Principal.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    public void pagina_login(View v)
    {
        startActivity(new Intent(this, Login.class));
    }

    public void pagina_registrar(View v)
    {
        startActivity(new Intent(this, RegistroUsuario.class));
    }
}
