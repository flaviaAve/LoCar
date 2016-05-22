package pucsp.locar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import pucsp.locar.conexoes.VeiculoImagemRequisicao;
import pucsp.locar.objetos.Veiculo;
import pucsp.locar.objetos.VeiculoAdapter;

public class Principal extends AppCompatActivity {

    ListView lista;
    JSONArray veiculosJSON;
    VeiculoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista = (ListView) findViewById(R.id.lvVeiculos);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray veiculosJSON = jsonResponse.getJSONArray("veiculo");
                        adapter = new VeiculoAdapter(Principal.this, Veiculo.fromJson(veiculosJSON));
                        lista.setAdapter(adapter);
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        VeiculoImagemRequisicao veiculoRequisicao = new VeiculoImagemRequisicao(responseListener);
        RequestQueue queue = Volley.newRequestQueue(Principal.this);
        queue.add(veiculoRequisicao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);

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
                e.printStackTrace();
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_buscar) {
            return true;
        }

        if (id == R.id.menu_veiculo) {
            Intent i = new Intent(Principal.this, Veiculos.class);
            startActivity(i);
            finish();
        }

        if (id == R.id.menu_logout) {
            logout(item.getActionView());
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(View v)
    {
        SalvarSharedPreferences.clearUserName(Principal.this);
        LoginManager.getInstance().logOut();
        Intent i = new Intent(Principal.this, Inicial.class);
        startActivity(i);
        finish();
    }
}
