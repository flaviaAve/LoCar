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

import java.util.ArrayList;

import pucsp.locar.conexoes.BuscarAvaliacoesPendentes;
import pucsp.locar.conexoes.ListarReservasRequisicao;
import pucsp.locar.conexoes.MontadorasRequisicao;
import pucsp.locar.objetos.AvaliacaoLista;
import pucsp.locar.objetos.AvaliacaoListaAdapter;
import pucsp.locar.objetos.Montadora;
import pucsp.locar.objetos.MontadoraAdapter;
import pucsp.locar.objetos.ReservaLista;
import pucsp.locar.objetos.ReservaListaAdapter;

public class Avaliacoes extends AppCompatActivity {
    private AvaliacaoListaAdapter adapter;
    private ListView lv_avaliacoes_pendentes;
    private TextView tv_sem_registros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacoes);

        lv_avaliacoes_pendentes = (ListView) findViewById(R.id.lv_avaliacoes_pendentes);
        tv_sem_registros = (TextView) findViewById(R.id.tv_sem_registro);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray avaliacoesJSON = jsonResponse.getJSONArray("avaliacao");
                        adapter = new AvaliacaoListaAdapter(Avaliacoes.this, AvaliacaoLista.fromJson(avaliacoesJSON));
                        lv_avaliacoes_pendentes.setAdapter(adapter);
                        lv_avaliacoes_pendentes.setVisibility(View.VISIBLE);
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

        String usuarioID = RecursosSharedPreferences.getUserID(Avaliacoes.this);

        BuscarAvaliacoesPendentes requisicao = new BuscarAvaliacoesPendentes(usuarioID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Avaliacoes.this);
        queue.add(requisicao);
    }

    public void abrirMinhasAvaliacoes(View v)
    {
        Intent i = new Intent(Avaliacoes.this, MinhasAvaliacoes.class);
        startActivity(i);
    }

    public void abrirAvaliacoesRealizadas(View v)
    {
        Intent i = new Intent(Avaliacoes.this, AvaliacoesRealizadas.class);
        startActivity(i);
    }

    public void abrirQuestionario(View v)
    {
        Intent i = new Intent(Avaliacoes.this, Questionario.class);

        Bundle bundle = new Bundle();
        bundle.putString("questionarioID", v.getTag(R.string.QUESTIONARIO_ID).toString());
        bundle.putString("reservaID", v.getTag(R.string.RESERVA_ID).toString());

        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Avaliacoes.this, Principal.class);
        startActivity(i);
    }
}
