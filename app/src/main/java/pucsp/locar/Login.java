package pucsp.locar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import pucsp.locar.conexoes.LoginRequisicao;

public class Login extends AppCompatActivity {

    EditText et_usuario;
    EditText et_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_usuario = (EditText) findViewById(R.id.etUsuario);
        et_senha = (EditText) findViewById(R.id.etSenha);
    }

    public void logar(View v)
    {
        final String usuario = et_usuario.getText().toString();
        final String senha = et_senha.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String motivo = jsonResponse.getString("motivo");

                    if (success) {
                        String nome = jsonResponse.getString("nome");
                        String id = jsonResponse.getString("id");

                        RecursosSharedPreferences.setUserName(Login.this, nome, id, "local");
                        Intent i = new Intent(Login.this, Principal.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();

                    } else {
                        if (motivo.equals("usuario")) {
                            et_usuario.setError("Usuário inválido");
                        }
                        else {
                            et_senha.setError("Senha inválida");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequisicao loginRequisicao = new LoginRequisicao(usuario, senha, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        queue.add(loginRequisicao);
    }

    public void pagina_esqueci_senha(View v)
    {
        //TODO: Página de reset de senha
    }
}
