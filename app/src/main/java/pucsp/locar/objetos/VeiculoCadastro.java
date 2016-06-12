package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 29/05/2016.
 */
public class VeiculoCadastro {
    public int id_veiculo;
    public String modeloID;
    public String montadoraID;
    public String preco;
    public String placa;
    public String renavam;
    public String imagem_veiculo;

    public VeiculoCadastro(JSONObject object){
        try {
            this.id_veiculo = object.getInt("codigo");
            this.modeloID = object.getString("modelo");
            this.montadoraID = object.getString("montadora");
            this.preco = object.getString("preco");
            this.placa = object.getString("placa");
            this.renavam = object.getString("renavam");
            this.imagem_veiculo = object.getString("imagem_veiculo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<VeiculoCadastro> fromJson(JSONArray jsonObjects) {
        ArrayList<VeiculoCadastro> veiculos = new ArrayList<VeiculoCadastro>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                veiculos.add(new VeiculoCadastro(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return veiculos;
    }
}
