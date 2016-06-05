package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 04/06/2016.
 */
public class Avaliacao {
    public int id_avaliacao;
    public String usuario;
    public String pontuacao;
    public String comentario;

    public Avaliacao(JSONObject object){
        try {
            this.id_avaliacao = object.getInt("codigo");
            this.usuario = object.getString("usuario");
            this.pontuacao = object.getString("pontuacao");
            this.comentario = object.getString("comentario");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Avaliacao> fromJson(JSONArray jsonObjects) {
        ArrayList<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                avaliacoes.add(new Avaliacao(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return avaliacoes;
    }
}
