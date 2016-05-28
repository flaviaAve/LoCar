package pucsp.locar.pucsp.locar.assincrono;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import pucsp.locar.conexoes.SalvarImagemRequisicao;

/**
 * Created by Flavia on 26/05/2016.
 */
public class EnviarImagem extends AsyncTask<Void, Void, Void> {
    Bitmap imagem;
    Uri imagem_uri;
    String nome_imagem;
    String caminho;
    Context context;

    public EnviarImagem(Context context, String nome_imagem, String caminho, Bitmap imagem)
    {
        this.context = context;
        this.imagem = imagem;
        this.caminho = caminho;
        this.nome_imagem = nome_imagem;
    }

    public EnviarImagem(Context context, String nome_imagem, String caminho, Uri imagem)
    {
        this.context = context;
        this.imagem_uri = imagem;
        this.caminho = caminho;
        this.nome_imagem = nome_imagem;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (imagem == null)
        {
            try {
                imagem = Picasso.with(context).load(imagem_uri).get();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String imagemCondificada = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        SalvarImagemRequisicao requisicao = new SalvarImagemRequisicao(caminho, nome_imagem, imagemCondificada);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(requisicao);
        return null;
    }
}
