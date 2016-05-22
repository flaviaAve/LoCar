package pucsp.locar.conexoes;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Flavia on 22/05/2016.
 */
public class LoginRequisicao extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://locar-servicos.pe.hu/autenticar_usuario.php";
    private Map<String, String> params;

    public LoginRequisicao(String usuario, String senha, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuario", usuario);
        params.put("senha", senha);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
