package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 22/05/2016.
 */
public class Modelo {
    public String codModelo;
    public String descricao;

    public Modelo(JSONObject object){
        try {
            this.codModelo = object.getString("codModelo");
            this.descricao = object.getString("descricao");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Modelo> fromJson(JSONArray jsonObjects) {
        ArrayList<Modelo> modelos = new ArrayList<Modelo>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                modelos.add(new Modelo(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return modelos;
    }
}
