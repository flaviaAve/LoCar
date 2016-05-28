package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 28/05/2016.
 */
public class BuscarUsuarioRequisicao extends StringRequest {
    private static final String USUARIO_REQUEST_URL = "http://locar-servicos.pe.hu/buscar_usuario.php";
    private Map<String, String> params;

    public BuscarUsuarioRequisicao(String usuarioID, Response.Listener<String> listener) {
        super(Method.POST, USUARIO_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuarioID", usuarioID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
