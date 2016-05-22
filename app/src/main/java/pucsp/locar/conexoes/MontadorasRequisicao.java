package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 22/05/2016.
 */
public class MontadorasRequisicao extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://locar-servicos.pe.hu/buscar_montadoras.php";
    private Map<String, String> params;

    public MontadorasRequisicao(Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
