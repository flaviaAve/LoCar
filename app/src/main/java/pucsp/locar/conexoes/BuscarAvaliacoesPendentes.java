package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 04/06/2016.
 */
public class BuscarAvaliacoesPendentes extends StringRequest {
    private static final String AVALIACOES_REQUEST_URL = "http://locar-servicos.pe.hu/buscar_avaliacoes_pendentes.php";
    private Map<String, String> params;

    public BuscarAvaliacoesPendentes(String usuarioID, Response.Listener<String> listener) {
        super(Method.POST, AVALIACOES_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuarioID", usuarioID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
