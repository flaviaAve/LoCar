package pucsp.locar.objetos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pucsp.locar.R;

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

        String conteudo = veiculo.montadora + " - " + veiculo.modelo + " (R$ " + String.valueOf(veiculo.preco_minuto).replace(".", ",") + "/min)";
        viewHolder.tv_veiculo.setText(conteudo);
        viewHolder.tv_veiculo.setTag(veiculo.id_veiculo);
        Picasso.with(getContext()).load(veiculo.veiculoFoto)
                .into(viewHolder.iv_veiculo);
        viewHolder.iv_veiculo.setTag(veiculo.id_veiculo);

        return convertView;
    }
}


