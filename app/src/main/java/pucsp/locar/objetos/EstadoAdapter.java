package pucsp.locar.objetos;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

/**
 * Created by Flavia on 11/06/2016.
 */
public class EstadoAdapter extends ArrayAdapter<String> {
    public EstadoAdapter(Context context, ArrayList<String> estados) {
        super(context, android.R.layout.simple_spinner_item, estados);
    }
}
