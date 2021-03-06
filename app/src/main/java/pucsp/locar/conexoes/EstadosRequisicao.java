package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 11/06/2016.
 */
public class EstadosRequisicao extends StringRequest {
    private static final String ESTADOS_REQUEST_URL = "http://locar-servicos.pe.hu/buscar_estados.php";
    private Map<String, String> params;

    public EstadosRequisicao(Response.Listener<String> listener) {
        super(Method.POST, ESTADOS_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
