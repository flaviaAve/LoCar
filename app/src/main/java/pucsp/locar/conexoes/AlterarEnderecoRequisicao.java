package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 11/06/2016.
 */
public class AlterarEnderecoRequisicao extends StringRequest {
    private static final String SALVAR_REQUEST_URL = "http://locar-servicos.pe.hu/alterar_endereco.php";
    private Map<String, String> params;

    public AlterarEnderecoRequisicao(String codEndereco, String codTipo, String logradouro, String numero, String complemento, String cep, String bairro, String codCidade, Response.Listener<String> listener) {
        super(Method.POST, SALVAR_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("ID", codEndereco);
        params.put("codTipo", codTipo);
        params.put("logradouro", logradouro);
        params.put("numero", numero);
        params.put("complemento", complemento);
        params.put("cep", cep);
        params.put("bairro", bairro);
        params.put("codCidade", codCidade);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
