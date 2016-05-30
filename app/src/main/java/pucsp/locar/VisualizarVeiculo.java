package pucsp.locar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import pucsp.locar.conexoes.BuscarUsuarioRequisicao;
import pucsp.locar.conexoes.BuscarVeiculoReservaRequisicao;
import pucsp.locar.conexoes.ReservarVeiculoRequisicao;
import pucsp.locar.objetos.Usuario;
import pucsp.locar.objetos.Veiculo;
import pucsp.locar.objetos.VeiculoReserva;

public class VisualizarVeiculo extends AppCompatActivity {
    private ImageView iv_veiculo;
    private TextView tv_modelo;
    private TextView tv_preco;
    private TextView tv_proprietario;
    private TextView tv_avaliacao;
    private RelativeLayout layout_carregando;
    private String veiculoID;
    private VeiculoReserva veiculo;
    private ProgressBar pb_loading;
    private EditText data_inicio;
    private EditText data_fim;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_veiculo);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            veiculoID = bundle.getString("veiculoID");
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        veiculo = new VeiculoReserva(jsonResponse);
                        preencherCampos();
                    } else {
                        veiculo = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        BuscarVeiculoReservaRequisicao requisicao = new BuscarVeiculoReservaRequisicao(veiculoID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(VisualizarVeiculo.this);
        queue.add(requisicao);

        iv_veiculo = (ImageView) findViewById(R.id.iv_foto_veiculo);
        tv_modelo = (TextView) findViewById(R.id.tv_montadora_modelo);
        tv_preco = (TextView) findViewById(R.id.tv_preco_aluguel);
        tv_proprietario = (TextView) findViewById(R.id.tv_proprietario);
        tv_avaliacao = (TextView) findViewById(R.id.tv_avaliacao_proprietario);
        data_inicio = (EditText) findViewById(R.id.et_data_inicio);
        data_fim = (EditText) findViewById(R.id.et_data_fim);

        MaskEditTextChangedListener maskDataInicio = new MaskEditTextChangedListener("##/##/####", data_inicio);
        data_inicio.addTextChangedListener(maskDataInicio);

        MaskEditTextChangedListener maskDataFim = new MaskEditTextChangedListener("##/##/####", data_fim);
        data_fim.addTextChangedListener(maskDataFim);

        layout_carregando = (RelativeLayout) findViewById(R.id.layout_carregando);
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
        pb_loading.setIndeterminate(true);
    }

    private void preencherCampos()
    {
        tv_modelo.setText(veiculo.montadora + " - " + veiculo.modelo);
        tv_modelo.setTag(veiculo.id_veiculo);
        tv_preco.setText("R$ " + veiculo.preco.replace(".", ","));
        tv_proprietario.setText("Proprietário: " + veiculo.proprietario);
        tv_avaliacao.setText("Avaliação: " + veiculo.avaliacao);
        Picasso.with(VisualizarVeiculo.this).load(veiculo.imagem_veiculo)
                .into(iv_veiculo);

        layout_carregando.setVisibility(View.GONE);
    }

    public void reservar(View v)
    {
        boolean erro = false;

        String usuarioID = RecursosSharedPreferences.getUserID(VisualizarVeiculo.this);
        String veiculoID = tv_modelo.getTag().toString();
        String dataIni = data_inicio.getText().toString();
        if (dataIni.isEmpty())
        {
            erro = true;
            data_inicio.setError("Data Início é obrigatória!");
        }
        String dataFim = data_fim.getText().toString();
        if (dataIni.isEmpty())
        {
            erro = true;
            data_fim.setError("Data Fim é obrigatória!");
        }

        if (!erro) {
            dataIni = dataIni.substring(6,10) + "-" + dataIni.substring(3,5) + "-" + dataIni.substring(0,2);
            dataFim = dataFim.substring(6,10) + "-" + dataFim.substring(3,5) + "-" + dataFim.substring(0,2);

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarVeiculo.this);

                            builder.setTitle("Reserva criada");
                            builder.setMessage("Um aviso foi enviado ao locador. Você será notificado quando ele confirmar a reserva.");
                            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Intent i = new Intent(VisualizarVeiculo.this, Principal.class);
                                    startActivity(i);
                                }
                            });
                            alerta = builder.create();
                            alerta.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarVeiculo.this);

                            builder.setTitle("Erro ao criar reserva");
                            builder.setMessage("Ocorreu um erro ao criar a reserva. Por favor, entre em contato com o suporte.");
                            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Intent i = new Intent(VisualizarVeiculo.this, Principal.class);
                                    startActivity(i);
                                }
                            });
                            alerta = builder.create();
                            alerta.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            ReservarVeiculoRequisicao requisicao = new ReservarVeiculoRequisicao(usuarioID, veiculoID, dataIni, dataFim, responseListener);
            RequestQueue queue = Volley.newRequestQueue(VisualizarVeiculo.this);
            queue.add(requisicao);
        }
    }
}
