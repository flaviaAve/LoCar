package pucsp.locar.conexoes;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 26/05/2016.
 */
public class SalvarImagemRequisicao extends StringRequest {
    private static final String UPLOAD_REQUEST_URL = "http://locar-servicos.pe.hu/salvar_imagem.php";
    private Map<String, String> params;

    public SalvarImagemRequisicao(String caminho, String nome, String imagem) {
        super(Method.POST, UPLOAD_REQUEST_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                    }
                },
                null);

        params = new HashMap<>();
        params.put("caminho_imagem", caminho);
        params.put("nome_imagem", nome);
        params.put("imagem", imagem);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
