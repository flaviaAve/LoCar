package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 11/06/2016.
 */
public class BuscarTelefoneRequisicao extends StringRequest {
    private static final String TELEFONE_REQUEST_URL = "http://locar-servicos.pe.hu/buscar_telefone.php";
    private Map<String, String> params;

    public BuscarTelefoneRequisicao(String usuarioID, Response.Listener<String> listener) {
        super(Method.POST, TELEFONE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuarioID", usuarioID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
