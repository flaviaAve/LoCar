package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 28/05/2016.
 */
public class BuscarVeiculoReservaRequisicao extends StringRequest {
    private static final String VEICULO_REQUEST_URL = "http://locar-servicos.pe.hu/buscar_veiculo_reserva.php";
    private Map<String, String> params;

    public BuscarVeiculoReservaRequisicao(String veiculoID, Response.Listener<String> listener) {
        super(Method.POST, VEICULO_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("veiculo", veiculoID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
