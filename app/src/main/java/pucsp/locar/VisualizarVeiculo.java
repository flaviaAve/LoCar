package pucsp.locar;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
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
import pucsp.locar.objetos.Usuario;
import pucsp.locar.objetos.Veiculo;
import pucsp.locar.objetos.VeiculoReserva;

public class VisualizarVeiculo extends AppCompatActivity {
    private ImageView iv_veiculo;
    private TextView tv_modelo;
    private TextView tv_preco;
    private TextView tv_proprietario;
    private TextView tv_avaliacao;
    private String veiculoID;
    private VeiculoReserva veiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_veiculo);

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
        tv_avaliacao = (TextView) findViewById(R.id.tv_proprietario);
    }

    private void preencherCampos()
    {
        tv_modelo.setText(veiculo.montadora + " - " + veiculo.modelo);
        tv_preco.setText("R$ " + veiculo.preco.replace(".", ","));
        tv_proprietario.setText("Proprietário: " + veiculo.proprietario);
        tv_avaliacao.setText("Avaliação: " + veiculo.avaliacao);
        Picasso.with(VisualizarVeiculo.this).load(veiculo.imagem_veiculo)
                .into(iv_veiculo);
    }
}
