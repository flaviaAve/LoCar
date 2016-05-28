package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 22/05/2016.
 */
public class Montadora {
    public String codMontadora;
    public String descricao;

    public Montadora(JSONObject object){
        try {
            this.codMontadora = object.getString("codMontadora");
            this.descricao = object.getString("descricao");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Montadora> fromJson(JSONArray jsonObjects) {
        ArrayList<Montadora> montadoras = new ArrayList<Montadora>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                montadoras.add(new Montadora(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return montadoras;
    }
}
