package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 04/06/2016.
 */
public class AvaliacaoLista {
    public int id_reserva;
    public String usuario;
    public String modelo;
    public String montadora;
    public String data_inicio;
    public String data_fim;
    public String tipo_questionario;

    public AvaliacaoLista(JSONObject object){
        try {
            this.id_reserva = object.getInt("codigo");
            this.usuario = object.getString("usuario");
            this.modelo = object.getString("modelo");
            this.montadora = object.getString("montadora");
            this.data_inicio = object.getString("dataInicio");
            this.data_fim = object.getString("dataFim");
            this.tipo_questionario = object.getString("tipoQuestionario");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<AvaliacaoLista> fromJson(JSONArray jsonObjects) {
        ArrayList<AvaliacaoLista> reservas = new ArrayList<AvaliacaoLista>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                reservas.add(new AvaliacaoLista(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return reservas;
    }
}
