package pucsp.locar.pucsp.locar.assincrono;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Base64;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import pucsp.locar.Principal;
import pucsp.locar.SalvarSharedPreferences;
import pucsp.locar.conexoes.LoginRequisicao;
import pucsp.locar.conexoes.SalvarImagemRequisicao;

/**
 * Created by Flavia on 26/05/2016.
 */
public class EnviarImagem extends AsyncTask<Void, Void, Void> {
    Bitmap imagem;
    String nome_imagem;
    Context context;

    public EnviarImagem(Context context, String nome_imagem, Bitmap imagem)
    {
        this.context = context;
        this.imagem = imagem;
        this.nome_imagem = nome_imagem;
    }

    @Override
    protected Void doInBackground(Void... params) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String imagemCondificada = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        SalvarImagemRequisicao requisicao = new SalvarImagemRequisicao("usuarios_imagens/", nome_imagem, imagemCondificada);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(requisicao);
        return null;
    }
}
