package pucsp.locar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pucsp.locar.conexoes.AlterarVeiculoRequisicao;
import pucsp.locar.conexoes.BuscarUsuarioRequisicao;
import pucsp.locar.conexoes.BuscarVeiculoRequisicao;
import pucsp.locar.conexoes.ExcluirVeiculoRequisicao;
import pucsp.locar.conexoes.ModelosRequisicao;
import pucsp.locar.conexoes.MontadorasRequisicao;
import pucsp.locar.conexoes.MudarStatusReservaRequisicao;
import pucsp.locar.conexoes.SalvarVeiculoRequisicao;
import pucsp.locar.objetos.Modelo;
import pucsp.locar.objetos.ModeloAdapter;
import pucsp.locar.objetos.Montadora;
import pucsp.locar.objetos.MontadoraAdapter;
import pucsp.locar.objetos.Usuario;
import pucsp.locar.objetos.Veiculo;
import pucsp.locar.objetos.VeiculoCadastro;
import pucsp.locar.pucsp.locar.assincrono.EnviarImagem;

public class MeuVeiculo extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView iv_foto_veiculo;
    private Spinner s_montadora;
    private Spinner s_modelo;
    private ArrayAdapter<String> adapter;
    private ArrayList<Montadora> montadoras;
    private ArrayList<Modelo> modelos;
    private EditText e_preco;
    private EditText e_placa;
    private String modelo;
    private ProgressBar pb_loading;
    private RelativeLayout layout_carregando;
    private VeiculoCadastro veiculo;
    private String veiculoID;
    private Button bt_alterar;
    private Button bt_excluir;
    private AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_veiculo);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        veiculoID = bundle.getString("veiculoID");

        carregarVeiculo();

        iv_foto_veiculo = (ImageView) findViewById(R.id.iv_foto_veiculo);
        s_montadora = (Spinner) findViewById(R.id.s_montadoras);
        s_modelo = (Spinner) findViewById(R.id.s_modelo);
        e_preco = (EditText) findViewById(R.id.et_preco_minuto);
        e_placa = (EditText) findViewById(R.id.et_placa);
        bt_alterar = (Button) findViewById(R.id.bt_alterar);
        bt_excluir = (Button) findViewById(R.id.bt_excluir);
        layout_carregando = (RelativeLayout) findViewById(R.id.layout_carregando);
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
        pb_loading.setIndeterminate(true);

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
                        adapter = new MontadoraAdapter(MeuVeiculo.this, descricoes);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_montadora.setAdapter(adapter);
                        if (veiculo != null) {
                            s_montadora.setSelection(buscarMontadora(veiculo.montadoraID));
                        }
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MontadorasRequisicao montadorasRequisicao = new MontadorasRequisicao(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MeuVeiculo.this);
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

    private void carregarVeiculo()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        veiculo = new VeiculoCadastro(jsonResponse);
                        preencherCampos();
                    } else {
                        veiculo = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        BuscarVeiculoRequisicao requisicao = new BuscarVeiculoRequisicao(veiculoID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MeuVeiculo.this);
        queue.add(requisicao);
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
                        adapter = new ModeloAdapter(MeuVeiculo.this, descricoes);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_modelo.setAdapter(adapter);
                        if (veiculo != null && veiculo.modeloID != null) {
                            s_modelo.setSelection(buscarModelo(veiculo.modeloID));
                        }
                        if (veiculo != null) {
                            veiculo.modeloID = null;
                        }
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ModelosRequisicao modelosRequisicao = new ModelosRequisicao(montadora, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MeuVeiculo.this);
        queue.add(modelosRequisicao);
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

    private void preencherCampos()
    {
        e_preco.setText(veiculo.preco);
        e_placa.setText(veiculo.placa);

        Picasso.with(MeuVeiculo.this).load(veiculo.imagem_veiculo)
                .into(iv_foto_veiculo);
        String nome = veiculo.imagem_veiculo.substring(veiculo.imagem_veiculo.lastIndexOf("/"));
        nome = nome.substring(1, nome.length() - (nome.length() - nome.lastIndexOf(".")));
        iv_foto_veiculo.setTag(nome);

        if(s_montadora != null && s_montadora.getCount() > 0)
        {
            s_montadora.setSelection(buscarMontadora(veiculo.montadoraID));
        }

        if(s_modelo != null && s_modelo.getCount() > 0)
        {
            s_modelo.setSelection(buscarModelo(veiculo.modeloID));
        }

        layout_carregando.setVisibility(View.GONE);
        iv_foto_veiculo.setVisibility(View.VISIBLE);
        s_modelo.setVisibility(View.VISIBLE);
        s_montadora.setVisibility(View.VISIBLE);
        e_preco.setVisibility(View.VISIBLE);
        e_placa.setVisibility(View.VISIBLE);
        s_modelo.setVisibility(View.VISIBLE);
        bt_alterar.setVisibility(View.VISIBLE);
        bt_excluir.setVisibility(View.VISIBLE);
    }

    private int buscarModelo(String modeloID)
    {
        for(Modelo modelo : modelos)
        {
            if (modelo.codModelo.equals(modeloID))
            {
                return modelos.indexOf(modelo);
            }
        }
        return 0;
    }

    private int buscarMontadora(String montadoraID)
    {
        for(Montadora montadora : montadoras)
        {
            if (montadora.codMontadora.equals(montadoraID))
            {
                return montadoras.indexOf(montadora);
            }
        }
        return 0;
    }

    public void enviarImagem(View v) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    public void alterar(View v) {
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

        final String caminhoImagem = "veiculos_imagens/" + iv_foto_veiculo.getTag().toString() + ".jpg";
        if (!erro) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            Intent i = new Intent(MeuVeiculo.this, Veiculos.class);
                            startActivity(i);

                            if (!veiculo.imagem_veiculo.equals(caminhoImagem)) {
                                Bitmap imagem = ((BitmapDrawable) iv_foto_veiculo.getDrawable()).getBitmap();
                                new EnviarImagem(MeuVeiculo.this, "veiculos_imagens/", iv_foto_veiculo.getTag().toString(), imagem).execute();
                            }
                            finish();
                        } else {
                            Intent i = new Intent(MeuVeiculo.this, Veiculos.class);
                            startActivity(i);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            AlterarVeiculoRequisicao requisicao = new AlterarVeiculoRequisicao(veiculoID, modelo, preco, placa, caminhoImagem, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MeuVeiculo.this);
            queue.add(requisicao);
        }
    }

    public void excluir(View v)
    {
        final String veiculoID = String.valueOf(veiculo.id_veiculo);
        AlertDialog.Builder builder = new AlertDialog.Builder(MeuVeiculo.this);
        builder.setTitle("Excluir");
        builder.setMessage("Você tem certeza que deseja excluir o veículo?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MeuVeiculo.this);

                                builder.setTitle("Veículo excluído");
                                builder.setMessage("Seu veículo foi excluído com sucesso.");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent i = new Intent(MeuVeiculo.this, Veiculos.class);
                                        startActivity(i);
                                    }
                                });
                                alerta = builder.create();
                                alerta.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MeuVeiculo.this);

                                builder.setTitle("Erro ao excluir veículo");
                                builder.setMessage("Ocorreu um erro ao excluir o veículo. Por favor, entre em contato com o suporte.");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent i = new Intent(MeuVeiculo.this, Veiculos.class);
                                        startActivity(i);
                                    }
                                });
                                alerta = builder.create();
                                alerta.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ExcluirVeiculoRequisicao requisicao = new ExcluirVeiculoRequisicao(veiculoID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MeuVeiculo.this);
                queue.add(requisicao);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alerta = builder.create();
        alerta.show();
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(MeuVeiculo.this, Veiculos.class);
        startActivity(i);
    }
}
