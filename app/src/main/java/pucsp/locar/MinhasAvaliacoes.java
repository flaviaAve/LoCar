package pucsp.locar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pucsp.locar.conexoes.BuscarAvaliacoesPendentes;
import pucsp.locar.conexoes.BuscarMinhasAvaliacoes;
import pucsp.locar.objetos.Avaliacao;
import pucsp.locar.objetos.AvaliacaoAdapter;
import pucsp.locar.objetos.AvaliacaoLista;
import pucsp.locar.objetos.AvaliacaoListaAdapter;

public class MinhasAvaliacoes extends AppCompatActivity {
    private AvaliacaoAdapter adapter;
    private ListView lv_minhas_avaliacoes;
    private TextView tv_sem_registros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_avaliacoes);

        lv_minhas_avaliacoes = (ListView) findViewById(R.id.lv_minhas_avaliacoes);
        tv_sem_registros = (TextView) findViewById(R.id.tv_sem_registro);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray avaliacoesJSON = jsonResponse.getJSONArray("avaliacao");
                        adapter = new AvaliacaoAdapter(MinhasAvaliacoes.this, Avaliacao.fromJson(avaliacoesJSON));
                        lv_minhas_avaliacoes.setAdapter(adapter);
                        lv_minhas_avaliacoes.setVisibility(View.VISIBLE);
                        tv_sem_registros.setVisibility(View.INVISIBLE);
                    } else {
                        adapter = null;
                        tv_sem_registros.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String usuarioID = RecursosSharedPreferences.getUserID(MinhasAvaliacoes.this);

        BuscarMinhasAvaliacoes requisicao = new BuscarMinhasAvaliacoes(usuarioID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MinhasAvaliacoes.this);
        queue.add(requisicao);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(MinhasAvaliacoes.this, Avaliacoes.class);
        startActivity(i);
    }
}
