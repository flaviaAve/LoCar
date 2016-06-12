package pucsp.locar.objetos;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Flavia on 11/06/2016.
 */
public class TipoEnderecoAdapter extends ArrayAdapter<String> {
    public TipoEnderecoAdapter(Context context, ArrayList<String> tipos) {
        super(context, android.R.layout.simple_spinner_item, tipos);
    }
}
