package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 28/05/2016.
 */
public class SalvarVeiculoRequisicao extends StringRequest {
    private static final String VEICULO_REQUEST_URL = "http://locar-servicos.pe.hu/salvar_veiculo.php";
    private Map<String, String> params;

    public SalvarVeiculoRequisicao(String usuario, String modelo, String preco, String placa, String renavam, String caminhoImagem, Response.Listener<String> listener) {
        super(Method.POST, VEICULO_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuarioID", usuario);
        params.put("modelo", modelo);
        params.put("preco", preco);
        params.put("placa", placa);
        params.put("renavam", renavam);
        params.put("caminho_imagem", caminhoImagem);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
