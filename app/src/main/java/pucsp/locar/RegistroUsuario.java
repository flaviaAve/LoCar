package pucsp.locar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import pucsp.locar.conexoes.SalvarUsuarioRequisicao;
import pucsp.locar.pucsp.locar.assincrono.EnviarImagem;

public class RegistroUsuario extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    EditText nome;
    EditText email;
    EditText login;
    EditText nascimento;
    EditText senha;
    EditText confirma_senha;
    ImageView iv_foto_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nome = (EditText) findViewById(R.id.etNome);
        email = (EditText) findViewById(R.id.etEmail);
        login = (EditText) findViewById(R.id.etLogin);
        nascimento = (EditText) findViewById(R.id.etNascimento);
        senha = (EditText) findViewById(R.id.etSenha);
        confirma_senha = (EditText)findViewById(R.id.etConfirmaSenha);
        iv_foto_usuario = (ImageView) findViewById(R.id.ivFotoUsuario);

        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##/####", nascimento);
        nascimento.addTextChangedListener(maskData);
    }

    public void enviarImagem(View v)
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri imagem = data.getData();
            iv_foto_usuario.setImageURI(imagem);
            String caminho = getRealPathFromURI(imagem);
            String nome = caminho.substring(caminho.lastIndexOf("/"));
            nome = nome.substring(1, nome.length() - (nome.length() - nome.lastIndexOf(".")));
            iv_foto_usuario.setTag(nome);
        }
    }

    public void registrar(View v)
    {
        boolean erro = false;

        String login_u = login.getText().toString();
        if (login_u.isEmpty())
        {
            erro = true;
            login.setError("Login é obrigatório!");
        }
        String nome_u = nome.getText().toString();
        if (nome_u.isEmpty())
        {
            erro = true;
            nome.setError("Nome é obrigatório!");
        }
        String email_u = email.getText().toString();
        String data_nasc = nascimento.getText().toString();
        data_nasc = data_nasc.substring(6,10) + "-" + data_nasc.substring(3,5) + "-" + data_nasc.substring(0,2);
        String senha_u = senha.getText().toString();
        if (senha_u.isEmpty())
        {
            erro = true;
            senha.setError("Senha é obrigatória!");
        }
        String confirma_senha_u = confirma_senha.getText().toString();
        if (confirma_senha_u.isEmpty())
        {
            erro = true;
            confirma_senha.setError("Confirmação de Senha é obrigatória!");
        }
        if (!senha_u.equals(confirma_senha_u))
        {
            erro = true;
            confirma_senha.setError("Senhas digitadas estão diferentes.");
        }

        String caminhoImagem = "usuarios_imagens/" + iv_foto_usuario.getTag().toString() + ".jpg";
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

                            RecursosSharedPreferences.setUserName(RegistroUsuario.this, usuario_nome, usuario_id, "local");
                            Intent i = new Intent(RegistroUsuario.this, Principal.class);
                            startActivity(i);
                            Bitmap imagem = ((BitmapDrawable)iv_foto_usuario.getDrawable()).getBitmap();
                            new EnviarImagem(RegistroUsuario.this, "usuarios_imagens/", iv_foto_usuario.getTag().toString(), imagem).execute();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            SalvarUsuarioRequisicao salvarUsuario = new SalvarUsuarioRequisicao(login_u, senha_u, nome_u, email_u, data_nasc, "local", caminhoImagem, responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegistroUsuario.this);
            queue.add(salvarUsuario);
        }
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
}
