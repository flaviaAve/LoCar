package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 29/05/2016.
 */
public class BuscarReservaRequisicao extends StringRequest {
    private static final String RESERVA_REQUEST_URL = "http://locar-servicos.pe.hu/buscar_reserva.php";
    private Map<String, String> params;

    public BuscarReservaRequisicao(String reservaID, Response.Listener<String> listener) {
        super(Method.POST, RESERVA_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("reservaID", reservaID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
