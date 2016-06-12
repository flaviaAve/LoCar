package pucsp.locar.objetos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;

import pucsp.locar.R;

/**
 * Created by Flavia on 04/06/2016.
 */
public class QuestaoAdapter extends BaseAdapter
{
    LayoutInflater inflater;

    ArrayList<Questao> list;

    public QuestaoAdapter(Context context, ArrayList<Questao> data) {
        inflater = LayoutInflater.from(context);
        this.list = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View v = convertView;

        if (convertView == null) {
            v = inflater.inflate(R.layout.list_item_questao, parent, false);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);

            viewHolder.rg_respostas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                public void onCheckedChanged(RadioGroup group,
                                             int checkedId) {
                    Integer pos = (Integer) group.getTag();
                    Questao element = list.get(pos);
                    switch (checkedId) {
                        case R.id.rb0:
                            element.selecao = Questao.ANSWER_ZERO_SELECTED;
                            break;
                        case R.id.rb1:
                            element.selecao = Questao.ANSWER_ONE_SELECTED;
                            break;
                        case R.id.rb2:
                            element.selecao = Questao.ANSWER_TWO_SELECTED;
                            break;
                        case R.id.rb3:
                            element.selecao = Questao.ANSWER_THREE_SELECTED;
                            break;
                        case R.id.rb4:
                            element.selecao = Questao.ANSWER_FOUR_SELECTED;
                            break;
                        case R.id.rb5:
                            element.selecao = Questao.ANSWER_FIVE_SELECTED;
                            break;
                        default:
                            element.selecao = Questao.NONE;
                    }

                }
            });
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.rg_respostas.setTag(new Integer(position)); // I passed the current
        // position as a tag

        viewHolder.tv_questao.setText(list.get(position).textoQuestao); // Set the question body

        if (list.get(position).selecao != Questao.NONE) {
            RadioButton r = (RadioButton) viewHolder.rg_respostas.getChildAt(list.get(position).selecao);
            r.setChecked(true);
        } else {
            viewHolder.rg_respostas.clearCheck();

        }

        return v;
    }

    class ViewHolder {
        TextView tv_questao = null;
        RadioGroup rg_respostas;

        ViewHolder(View v) {
            tv_questao = (TextView) v.findViewById(R.id.tv_questao);
            rg_respostas = (RadioGroup) v.findViewById(R.id.rg_resultado);
        }

    }
}
