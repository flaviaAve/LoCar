package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 29/05/2016.
 */
public class ReservaLista {
    public int id_reserva;
    public String status;
    public String veiculo;
    public String data_inicio;
    public String data_fim;
    public String tipo;

    public ReservaLista(JSONObject object){
        try {
            this.id_reserva = object.getInt("codReserva");
            this.status = object.getString("status");
            this.veiculo = object.getString("veiculo");
            this.data_inicio = object.getString("data_inicio");
            this.data_fim = object.getString("data_fim");
            this.tipo = object.getString("tipo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ReservaLista> fromJson(JSONArray jsonObjects) {
        ArrayList<ReservaLista> reservas = new ArrayList<ReservaLista>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                reservas.add(new ReservaLista(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return reservas;
    }
}
