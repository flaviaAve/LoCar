package pucsp.locar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.lang.reflect.Method;

import pucsp.locar.pucsp.locar.assincrono.CarregarVeiculos;

public class Principal extends AppCompatActivity {

    private ListView lista;
    private Toolbar toolbar;
    private TextView sem_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_principal);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista = (ListView) findViewById(R.id.lvVeiculos);
        sem_registro = (TextView) findViewById(R.id.tv_sem_registro);

        Intent intent = getIntent();
        String montadora = null;
        String modelo = null;
        String preco = null;

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            montadora = bundle.getString("montadora");
            modelo = bundle.getString("modelo");
            preco = bundle.getString("preco");
        }

        new CarregarVeiculos(this, RecursosSharedPreferences.getUserID(this), montadora, modelo, preco, lista, sem_registro).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_buscar) {
            Intent i = new Intent(Principal.this, BuscarVeiculo.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.menu_veiculo) {
            Intent i = new Intent(Principal.this, Veiculos.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.menu_reserva) {
            Intent i = new Intent(Principal.this, Reservas.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.menu_cadastro)
        {
            Intent i = new Intent(Principal.this, MeuCadastro.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.menu_avaliacao)
        {
            Intent i = new Intent(Principal.this, Avaliacoes.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.menu_logout) {
            logout(item.getActionView());
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        // To show icons in the actionbar's overflow menu:
        // http://stackoverflow.com/questions/18374183/how-to-show-icons-in-overflow-menu-in-actionbar
        //if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
            try{
                Method m = menu.getClass().getDeclaredMethod(
                        "setOptionalIconsVisible", Boolean.TYPE);
                m.setAccessible(true);
                m.invoke(menu, true);
            }
            catch(NoSuchMethodException e){
                e.printStackTrace();
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        //}

        return super.onCreateOptionsMenu(menu);
    }

    public void logout(View v)
    {
        RecursosSharedPreferences.clearUserName(Principal.this);
        LoginManager.getInstance().logOut();
        Intent i = new Intent(Principal.this, Inicial.class);
        startActivity(i);
        finish();
    }

    public void exibirVeiculo(View v)
    {
        Intent i = new Intent(Principal.this, VisualizarVeiculo.class);

        Bundle bundle = new Bundle();
        bundle.putString("veiculoID", v.getTag().toString());

        i.putExtras(bundle);
        startActivity(i);
    }
}
