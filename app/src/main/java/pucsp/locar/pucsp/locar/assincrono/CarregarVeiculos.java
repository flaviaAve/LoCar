package pucsp.locar.pucsp.locar.assincrono;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pucsp.locar.CadastroVeiculo;
import pucsp.locar.conexoes.ListarVeiculosRequisicao;
import pucsp.locar.objetos.Veiculo;
import pucsp.locar.objetos.VeiculoAdapter;

/**
 * Created by Flavia on 27/05/2016.
 */
public class CarregarVeiculos extends AsyncTask<Void, Void, Void> {
    private VeiculoAdapter adapter;
    private ListView lista;
    private Context context;

    public CarregarVeiculos(Context context, ListView lista)
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
                        adapter = new VeiculoAdapter(context, Veiculo.fromJson(veiculosJSON));
                        lista.setAdapter(adapter);
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ListarVeiculosRequisicao veiculoRequisicao = new ListarVeiculosRequisicao(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(veiculoRequisicao);
        return null;
    }
}
