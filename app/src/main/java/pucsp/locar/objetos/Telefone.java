package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 11/06/2016.
 */
public class Telefone {
    public int id_telefone;
    public String ddd;
    public String numero;
    public String codTipo;

    public Telefone(JSONObject object){
        try {
            this.id_telefone = object.getInt("codigo");
            this.ddd = object.getString("ddd");
            this.numero = object.getString("numero");
            this.codTipo = object.getString("codTipo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Telefone> fromJson(JSONArray jsonObjects) {
        ArrayList<Telefone> telefones = new ArrayList<Telefone>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                telefones.add(new Telefone(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return telefones;
    }
}
