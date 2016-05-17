package pucsp.locar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Veiculos extends AppCompatActivity {

    ArrayAdapter<String> veiculosAdapter;
    ListView veiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<String> dadosVeiculos = new ArrayList<String>();
        dadosVeiculos.add("Corsa:1998:foto1.png");
        dadosVeiculos.add("KA:2010:foto2.png");
        dadosVeiculos.add("EcoSport:2014:foto3.png");
        dadosVeiculos.add("Corsa:2000:foto4.png");
        dadosVeiculos.add("Corsa:1998:foto1.png");
        dadosVeiculos.add("KA:2010:foto2.png");
        dadosVeiculos.add("EcoSport:2014:foto3.png");
        dadosVeiculos.add("Corsa:2000:foto4.png");
        dadosVeiculos.add("Corsa:1998:foto1.png");
        dadosVeiculos.add("KA:2010:foto2.png");
        dadosVeiculos.add("EcoSport:2014:foto3.png");
        dadosVeiculos.add("Corsa:2000:foto4.png");

        veiculosAdapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item_veiculo,
                R.id.liVeiculo,
                dadosVeiculos
        );

        veiculos = (ListView) findViewById(R.id.lvVeiculos);

        veiculos.setAdapter(veiculosAdapter);
    }

}
