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

import pucsp.locar.conexoes.ListarReservasRequisicao;
import pucsp.locar.conexoes.ListarVeiculosRequisicao;
import pucsp.locar.objetos.ReservaLista;
import pucsp.locar.objetos.ReservaListaAdapter;
import pucsp.locar.objetos.Veiculo;
import pucsp.locar.objetos.VeiculoAdapter;

public class Reservas extends AppCompatActivity {
    private ReservaListaAdapter adapter;
    private ListView lv_reservas;
    private TextView tv_sem_registros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        lv_reservas = (ListView) findViewById(R.id.lv_reservas);
        tv_sem_registros = (TextView) findViewById(R.id.tv_sem_registro);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray reservasJSON = jsonResponse.getJSONArray("reserva");
                        adapter = new ReservaListaAdapter(Reservas.this, ReservaLista.fromJson(reservasJSON));
                        lv_reservas.setAdapter(adapter);
                        lv_reservas.setVisibility(View.VISIBLE);
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

        String usuarioID = RecursosSharedPreferences.getUserID(Reservas.this);

        ListarReservasRequisicao requisicao = new ListarReservasRequisicao(usuarioID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Reservas.this);
        queue.add(requisicao);
    }

    public void exibirReserva(View v)
    {
        Intent i = new Intent(Reservas.this, VisualizarReserva.class);

        Bundle bundle = new Bundle();
        bundle.putString("reservaID", v.getTag().toString());

        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Reservas.this, Principal.class);
        startActivity(i);
    }
}
