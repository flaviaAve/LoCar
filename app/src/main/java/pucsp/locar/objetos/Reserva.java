package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pucsp.locar.RecursosSharedPreferences;

/**
 * Created by Flavia on 29/05/2016.
 */
public class Reserva {
    public int id_reserva;
    public String status;
    public int statusID;
    public String modelo;
    public String montadora;
    public String preco;
    public String locador;
    public String locatario;
    public String locadorID;
    public String locatarioID;
    public String locadorEmail;
    public String locatarioEmail;
    public String avaliacaoLocador;
    public String avaliacaoLocatario;
    public String imagem_veiculo;
    public String data_inicio;
    public String data_fim;

    public Reserva(JSONObject object){
        try {
            this.id_reserva = object.getInt("codigo");
            this.status = object.getString("status");
            this.statusID = object.getInt("statusID");
            this.modelo = object.getString("modelo");
            this.montadora = object.getString("montadora");
            this.preco = object.getString("preco");
            this.imagem_veiculo = object.getString("imagem_veiculo");
            this.locador = object.getString("locador");
            this.locatario = object.getString("locatario");
            this.locadorID = object.getString("locadorID");
            this.locatarioID = object.getString("locatarioID");
            this.locadorEmail = object.getString("locadorEmail");
            this.locatarioEmail = object.getString("locatarioEmail");
            this.avaliacaoLocador = object.getString("pontuacaoLocador");
            this.avaliacaoLocatario = object.getString("pontuacaoLocatario");
            this.data_inicio = object.getString("data_inicio");
            this.data_fim = object.getString("data_fim");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Reserva> fromJson(JSONArray jsonObjects) {
        ArrayList<Reserva> reservas = new ArrayList<Reserva>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                reservas.add(new Reserva(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return reservas;
    }
}
