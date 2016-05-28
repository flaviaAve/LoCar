package pucsp.locar.objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Flavia on 28/05/2016.
 */
public class Usuario {
    public int id_usuario;
    public String login;
    public String email;
    public String senha;
    public String nome;
    public String dt_nascimento;
    public String imagem_perfil;
    public String tipo_perfil;

    public Usuario(JSONObject object){
        try {
            this.id_usuario = object.getInt("usuarioID");
            this.login = object.getString("login");
            this.email = object.getString("email");
            this.senha = object.getString("senha");
            this.nome = object.getString("nome");
            this.dt_nascimento = object.getString("dt_nascimento");
            this.imagem_perfil = object.getString("imagem_perfil");
            this.tipo_perfil = object.getString("tipo_perfil");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Usuario> fromJson(JSONArray jsonObjects) {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                usuarios.add(new Usuario(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return usuarios;
    }
}
