package pucsp.locar.conexoes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Flavia on 05/06/2016.
 */
public class SalvarQuestionario extends StringRequest {
    private static final String QUESTIONARIO_REQUEST_URL = "http://locar-servicos.pe.hu/salvar_questionario.php";
    private Map<String, String> params;

    public SalvarQuestionario(String questionarioID, String reservaID, String comentario, float pontuacao, Response.Listener<String> listener) {
        super(Method.POST, QUESTIONARIO_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("questionarioID", questionarioID);
        params.put("reservaID", reservaID);
        params.put("comentario", comentario);
        params.put("pontuacao", String.valueOf(pontuacao));
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
