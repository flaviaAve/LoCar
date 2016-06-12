package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 11/06/2016.
 */
public class Cidade {
    public String codCidade;
    public String descricao;

    public Cidade(JSONObject object){
        try {
            this.codCidade = object.getString("codCidade");
            this.descricao = object.getString("cidade");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Cidade> fromJson(JSONArray jsonObjects) {
        ArrayList<Cidade> cidades = new ArrayList<Cidade>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                cidades.add(new Cidade(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return cidades;
    }
}
