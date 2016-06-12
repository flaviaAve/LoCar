package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 11/06/2016.
 */
public class AlterarTelefoneRequisicao extends StringRequest {
    private static final String SALVAR_REQUEST_URL = "http://locar-servicos.pe.hu/alterar_telefone.php";
    private Map<String, String> params;

    public AlterarTelefoneRequisicao(String codTelefone, String codTipo, String ddd, String numero, Response.Listener<String> listener) {
        super(Method.POST, SALVAR_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("ID", codTelefone);
        params.put("codTipo", codTipo);
        params.put("DDD", ddd);
        params.put("numero", numero);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
