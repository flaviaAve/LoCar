package pucsp.locar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import pucsp.locar.assincrono.CarregarMeusVeiculos;

public class Veiculos extends AppCompatActivity {
    ListView lv_meus_veiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Veiculos.this, CadastroVeiculo.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_meus_veiculos = (ListView) findViewById(R.id.lv_meus_veiculos);

        new CarregarMeusVeiculos(this, lv_meus_veiculos).execute();
    }

    public void exibirVeiculo(View v)
    {
        Intent i = new Intent(Veiculos.this, MeuVeiculo.class);

        Bundle bundle = new Bundle();
        bundle.putString("veiculoID", v.getTag().toString());

        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Veiculos.this, Principal.class);
        startActivity(i);
    }

}
