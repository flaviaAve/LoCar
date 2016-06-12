package pucsp.locar.assincrono;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pucsp.locar.CadastroVeiculo;
import pucsp.locar.R;
import pucsp.locar.conexoes.ListarVeiculosRequisicao;
import pucsp.locar.objetos.Veiculo;
import pucsp.locar.objetos.VeiculoAdapter;

/**
 * Created by Flavia on 27/05/2016.
 */
public class CarregarVeiculos extends AsyncTask<Void, Void, Void> {
    private VeiculoAdapter adapter;
    private ListView lista;
    private TextView texto;
    private Context context;
    private String montadora;
    private String modelo;
    private String preco;
    private String usuarioID;

    public CarregarVeiculos(Context context, String usuarioID, String montadora, String modelo, String preco, ListView lista, TextView texto)
    {
        this.context = context;
        this.lista = lista;
        this.montadora = montadora;
        this.modelo = modelo;
        this.preco = preco;
        this.usuarioID = usuarioID;
        this.texto = texto;
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
                        lista.setVisibility(View.VISIBLE);
                        texto.setVisibility(View.INVISIBLE);
                    } else {
                        adapter = null;
                        texto.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ListarVeiculosRequisicao veiculoRequisicao = new ListarVeiculosRequisicao(usuarioID, montadora, modelo, preco, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(veiculoRequisicao);
        return null;
    }
}
