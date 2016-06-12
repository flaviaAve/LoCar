package pucsp.locar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pucsp.locar.conexoes.AlterarEnderecoRequisicao;
import pucsp.locar.conexoes.BuscarEnderecoRequisicao;
import pucsp.locar.conexoes.BuscarVeiculoRequisicao;
import pucsp.locar.conexoes.CidadesRequisicao;
import pucsp.locar.conexoes.EstadosRequisicao;
import pucsp.locar.conexoes.SalvarEnderecoRequisicao;
import pucsp.locar.conexoes.TipoEnderecoRequisicao;
import pucsp.locar.objetos.Cidade;
import pucsp.locar.objetos.CidadeAdapter;
import pucsp.locar.objetos.Endereco;
import pucsp.locar.objetos.Estado;
import pucsp.locar.objetos.EstadoAdapter;
import pucsp.locar.objetos.Modelo;
import pucsp.locar.objetos.TipoEndereco;
import pucsp.locar.objetos.TipoEnderecoAdapter;
import pucsp.locar.objetos.VeiculoCadastro;

public class EditarEndereco extends AppCompatActivity {
    private Endereco endereco;
    private Spinner s_estado;
    private Spinner s_cidade;
    private Spinner s_tipo_endereco;
    private ArrayAdapter<String> adapter;
    private ArrayList<Cidade> cidades;
    private ArrayList<Estado> estados;
    private ArrayList<TipoEndereco> tipos;
    private EditText e_logradouro;
    private EditText e_numero;
    private EditText e_complemento;
    private EditText e_bairro;
    private EditText e_cep;
    private String cidade;
    private String tipoEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_endereco);

        s_estado = (Spinner) findViewById(R.id.s_estado);
        s_cidade = (Spinner) findViewById(R.id.s_cidade);
        s_tipo_endereco = (Spinner) findViewById(R.id.s_tipo_endereco);
        e_logradouro = (EditText) findViewById(R.id.et_logradouro);
        e_numero = (EditText) findViewById(R.id.et_numero);
        e_complemento = (EditText) findViewById(R.id.et_complemento);
        e_bairro = (EditText) findViewById(R.id.et_bairro);
        e_cep = (EditText) findViewById(R.id.et_cep);

        buscarEstados();
        buscarTiposEnderecos();

        s_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                buscarCidadesPorEstado(estados.get(position).sigla);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                s_cidade.setAdapter(null);
            }
        });

        s_cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                cidade = cidades.get(position).codCidade;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                cidade = null;
            }
        });

        s_tipo_endereco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                tipoEndereco = tipos.get(position).codTipo;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                tipoEndereco = null;
            }
        });

        buscarEndereco();
    }

    private void buscarEndereco()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        endereco = new Endereco(jsonResponse);
                        preencherCampos();
                    } else {
                        endereco = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        BuscarEnderecoRequisicao requisicao = new BuscarEnderecoRequisicao(RecursosSharedPreferences.getUserID(EditarEndereco.this), responseListener);
        RequestQueue queue = Volley.newRequestQueue(EditarEndereco.this);
        queue.add(requisicao);
    }

    private void preencherCampos()
    {
        e_logradouro.setText(endereco.logradouro);
        e_numero.setText(endereco.numero);
        e_complemento.setText(endereco.complemento);
        e_cep.setText(endereco.cep);
        e_bairro.setText(endereco.bairro);

        if(s_estado != null && s_estado.getCount() > 0)
        {
            s_estado.setSelection(buscarEstado(endereco.sigla));
        }

        if(s_cidade != null && s_cidade.getCount() > 0)
        {
            s_cidade.setSelection(buscarCidade(endereco.codCidade));
        }

        if(s_tipo_endereco != null && s_tipo_endereco.getCount() > 0)
        {
            s_tipo_endereco.setSelection(buscarTipoEndereco(endereco.codTipo));
        }
    }

    private int buscarEstado(String sigla)
    {
        for(Estado estado : estados)
        {
            if (estado.sigla.equals(sigla))
            {
                return estados.indexOf(estado);
            }
        }
        return 0;
    }

    private int buscarCidade(String codCidade)
    {
        for(Cidade cidade : cidades)
        {
            if (cidade.codCidade.equals(codCidade))
            {
                return cidades.indexOf(cidade);
            }
        }
        return 0;
    }

    private int buscarTipoEndereco(String codTipo)
    {
        for(TipoEndereco tipo : tipos)
        {
            if (tipo.codTipo.equals(codTipo))
            {
                return tipos.indexOf(tipo);
            }
        }
        return 0;
    }

    private void buscarEstados()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray estadosJSON = jsonResponse.getJSONArray("estado");
                        estados = Estado.fromJson(estadosJSON);
                        ArrayList<String> descricoes = new ArrayList<String>();
                        for (Estado estado : estados) {
                            descricoes.add(estado.descricao);
                        }
                        adapter = new EstadoAdapter(EditarEndereco.this, descricoes);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_estado.setAdapter(adapter);
                        if (endereco != null) {
                            s_estado.setSelection(buscarEstado(endereco.sigla));
                        }
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        EstadosRequisicao requisicao = new EstadosRequisicao(responseListener);
        RequestQueue queue = Volley.newRequestQueue(EditarEndereco.this);
        queue.add(requisicao);
    }

    private void buscarCidadesPorEstado(String sigla)
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray cidadeJSON = jsonResponse.getJSONArray("cidade");
                        cidades = Cidade.fromJson(cidadeJSON);
                        ArrayList<String> descricoes = new ArrayList<String>();
                        for (Cidade cidade : cidades) {
                            descricoes.add(cidade.descricao);
                        }
                        adapter = new CidadeAdapter(EditarEndereco.this, descricoes);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_cidade.setAdapter(adapter);
                        if (endereco != null) {
                            s_cidade.setSelection(buscarCidade(endereco.codCidade));
                        }
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        CidadesRequisicao requisicao = new CidadesRequisicao(sigla, responseListener);
        RequestQueue queue = Volley.newRequestQueue(EditarEndereco.this);
        queue.add(requisicao);
    }

    private void buscarTiposEnderecos()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray tiposJSON = jsonResponse.getJSONArray("tipo");
                        tipos = TipoEndereco.fromJson(tiposJSON);
                        ArrayList<String> descricoes = new ArrayList<String>();
                        for (TipoEndereco tipo : tipos) {
                            descricoes.add(tipo.descricao);
                        }
                        adapter = new TipoEnderecoAdapter(EditarEndereco.this, descricoes);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        s_tipo_endereco.setAdapter(adapter);
                        if (endereco != null) {
                            s_tipo_endereco.setSelection(buscarTipoEndereco(endereco.codTipo));
                        }
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        TipoEnderecoRequisicao requisicao = new TipoEnderecoRequisicao(responseListener);
        RequestQueue queue = Volley.newRequestQueue(EditarEndereco.this);
        queue.add(requisicao);
    }

    public void salvar(View v) {
        boolean erro = false;

        if (cidade.isEmpty()) {
            erro = true;
        }

        if (tipoEndereco.isEmpty()) {
            erro = true;
        }

        String logradouro = e_logradouro.getText().toString();
        if (logradouro.isEmpty()) {
            erro = true;
            e_logradouro.setError("Logradouro é obrigatório!");
        }

        String numero = e_numero.getText().toString();
        if (numero.isEmpty()) {
            erro = true;
            e_numero.setError("Número é obrigatório!");
        }

        String cep = e_cep.getText().toString();
        if (cep.isEmpty()) {
            erro = true;
            e_cep.setError("CEP é obrigatório!");
        }

        String bairro = e_bairro.getText().toString();
        if (bairro.isEmpty()) {
            erro = true;
            e_bairro.setError("Bairro é obrigatório!");
        }

        String complemento = e_complemento.getText().toString();

        if (!erro) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            Intent i = new Intent(EditarEndereco.this, EditarTelefone.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(EditarEndereco.this, EditarTelefone.class);
                            startActivity(i);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            AlterarEnderecoRequisicao salvar = new AlterarEnderecoRequisicao(String.valueOf(endereco.id_endereco), tipoEndereco, logradouro, numero, complemento, cep, bairro, cidade, responseListener);
            RequestQueue queue = Volley.newRequestQueue(EditarEndereco.this);
            queue.add(salvar);
        }
    }
}
