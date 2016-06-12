package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 11/06/2016.
 */
public class Estado {
    public String sigla;
    public String descricao;

    public Estado(JSONObject object){
        try {
            this.sigla = object.getString("sigla");
            this.descricao = object.getString("estado");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Estado> fromJson(JSONArray jsonObjects) {
        ArrayList<Estado> estados = new ArrayList<Estado>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                estados.add(new Estado(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return estados;
    }
}
