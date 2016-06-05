package pucsp.locar.conexoes;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 04/06/2016.
 */
public class ListarQuestoes extends StringRequest {

    private static final String QUESTIONARIO_REQUEST_URL = "http://locar-servicos.pe.hu/listar_questionario.php";
    private Map<String, String> params;

    public ListarQuestoes(String questionarioID, Response.Listener<String> listener) {
        super(Request.Method.POST, QUESTIONARIO_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("questionarioID", questionarioID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
