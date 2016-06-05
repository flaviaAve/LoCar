package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 04/06/2016.
 */
public class Questao {
    public int id_questionario;
    public String textoQuestao;

    public Questao(JSONObject object){
        try {
            this.id_questionario = object.getInt("codigo");
            this.textoQuestao = object.getString("questao");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Questao> fromJson(JSONArray jsonObjects) {
        ArrayList<Questao> questoes = new ArrayList<Questao>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                questoes.add(new Questao(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return questoes;
    }
}
