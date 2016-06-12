package pucsp.locar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pucsp.locar.conexoes.SalvarEnderecoRequisicao;
import pucsp.locar.conexoes.SalvarTelefoneRequisicao;
import pucsp.locar.conexoes.TipoEnderecoRequisicao;
import pucsp.locar.conexoes.TipoTelefoneRequisicao;
import pucsp.locar.objetos.Cidade;
import pucsp.locar.objetos.TipoEndereco;
import pucsp.locar.objetos.TipoEnderecoAdapter;
import pucsp.locar.objetos.TipoTelefone;
import pucsp.locar.objetos.TipoTelefoneAdapter;

public class CadastroTelefone extends AppCompatActivity {
    private Spinner s_tipo_telefone1;
    private Spinner s_tipo_telefone2;
    private Spinner s_tipo_telefone3;
    private ArrayAdapter<String> adapter;
    private ArrayList<TipoTelefone> tipos;
    private EditText e_ddd1;
    private EditText e_ddd2;
    private EditText e_ddd3;
    private EditText e_numero1;
    private EditText e_numero2;
    private EditText e_numero3;

    private String tipoTelefone1;
    private String tipoTelefone2;
    private String tipoTelefone3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_telefone);

        s_tipo_telefone1 = (Spinner) findViewById(R.id.s_tipo_telefone1);
        s_tipo_telefone2 = (Spinner) findViewById(R.id.s_tipo_telefone2);
        s_tipo_telefone3 = (Spinner) findViewById(R.id.s_tipo_telefone3);
        e_ddd1 = (EditText) findViewById(R.id.et_ddd1);
        e_ddd2 = (EditText) findViewById(R.id.et_ddd2);
        e_ddd3 = (EditText) findViewById(R.id.et_ddd3);
        e_numero1 = (EditText) findViewById(R.id.et_numero1);
        e_numero2 = (EditText) findViewById(R.id.et_numero2);
        e_numero3 = (EditText) findViewById(R.id.et_numero3);

        buscarTiposTelefones();

        s_tipo_telefone1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                tipoTelefone1 = tipos.get(position).codTipo;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                tipoTelefone1 = null;
            }
        });

        s_tipo_telefone2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                tipoTelefone2 = tipos.get(position).codTipo;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                tipoTelefone2 = null;
            }
        });

        s_tipo_telefone3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                tipoTelefone3 = tipos.get(position).codTipo;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                tipoTelefone3 = null;
            }
        });
    }

    private void buscarTiposTelefones()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray tiposJSON = jsonResponse.getJSONArray("tipo");
                        tipos = TipoTelefone.fromJson(tiposJSON);
                        ArrayList<String> descricoes = new ArrayList<String>();
                        for (TipoTelefone tipo : tipos) {
                            descricoes.add(tipo.descricao);
                        }
                        adapter = new TipoTelefoneAdapter(CadastroTelefone.this, descricoes);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_tipo_telefone1.setAdapter(adapter);
                        s_tipo_telefone2.setAdapter(adapter);
                        s_tipo_telefone3.setAdapter(adapter);
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        TipoTelefoneRequisicao requisicao = new TipoTelefoneRequisicao(responseListener);
        RequestQueue queue = Volley.newRequestQueue(CadastroTelefone.this);
        queue.add(requisicao);
    }

    public void salvar(View v) {
        boolean erro = false;

        if (tipoTelefone1.isEmpty()) {
            erro = true;
        }

        String ddd = e_ddd1.getText().toString();
        if (ddd.isEmpty()) {
            erro = true;
            e_ddd1.setError("DDD é obrigatório!");
        }

        String numero = e_numero1.getText().toString();
        if (numero.isEmpty()) {
            erro = true;
            e_numero1.setError("Número é obrigatório!");
        }

        if (!erro) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            SalvarTelefoneRequisicao salvar = new SalvarTelefoneRequisicao(RecursosSharedPreferences.getUserID(CadastroTelefone.this), tipoTelefone1, ddd, numero, responseListener);
            RequestQueue queue = Volley.newRequestQueue(CadastroTelefone.this);
            queue.add(salvar);

            ddd = e_ddd2.getText().toString();
            numero = e_numero2.getText().toString();

            if(!ddd.isEmpty() && !numero.isEmpty())
            {
                salvar = new SalvarTelefoneRequisicao(RecursosSharedPreferences.getUserID(CadastroTelefone.this), tipoTelefone2, ddd, numero, responseListener);
                queue = Volley.newRequestQueue(CadastroTelefone.this);
                queue.add(salvar);
            }

            ddd = e_ddd3.getText().toString();
            numero = e_numero3.getText().toString();

            if(!ddd.isEmpty() && !numero.isEmpty())
            {
                salvar = new SalvarTelefoneRequisicao(RecursosSharedPreferences.getUserID(CadastroTelefone.this), tipoTelefone3, ddd, numero, responseListener);
                queue = Volley.newRequestQueue(CadastroTelefone.this);
                queue.add(salvar);
            }

            Intent i = new Intent(CadastroTelefone.this, Principal.class);
            startActivity(i);
            finish();
        }
    }
}
