package pucsp.locar.pucsp.locar.assincrono;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pucsp.locar.Principal;
import pucsp.locar.R;
import pucsp.locar.objetos.VeiculoAdapter;

/**
 * Created by Flavia on 22/05/2016.
 */
public class ObterImagem extends AsyncTask<ImageView, Void, Bitmap> {

    private ImageView mImageView;
    private Bitmap bmp =null;
    private String url = "";
    Context context;

    public ObterImagem(String url, Context context)
    {
        this.url = url;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(ImageView... imageViews) {
        this.mImageView = imageViews[0];
        return download_Image(url);
    }

    @Override
    protected void onPostExecute(Bitmap result) {

        mImageView.setImageBitmap(result);
    }

    private Bitmap download_Image(String url) {

        try{
            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);

            if (null != bmp)
                return bmp;

        }catch(Exception e){}
        return bmp;
    }
}
