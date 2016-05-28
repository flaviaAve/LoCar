package pucsp.locar.conexoes;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 27/05/2016.
 */
public class ListarMeusVeiculos extends StringRequest {

    private static final String VEICULOS_REQUEST_URL = "http://locar-servicos.pe.hu/listar_meus_veiculos.php";
    private Map<String, String> params;

    public ListarMeusVeiculos(String usuarioID, Response.Listener<String> listener) {
        super(Request.Method.POST, VEICULOS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuarioID", usuarioID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
