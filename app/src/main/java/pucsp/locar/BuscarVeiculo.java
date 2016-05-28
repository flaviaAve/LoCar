package pucsp.locar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    ArrayAdapter<String> adapter;
    ArrayList<Montadora> montadoras;
    ArrayList<Modelo> modelos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_veiculo);

        s_montadora = (Spinner) findViewById(R.id.s_montadoras);
        s_modelo = (Spinner) findViewById(R.id.s_modelo);

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
                buscarModelosPorMontadora(montadoras.get(position).codMontadora);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                s_modelo.setAdapter(null);
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
}
