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
    int selecao = NONE; // hold the answer picked by the user, initial is NONE(see below)
    public static final int NONE = 1000; // No answer selected
    public static final int ANSWER_ZERO_SELECTED = 0; // first answer selected
    public static final int ANSWER_ONE_SELECTED = 1; // first answer selected
    public static final int ANSWER_TWO_SELECTED = 2; // second answer selected
    public static final int ANSWER_THREE_SELECTED = 3; // third answer selected
    public static final int ANSWER_FOUR_SELECTED = 4; // forth answer selected
    public static final int ANSWER_FIVE_SELECTED = 5; // forth answer selected

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
