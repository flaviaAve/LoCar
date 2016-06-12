package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 11/06/2016.
 */
public class CidadesRequisicao extends StringRequest {
    private static final String CIDADES_REQUEST_URL = "http://locar-servicos.pe.hu/buscar_cidades.php";
    private Map<String, String> params;

    public CidadesRequisicao(String sigla, Response.Listener<String> listener) {
        super(Method.POST, CIDADES_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("estado", sigla);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
