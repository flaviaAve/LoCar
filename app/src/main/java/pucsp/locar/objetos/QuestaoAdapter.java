package pucsp.locar.objetos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pucsp.locar.R;

/**
 * Created by Flavia on 04/06/2016.
 */
public class QuestaoAdapter extends ArrayAdapter<Questao>
{
    private static class ViewHolder {
        TextView tv_questao;
        RadioGroup rg_respostas;
    }

    public QuestaoAdapter(Context context, ArrayList<Questao> questoes) {
        super(context, R.layout.list_item_questao, questoes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Questao questao = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_questao, parent, false);
            viewHolder.tv_questao = (TextView) convertView.findViewById(R.id.tv_questao);
            viewHolder.rg_respostas = (RadioGroup) convertView.findViewById(R.id.rg_resultado);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tv_questao.setText(questao.textoQuestao);
        viewHolder.tv_questao.setTag(questao.id_questionario);

        return convertView;
    }
}
