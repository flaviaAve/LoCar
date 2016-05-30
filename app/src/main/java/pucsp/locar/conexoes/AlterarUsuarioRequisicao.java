package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 29/05/2016.
 */
public class AlterarUsuarioRequisicao extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://locar-servicos.pe.hu/alterar_usuario.php";
    private Map<String, String> params;

    public AlterarUsuarioRequisicao(String usuarioID, String usuario, String senha, String nome, String email, String data_nascimento, String imagem_perfil, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuarioID", usuarioID);
        params.put("login", usuario);
        params.put("email", email);
        params.put("senha", senha);
        params.put("nome", nome);
        params.put("data_nascimento", data_nascimento);
        params.put("imagem_perfil", imagem_perfil);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
