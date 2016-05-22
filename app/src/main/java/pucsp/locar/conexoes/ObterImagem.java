package pucsp.locar.conexoes;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

import pucsp.locar.R;

/**
 * Created by Flavia on 22/05/2016.
 */
public class ObterImagem extends AsyncTask<String, Void, Drawable> {

    private ImageView mImageView;

    public ObterImagem(ImageView imageView)
    {
        mImageView = imageView;
    }

    @Override
    protected Drawable doInBackground(String... params) {
        try {
            InputStream is = (InputStream) new URL(params[0]).getContent();
            Drawable d = Drawable.createFromStream(is, params[1]);
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Drawable result)
    {
        mImageView.setImageDrawable(result);
    }
}
