package pucsp.locar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import pucsp.locar.conexoes.BuscarUsuarioRequisicao;
import pucsp.locar.conexoes.MontadorasRequisicao;
import pucsp.locar.objetos.Montadora;
import pucsp.locar.objetos.MontadoraAdapter;
import pucsp.locar.objetos.Usuario;

public class MeuCadastro extends AppCompatActivity {
    private Usuario usuario;
    ImageView iv_usuario;
    EditText et_nome;
    EditText et_email;
    EditText et_login;
    EditText et_senha;
    EditText et_nascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_cadastro);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        usuario = new Usuario(jsonResponse);
                        preencherCampos();
                    } else {
                        usuario = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        BuscarUsuarioRequisicao usuarioRequisicao = new BuscarUsuarioRequisicao(RecursosSharedPreferences.getUserID(MeuCadastro.this), responseListener);
        RequestQueue queue = Volley.newRequestQueue(MeuCadastro.this);
        queue.add(usuarioRequisicao);

        iv_usuario = (ImageView) findViewById(R.id.iv_foto_usuario);
        et_nome = (EditText) findViewById(R.id.etNome);
        et_email = (EditText) findViewById(R.id.etEmail);
        et_login = (EditText) findViewById(R.id.etLogin);
        et_senha = (EditText) findViewById(R.id.etSenha);
        et_nascimento = (EditText) findViewById(R.id.etNascimento);

        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##/####", et_nascimento);
        et_nascimento.addTextChangedListener(maskData);
    }

    private void preencherCampos()
    {
        et_nome.setText(usuario.nome);
        et_login.setText(usuario.login);
        et_email.setText(usuario.email);
        String data = usuario.dt_nascimento.substring(8, 10) + "/" + usuario.dt_nascimento.substring(5, 7) + "/" + usuario.dt_nascimento.substring(0, 4);
        et_nascimento.setText(data);
        et_senha.setText(usuario.senha);
        Picasso.with(MeuCadastro.this).load(usuario.imagem_perfil)
                .into(iv_usuario);
    }
}
