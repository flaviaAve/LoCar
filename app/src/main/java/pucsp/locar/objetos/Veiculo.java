package pucsp.locar.objetos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pucsp.locar.R;

/**
 * Created by Flavia on 21/05/2016.
 */
public class Veiculo {
    public String modelo;
    public String usuarioFoto;
    public String veiculoFoto;

    public Veiculo(JSONObject object){
        try {
            this.modelo = object.getString("modelo");
            this.usuarioFoto = object.getString("usuario_imagem");
            this.veiculoFoto = object.getString("veiculo_imagem");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Veiculo> fromJson(JSONArray jsonObjects) {
        ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                veiculos.add(new Veiculo(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return veiculos;
    }
}


