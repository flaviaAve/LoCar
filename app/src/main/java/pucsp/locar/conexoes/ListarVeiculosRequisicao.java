package pucsp.locar.conexoes;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 22/05/2016.
 */
public class ListarVeiculosRequisicao extends StringRequest {

    private static final String VEICULOS_REQUEST_URL = "http://locar-servicos.pe.hu/listar_veiculos.php";
    private Map<String, String> params;

    public ListarVeiculosRequisicao(String usuarioID, String montadora, String modelo, String preco, Response.Listener<String> listener) {
        super(Request.Method.POST, VEICULOS_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("usuarioID", usuarioID);
        if (montadora != null) {
            params.put("montadora", montadora);
        }
        if (modelo != null) {
            params.put("modelo", modelo);
        }
        if (preco != null) {
            params.put("preco", preco);
        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
