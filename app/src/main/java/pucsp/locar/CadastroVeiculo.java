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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pucsp.locar.conexoes.ModelosRequisicao;
import pucsp.locar.conexoes.MontadorasRequisicao;
import pucsp.locar.conexoes.SalvarVeiculoRequisicao;
import pucsp.locar.objetos.Modelo;
import pucsp.locar.objetos.ModeloAdapter;
import pucsp.locar.objetos.Montadora;
import pucsp.locar.objetos.MontadoraAdapter;
import pucsp.locar.assincrono.EnviarImagem;

public class CadastroVeiculo extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView iv_foto_veiculo;
    private Spinner s_montadora;
    private Spinner s_modelo;
    private ArrayAdapter<String> adapter;
    private ArrayList<Montadora> montadoras;
    private ArrayList<Modelo> modelos;
    private EditText e_preco;
    private EditText e_placa;
    private EditText e_renavam;
    private String modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veiculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_foto_veiculo = (ImageView) findViewById(R.id.iv_foto_veiculo);
        s_montadora = (Spinner) findViewById(R.id.s_montadoras);
        s_modelo = (Spinner) findViewById(R.id.s_modelo);
        e_preco = (EditText) findViewById(R.id.et_preco_minuto);
        e_placa = (EditText) findViewById(R.id.et_placa);
        e_renavam = (EditText) findViewById(R.id.et_renavam);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray montadorasJSON = jsonResponse.getJSONArray("montadora");
                        montadoras = Montadora.fromJson(montadorasJSON);
                        ArrayList<String> descricoes = new ArrayList<String>();
                        for (Montadora montadora : montadoras) {
                            descricoes.add(montadora.descricao);
                        }
                        adapter = new MontadoraAdapter(CadastroVeiculo.this, descricoes);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_montadora.setAdapter(adapter);
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MontadorasRequisicao montadorasRequisicao = new MontadorasRequisicao(responseListener);
        RequestQueue queue = Volley.newRequestQueue(CadastroVeiculo.this);
        queue.add(montadorasRequisicao);

        s_montadora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                s_modelo.setAdapter(null);
                buscarModelosPorMontadora(montadoras.get(position).codMontadora);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                s_modelo.setAdapter(null);
            }
        });

        s_modelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                modelo = modelos.get(position).codModelo;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                modelo = null;
            }
        });
    }

    private void buscarModelosPorMontadora(String montadora) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray modelosJSON = jsonResponse.getJSONArray("modelo");
                        modelos = Modelo.fromJson(modelosJSON);
                        ArrayList<String> descricoes = new ArrayList<String>();
                        for (Modelo modelo : modelos) {
                            descricoes.add(modelo.descricao);
                        }
                        adapter = new ModeloAdapter(CadastroVeiculo.this, descricoes);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_modelo.setAdapter(adapter);
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ModelosRequisicao modelosRequisicao = new ModelosRequisicao(montadora, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CadastroVeiculo.this);
        queue.add(modelosRequisicao);
    }

    public void enviarImagem(View v) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imagem = data.getData();
            iv_foto_veiculo.setImageURI(imagem);
            String caminho = getRealPathFromURI(imagem);
            String nome = caminho.substring(caminho.lastIndexOf("/"));
            nome = nome.substring(1, nome.length() - (nome.length() - nome.lastIndexOf(".")));
            iv_foto_veiculo.setTag(nome);
        }
    }

    public void salvar(View v) {
        boolean erro = false;

        if (modelo.isEmpty()) {
            erro = true;
        }

        String preco = e_preco.getText().toString();
        if (preco.isEmpty()) {
            erro = true;
            e_preco.setError("Preço é obrigatório!");
        }

        String placa = e_placa.getText().toString();
        if (placa.isEmpty()) {
            erro = true;
            e_placa.setError("Placa é obrigatória!");
        }

        String renavam = e_renavam.getText().toString();
        if (renavam.isEmpty()) {
            erro = true;
            e_renavam.setError("Renavam é obrigatório!");
        }

        String caminhoImagem = "veiculos_imagens/" + iv_foto_veiculo.getTag().toString() + ".jpg";
        if (!erro) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            Intent i = new Intent(CadastroVeiculo.this, Veiculos.class);
                            startActivity(i);
                            Bitmap imagem = ((BitmapDrawable) iv_foto_veiculo.getDrawable()).getBitmap();
                            new EnviarImagem(CadastroVeiculo.this, "veiculos_imagens/", iv_foto_veiculo.getTag().toString(), imagem).execute();
                            finish();
                        } else {
                            Intent i = new Intent(CadastroVeiculo.this, Veiculos.class);
                            startActivity(i);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            SalvarVeiculoRequisicao salvarVeiculo = new SalvarVeiculoRequisicao(RecursosSharedPreferences.getUserID(CadastroVeiculo.this), modelo, preco, placa, renavam, caminhoImagem, responseListener);
            RequestQueue queue = Volley.newRequestQueue(CadastroVeiculo.this);
            queue.add(salvarVeiculo);
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