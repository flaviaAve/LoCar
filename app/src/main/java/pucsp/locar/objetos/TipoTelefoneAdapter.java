package pucsp.locar.objetos;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Flavia on 11/06/2016.
 */
public class TipoTelefoneAdapter extends ArrayAdapter<String> {
    public TipoTelefoneAdapter(Context context, ArrayList<String> tipos) {
        super(context, android.R.layout.simple_spinner_item, tipos);
    }
}
