package pucsp.locar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import pucsp.locar.conexoes.SalvarUsuarioRequisicao;
import pucsp.locar.assincrono.EnviarImagem;

public class Inicial extends AppCompatActivity {

    private LoginButton bt_facebook;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_inicial);

        i = new Intent(Inicial.this, Principal.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                if (newProfile != null) {
                    try {
                        Uri profilePicture = newProfile.getProfilePictureUri(100, 100);
                        new EnviarImagem(getApplicationContext(), "usuarios_imagens/", newProfile.getId().toString(), profilePicture).execute();
                        String caminhoImagem = "usuarios_imagens/" + newProfile.getId().toString() + ".jpg";
                        String login = newProfile.getFirstName();
                        String nome = newProfile.getName();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        String usuario_id = jsonResponse.getString("id");
                                        String usuario_nome = jsonResponse.getString("nome");

                                        RecursosSharedPreferences.setUserName(Inicial.this, usuario_nome, usuario_id, "facebook");
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        SalvarUsuarioRequisicao salvarUsuario = new SalvarUsuarioRequisicao(login, null, nome, null, null, "facebook", caminhoImagem, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(Inicial.this);
                        queue.add(salvarUsuario);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
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
        bt_facebook.setReadPermissions("public_profile", "email");

        bt_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_facebook.registerCallback(callbackManager, callback);
            }
        });

        if(RecursosSharedPreferences.getUserName(Inicial.this).length() > 0)
        {
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
            startActivity(i);
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
