package pucsp.locar.objetos;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Flavia on 22/05/2016.
 */
public class MontadoraAdapter extends ArrayAdapter<String> {

    public MontadoraAdapter(Context context, ArrayList<String> montadoras) {
        super(context, android.R.layout.simple_spinner_item, montadoras);
    }
}
