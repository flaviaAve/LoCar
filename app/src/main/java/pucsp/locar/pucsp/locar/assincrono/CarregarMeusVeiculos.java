package pucsp.locar.pucsp.locar.assincrono;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pucsp.locar.SalvarSharedPreferences;
import pucsp.locar.conexoes.ListarMeusVeiculos;
import pucsp.locar.conexoes.ListarVeiculosRequisicao;
import pucsp.locar.objetos.MeuVeiculoAdapter;
import pucsp.locar.objetos.Veiculo;
import pucsp.locar.objetos.VeiculoAdapter;

/**
 * Created by Flavia on 27/05/2016.
 */
public class CarregarMeusVeiculos extends AsyncTask<Void, Void, Void> {
    private MeuVeiculoAdapter adapter;
    private ListView lista;
    private Context context;

    public CarregarMeusVeiculos(Context context, ListView lista)
    {
        this.context = context;
        this.lista = lista;
    }

    @Override
    protected Void doInBackground(Void... params) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray veiculosJSON = jsonResponse.getJSONArray("veiculo");
                        adapter = new MeuVeiculoAdapter(context, Veiculo.fromJson(veiculosJSON));
                        lista.setAdapter(adapter);
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ListarMeusVeiculos veiculoRequisicao = new ListarMeusVeiculos(SalvarSharedPreferences.getUserID(context), responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(veiculoRequisicao);
        return null;
    }
}