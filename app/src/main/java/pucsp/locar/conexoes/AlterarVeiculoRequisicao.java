package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 29/05/2016.
 */
public class AlterarVeiculoRequisicao extends StringRequest {
    private static final String VEICULO_REQUEST_URL = "http://locar-servicos.pe.hu/alterar_veiculo.php";
    private Map<String, String> params;

    public AlterarVeiculoRequisicao(String veiculoID, String modelo, String preco, String placa, String renavam, String caminhoImagem, Response.Listener<String> listener) {
        super(Method.POST, VEICULO_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("veiculoID", veiculoID);
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
