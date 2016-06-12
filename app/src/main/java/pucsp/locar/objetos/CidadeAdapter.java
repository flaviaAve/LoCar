package pucsp.locar.objetos;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

/**
 * Created by Flavia on 11/06/2016.
 */
public class CidadeAdapter extends ArrayAdapter<String> {
    public CidadeAdapter(Context context, ArrayList<String> cidades) {
        super(context, android.R.layout.simple_spinner_item, cidades);
    }
}
