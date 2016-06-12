package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 11/06/2016.
 */
public class Endereco {
    public int id_endereco;
    public String logradouro;
    public String numero;
    public String complemento;
    public String cep;
    public String bairro;
    public String codTipo;
    public String codCidade;
    public String sigla;

    public Endereco(JSONObject object){
        try {
            this.id_endereco = object.getInt("codigo");
            this.logradouro = object.getString("logradouro");
            this.numero = object.getString("numero");
            this.complemento = object.getString("complemento");
            this.cep = object.getString("cep");
            this.bairro = object.getString("bairro");
            this.codCidade = object.getString("codCidade");
            this.sigla = object.getString("sigla");
            this.codTipo = object.getString("codTipo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Endereco> fromJson(JSONArray jsonObjects) {
        ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                enderecos.add(new Endereco(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return enderecos;
    }
}
