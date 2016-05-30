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

import pucsp.locar.conexoes.ModelosRequisicao;
import pucsp.locar.conexoes.MontadorasRequisicao;
import pucsp.locar.objetos.Modelo;
import pucsp.locar.objetos.ModeloAdapter;
import pucsp.locar.objetos.Montadora;
import pucsp.locar.objetos.MontadoraAdapter;

public class BuscarVeiculo extends AppCompatActivity {
    Spinner s_montadora;
    Spinner s_modelo;
    EditText et_preco;
    ArrayAdapter<String> adapter;
    ArrayList<Montadora> montadoras;
    ArrayList<Modelo> modelos;
    String montadora;
    String modelo;
    String preco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_veiculo);

        s_montadora = (Spinner) findViewById(R.id.s_montadoras);
        s_modelo = (Spinner) findViewById(R.id.s_modelo);
        et_preco = (EditText) findViewById(R.id.et_preco_minuto);

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
                        descricoes.add("Qualquer");
                        for(Montadora montadora : montadoras)
                        {
                            descricoes.add(montadora.descricao);
                        }
                        adapter = new MontadoraAdapter(BuscarVeiculo.this, descricoes);
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
        RequestQueue queue = Volley.newRequestQueue(BuscarVeiculo.this);
        queue.add(montadorasRequisicao);

        s_montadora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                s_modelo.setAdapter(null);
                if (position > 0) {
                    buscarModelosPorMontadora(montadoras.get(position - 1).codMontadora);
                    montadora = montadoras.get(position - 1).codMontadora;
                }
                else
                {
                    montadora = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                s_modelo.setAdapter(null);
                montadora = null;
            }
        });

        s_modelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position > 0) {
                    modelo = modelos.get(position - 1).codModelo;
                }
                else
                {
                    modelo = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                modelo = null;
            }
        });
    }

    private void buscarModelosPorMontadora(String montadora)
    {
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
                        descricoes.add("Qualquer");
                        for(Modelo modelo : modelos)
                        {
                            descricoes.add(modelo.descricao);
                        }
                        adapter = new ModeloAdapter(BuscarVeiculo.this, descricoes);
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
        RequestQueue queue = Volley.newRequestQueue(BuscarVeiculo.this);
        queue.add(modelosRequisicao);
    }

    public void buscar(View v)
    {
        preco = et_preco.getText().toString();
        Intent intent = new Intent(BuscarVeiculo.this, Principal.class);

        Bundle bundle = new Bundle();
        bundle.putString("modelo", modelo);
        bundle.putString("montadora", montadora);
        bundle.putString("preco", (preco.isEmpty() ? null : preco));

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
