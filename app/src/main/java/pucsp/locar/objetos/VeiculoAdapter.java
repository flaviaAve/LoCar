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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import pucsp.locar.R;
import pucsp.locar.conexoes.ObterImagem;

/**
 * Created by Flavia on 21/05/2016.
 */
public class VeiculoAdapter extends ArrayAdapter<Veiculo>
{
    private static class ViewHolder {
        TextView tv_veiculo;
        ImageView iv_veiculo;
    }

    public VeiculoAdapter(Context context, ArrayList<Veiculo> veiculos) {
        super(context, R.layout.list_item_veiculo, veiculos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Veiculo veiculo = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_veiculo, parent, false);
            viewHolder.tv_veiculo = (TextView) convertView.findViewById(R.id.tvVeiculo);
            viewHolder.iv_veiculo = (ImageView) convertView.findViewById(R.id.ivVeiculo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_veiculo.setText(veiculo.modelo);

        try {
            String nome = veiculo.veiculoFoto.substring(veiculo.veiculoFoto.lastIndexOf("/") + 1);
            new ObterImagem(viewHolder.iv_veiculo).execute(veiculo.veiculoFoto, nome);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return convertView;
    }
}
