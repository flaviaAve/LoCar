package pucsp.locar.objetos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import pucsp.locar.R;

/**
 * Created by Flavia on 04/06/2016.
 */
public class AvaliacaoListaAdapter extends ArrayAdapter<AvaliacaoLista>
{
    private static class ViewHolder {
        TextView tv_veiculo;
        TextView tv_periodo;
        ImageButton bt_avaliar;
    }

    public AvaliacaoListaAdapter(Context context, ArrayList<AvaliacaoLista> avaliacoes) {
        super(context, R.layout.list_item_avaliacao, avaliacoes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AvaliacaoLista avaliacao = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_avaliacao, parent, false);
            viewHolder.tv_veiculo = (TextView) convertView.findViewById(R.id.tv_veiculo_locacao);
            viewHolder.tv_periodo = (TextView) convertView.findViewById(R.id.tv_data_locacao);
            viewHolder.bt_avaliar = (ImageButton) convertView.findViewById(R.id.bt_avaliar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String veiculo = "";
        if (avaliacao.tipo_questionario.equals("1"))
        {
            veiculo = avaliacao.montadora + " - " + avaliacao.modelo + ". Locador: " + avaliacao.usuario;
        }
        else
        {
            veiculo = avaliacao.montadora + " - " + avaliacao.modelo + ". Locatário: " + avaliacao.usuario;
        }
        viewHolder.tv_veiculo.setText(veiculo);
        String dataInicio = avaliacao.data_inicio.substring(8, 10) + "/" + avaliacao.data_inicio.substring(5, 7) + "/" + avaliacao.data_inicio.substring(0, 4);
        String dataFim = avaliacao.data_fim.substring(8, 10) + "/" + avaliacao.data_fim.substring(5, 7) + "/" + avaliacao.data_fim.substring(0, 4);

        viewHolder.tv_periodo.setText(dataInicio + " - " + dataFim);
        viewHolder.bt_avaliar.setTag(R.string.QUESTIONARIO_ID, avaliacao.tipo_questionario);
        viewHolder.bt_avaliar.setTag(R.string.RESERVA_ID, avaliacao.id_reserva);

        return convertView;
    }
}
