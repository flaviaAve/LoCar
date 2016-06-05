package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 28/05/2016.
 */
public class VeiculoReserva {
    public int id_veiculo;
    public String modelo;
    public String montadora;
    public String preco;
    public String proprietario;
    public String avaliacao;
    public String imagem_veiculo;

    public VeiculoReserva(JSONObject object){
        try {
            this.id_veiculo = object.getInt("codigo");
            this.modelo = object.getString("modelo");
            this.montadora = object.getString("montadora");
            this.preco = object.getString("preco");
            this.imagem_veiculo = object.getString("imagem_veiculo");
            this.proprietario = object.getString("proprietario");
            this.avaliacao = object.getString("pontuacao");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<VeiculoReserva> fromJson(JSONArray jsonObjects) {
        ArrayList<VeiculoReserva> veiculos = new ArrayList<VeiculoReserva>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                veiculos.add(new VeiculoReserva(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return veiculos;
    }
}
