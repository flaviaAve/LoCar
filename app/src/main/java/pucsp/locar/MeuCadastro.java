package pucsp.locar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import pucsp.locar.conexoes.AlterarUsuarioRequisicao;
import pucsp.locar.conexoes.BuscarUsuarioRequisicao;
import pucsp.locar.conexoes.MontadorasRequisicao;
import pucsp.locar.conexoes.SalvarUsuarioRequisicao;
import pucsp.locar.objetos.Montadora;
import pucsp.locar.objetos.MontadoraAdapter;
import pucsp.locar.objetos.Usuario;
import pucsp.locar.pucsp.locar.assincrono.EnviarImagem;

public class MeuCadastro extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private Usuario usuario;
    private ImageView iv_usuario;
    private EditText et_nome;
    private EditText et_email;
    private EditText et_login;
    private EditText et_senha;
    private EditText et_nascimento;
    private ProgressBar pb_loading;
    private RelativeLayout layout_carregando;

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

        layout_carregando = (RelativeLayout) findViewById(R.id.layout_carregando);
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
        pb_loading.setIndeterminate(true);

        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##/####", et_nascimento);
        et_nascimento.addTextChangedListener(maskData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri imagem = data.getData();
            iv_usuario.setImageURI(imagem);
            String caminho = getRealPathFromURI(imagem);
            String nome = caminho.substring(caminho.lastIndexOf("/"));
            nome = nome.substring(1, nome.length() - (nome.length() - nome.lastIndexOf(".")));
            iv_usuario.setTag(nome);
        }
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
        String nome = usuario.imagem_perfil.substring(usuario.imagem_perfil.lastIndexOf("/"));
        nome = nome.substring(1, nome.length() - (nome.length() - nome.lastIndexOf(".")));
        iv_usuario.setTag(nome);

        layout_carregando.setVisibility(View.GONE);
    }

    public void enviarImagem(View v)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public void alterar(View v)
    {
        boolean erro = false;

        String login_u = et_login.getText().toString();
        if (login_u.isEmpty())
        {
            erro = true;
            et_login.setError("Login é obrigatório!");
        }
        String nome_u = et_nome.getText().toString();
        if (nome_u.isEmpty())
        {
            erro = true;
            et_nome.setError("Nome é obrigatório!");
        }
        String email_u = et_email.getText().toString();
        String data_nasc = et_nascimento.getText().toString();
        data_nasc = data_nasc.substring(6,10) + "-" + data_nasc.substring(3,5) + "-" + data_nasc.substring(0,2);
        String senha_u = et_senha.getText().toString();
        if (senha_u.isEmpty())
        {
            erro = true;
            et_senha.setError("Senha é obrigatória!");
        }

        final String caminhoImagem = "usuarios_imagens/" + iv_usuario.getTag().toString() + ".jpg";
        if (!erro) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            String usuario_id = jsonResponse.getString("id");
                            String usuario_nome = jsonResponse.getString("nome");

                            RecursosSharedPreferences.setUserName(MeuCadastro.this, usuario_nome, usuario_id, "local");
                            Intent i = new Intent(MeuCadastro.this, Principal.class);
                            startActivity(i);
                            if (!usuario.imagem_perfil.equals(caminhoImagem)) {
                                Bitmap imagem = ((BitmapDrawable) iv_usuario.getDrawable()).getBitmap();
                                new EnviarImagem(MeuCadastro.this, "usuarios_imagens/", iv_usuario.getTag().toString(), imagem).execute();
                            }
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            AlterarUsuarioRequisicao requisicao = new AlterarUsuarioRequisicao(String.valueOf(usuario.id_usuario), login_u, senha_u, nome_u, email_u, data_nasc, caminhoImagem, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MeuCadastro.this);
            queue.add(requisicao);
        }
    }
}
