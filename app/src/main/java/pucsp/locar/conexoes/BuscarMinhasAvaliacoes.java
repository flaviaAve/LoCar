package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 04/06/2016.
 */
public class BuscarMinhasAvaliacoes extends StringRequest {
    private static final String AVALIACOES_REQUEST_URL = "http://locar-servicos.pe.hu/buscar_minhas_avaliacoes.php";
    private Map<String, String> params;

    public BuscarMinhasAvaliacoes(String usuarioID, Response.Listener<String> listener) {
        super(Method.POST, AVALIACOES_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuarioID", usuarioID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
