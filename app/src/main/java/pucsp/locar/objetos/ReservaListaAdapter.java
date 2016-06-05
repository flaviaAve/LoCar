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
 * Created by Flavia on 29/05/2016.
 */
public class ReservaListaAdapter extends ArrayAdapter<ReservaLista>
{
    private static class ViewHolder {
        TextView tv_tipo;
        TextView tv_status;
        TextView tv_veiculo;
        TextView tv_periodo;
    }

    public ReservaListaAdapter(Context context, ArrayList<ReservaLista> reservas) {
        super(context, R.layout.list_item_reserva, reservas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReservaLista reserva = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_reserva, parent, false);
            viewHolder.tv_tipo = (TextView) convertView.findViewById(R.id.tv_tipo);
            viewHolder.tv_status = (TextView) convertView.findViewById(R.id.tv_status_reserva);
            viewHolder.tv_veiculo = (TextView) convertView.findViewById(R.id.tv_veiculo);
            viewHolder.tv_periodo = (TextView) convertView.findViewById(R.id.tv_periodo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_tipo.setText("Reserva: " + reserva.tipo);
        viewHolder.tv_status.setText("Status: " + reserva.status);
        viewHolder.tv_veiculo.setText("Veículo: " + reserva.veiculo);

        String dataInicio = reserva.data_inicio.substring(8, 10) + "/" + reserva.data_inicio.substring(5, 7) + "/" + reserva.data_inicio.substring(0, 4);
        String dataFim = reserva.data_fim.substring(8, 10) + "/" + reserva.data_fim.substring(5, 7) + "/" + reserva.data_fim.substring(0, 4);

        viewHolder.tv_periodo.setText(dataInicio + " - " + dataFim);
        viewHolder.tv_tipo.setTag(reserva.id_reserva);
        viewHolder.tv_status.setTag(reserva.id_reserva);
        viewHolder.tv_veiculo.setTag(reserva.id_reserva);
        viewHolder.tv_periodo.setTag(reserva.id_reserva);

        return convertView;
    }
}
