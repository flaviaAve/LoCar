package pucsp.locar.conexoes;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 22/05/2016.
 */
public class ListarVeiculosRequisicao extends StringRequest {

    private static final String VEICULOS_REQUEST_URL = "http://locar-servicos.pe.hu/listar_veiculos.php";
    private Map<String, String> params;

    public ListarVeiculosRequisicao(Response.Listener<String> listener) {
        super(Request.Method.POST, VEICULOS_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
