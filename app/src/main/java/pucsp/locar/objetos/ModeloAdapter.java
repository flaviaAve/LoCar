package pucsp.locar.objetos;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Flavia on 22/05/2016.
 */
public class ModeloAdapter extends ArrayAdapter<String> {
    public ModeloAdapter(Context context, ArrayList<String> modelos) {
        super(context, android.R.layout.simple_spinner_item, modelos);
    }
}
