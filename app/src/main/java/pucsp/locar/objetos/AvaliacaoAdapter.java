package pucsp.locar.objetos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pucsp.locar.R;

/**
 * Created by Flavia on 04/06/2016.
 */
public class AvaliacaoAdapter extends ArrayAdapter<Avaliacao>
{
    private static class ViewHolder {
        TextView tv_pontuacao;
        TextView tv_comentario;
        ImageView iv_estrela;
    }

    public AvaliacaoAdapter(Context context, ArrayList<Avaliacao> avaliacoes) {
        super(context, R.layout.list_item_minha_avaliacao, avaliacoes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Avaliacao avaliacao = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_minha_avaliacao, parent, false);
            viewHolder.tv_pontuacao = (TextView) convertView.findViewById(R.id.tv_pontuacao);
            viewHolder.tv_comentario = (TextView) convertView.findViewById(R.id.tv_comentario);
            viewHolder.iv_estrela = (ImageView) convertView.findViewById(R.id.iv_estrela);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tv_pontuacao.setText("Pontuação: " + avaliacao.pontuacao.replace(".", ","));
        viewHolder.tv_comentario.setText(avaliacao.usuario + ": " + avaliacao.comentario);
        float pontuacao = Float.parseFloat(avaliacao.pontuacao);
        if (pontuacao < 1)
        {
            viewHolder.iv_estrela.setImageResource(R.mipmap.ic_estrela0);
        }
        else if (pontuacao < 2 && pontuacao >= 1)
        {
            viewHolder.iv_estrela.setImageResource(R.mipmap.ic_estrela1);
        }
        else if (pontuacao < 3 && pontuacao >= 2)
        {
            viewHolder.iv_estrela.setImageResource(R.mipmap.ic_estrela2);
        }
        else if (pontuacao < 4 && pontuacao >= 3)
        {
            viewHolder.iv_estrela.setImageResource(R.mipmap.ic_estrela3);
        }
        else if (pontuacao < 5 && pontuacao >= 4)
        {
            viewHolder.iv_estrela.setImageResource(R.mipmap.ic_estrela4);
        }
        else if (pontuacao >= 5)
        {
            viewHolder.iv_estrela.setImageResource(R.mipmap.ic_estrela5);
        }

        return convertView;
    }
}
