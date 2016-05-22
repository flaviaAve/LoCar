package pucsp.locar.conexoes;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 22/05/2016.
 */
public class VeiculoImagemRequisicao extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://locar-servicos.pe.hu/buscar_imagem_veiculo.php";
    private Map<String, String> params;

    public VeiculoImagemRequisicao(Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
