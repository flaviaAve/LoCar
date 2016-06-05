package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 29/05/2016.
 */
public class ReservarVeiculoRequisicao extends StringRequest {
    private static final String RESERVA_REQUEST_URL = "http://locar-servicos.pe.hu/reservar_veiculo.php";
    private Map<String, String> params;

    public ReservarVeiculoRequisicao(String usuarioID, String veiculoID, String dataInicio, String dataFim,  Response.Listener<String> listener) {
        super(Method.POST, RESERVA_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuarioID", usuarioID);
        params.put("veiculoID", veiculoID);
        params.put("data_inicio", dataInicio);
        params.put("data_fim", dataFim);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
