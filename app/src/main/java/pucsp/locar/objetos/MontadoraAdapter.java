package pucsp.locar.objetos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pucsp.locar.R;
import pucsp.locar.conexoes.ObterImagem;

/**
 * Created by Flavia on 22/05/2016.
 */
public class MontadoraAdapter extends ArrayAdapter<String> {

    public MontadoraAdapter(Context context, ArrayList<String> montadoras) {
        super(context, android.R.layout.simple_spinner_item, montadoras);
    }
}
