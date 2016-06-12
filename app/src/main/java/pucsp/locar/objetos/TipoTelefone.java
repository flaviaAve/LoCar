package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 11/06/2016.
 */
public class TipoTelefone {
    public String codTipo;
    public String descricao;

    public TipoTelefone(JSONObject object){
        try {
            this.codTipo = object.getString("codTipo");
            this.descricao = object.getString("descricao");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<TipoTelefone> fromJson(JSONArray jsonObjects) {
        ArrayList<TipoTelefone> tipos = new ArrayList<TipoTelefone>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                tipos.add(new TipoTelefone(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tipos;
    }
}
