package pucsp.locar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.Date;
import java.util.GregorianCalendar;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import pucsp.locar.conexoes.BuscarReservaRequisicao;
import pucsp.locar.conexoes.BuscarVeiculoReservaRequisicao;
import pucsp.locar.conexoes.MudarStatusReservaRequisicao;
import pucsp.locar.conexoes.ReservarVeiculoRequisicao;
import pucsp.locar.objetos.Reserva;
import pucsp.locar.objetos.VeiculoReserva;

public class VisualizarReserva extends AppCompatActivity {
    private ImageView iv_veiculo;
    private TextView tv_modelo;
    private TextView tv_preco;
    private TextView tv_proprietario;
    private TextView tv_avaliacao;
    private TextView tv_periodo;
    private TextView tv_email;
    private Button bt_cancelar;
    private Button bt_confirmar;
    private Button bt_recusar;
    private Button bt_finalizar;
    private RelativeLayout layout_carregando;
    private String reservaID;
    private Reserva reserva;
    private ProgressBar pb_loading;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_reserva);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            reservaID = bundle.getString("reservaID");
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        reserva = new Reserva(jsonResponse);
                        preencherCampos();
                    } else {
                        reserva = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        BuscarReservaRequisicao requisicao = new BuscarReservaRequisicao(reservaID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(VisualizarReserva.this);
        queue.add(requisicao);

        iv_veiculo = (ImageView) findViewById(R.id.iv_foto_veiculo);
        tv_modelo = (TextView) findViewById(R.id.tv_montadora_modelo);
        tv_preco = (TextView) findViewById(R.id.tv_preco_aluguel);
        tv_proprietario = (TextView) findViewById(R.id.tv_proprietario);
        tv_avaliacao = (TextView) findViewById(R.id.tv_avaliacao_proprietario);
        tv_periodo = (TextView) findViewById(R.id.tv_periodo);
        tv_email = (TextView) findViewById(R.id.tv_email);

        bt_cancelar = (Button) findViewById(R.id.bt_cancelar);
        bt_confirmar = (Button) findViewById(R.id.bt_confirmar);
        bt_recusar = (Button) findViewById(R.id.bt_recusar);
        bt_finalizar = (Button) findViewById(R.id.bt_finalizar);

        layout_carregando = (RelativeLayout) findViewById(R.id.layout_carregando);
        pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
        pb_loading.setIndeterminate(true);
    }

    private void preencherCampos()
    {
        tv_modelo.setText(reserva.montadora + " - " + reserva.modelo);
        tv_modelo.setTag(reserva.id_reserva);
        if (reserva.locadorID.equals(RecursosSharedPreferences.getUserID(VisualizarReserva.this))) {
            tv_proprietario.setText("Locatário: " + reserva.locatario);
            tv_avaliacao.setText("Avaliação: " + reserva.avaliacaoLocatario);
            tv_email.setText("Entre em contato: " + reserva.locatarioEmail);
        }
        else
        {
            tv_proprietario.setText("Locador: " + reserva.locador);
            tv_avaliacao.setText("Avaliação: " + reserva.avaliacaoLocador);
            tv_email.setText("Entre em contato: " + reserva.locadorEmail);
        }

        String dataInicio = reserva.data_inicio.substring(8, 10) + "/" + reserva.data_inicio.substring(5, 7) + "/" + reserva.data_inicio.substring(0, 4);
        String dataFim = reserva.data_fim.substring(8, 10) + "/" + reserva.data_fim.substring(5, 7) + "/" + reserva.data_fim.substring(0, 4);

        tv_periodo.setText(dataInicio + " - " + dataFim);
        Picasso.with(VisualizarReserva.this).load(reserva.imagem_veiculo)
                .into(iv_veiculo);

        int ano = Integer.parseInt(reserva.data_inicio.substring(0, 4));
        int mes = Integer.parseInt(reserva.data_inicio.substring(5, 7));
        int dia = Integer.parseInt(reserva.data_inicio.substring(8, 10));

        GregorianCalendar dataI = new GregorianCalendar(ano, mes, dia);

        ano = Integer.parseInt(reserva.data_fim.substring(0, 4));
        mes = Integer.parseInt(reserva.data_fim.substring(5, 7));
        dia = Integer.parseInt(reserva.data_fim.substring(8, 10));

        GregorianCalendar dataF = new GregorianCalendar(ano, mes, dia);

        long minutos = (dataF.getTimeInMillis() - dataI.getTimeInMillis()) / 60000;
        double preco = Double.parseDouble(reserva.preco);
        String precoFinal = String.valueOf(minutos * preco).replace(".",",");
        tv_preco.setText("R$ " + precoFinal);

        layout_carregando.setVisibility(View.GONE);
        iv_veiculo.setVisibility(View.VISIBLE);
        tv_modelo.setVisibility(View.VISIBLE);
        tv_preco.setVisibility(View.VISIBLE);
        tv_proprietario.setVisibility(View.VISIBLE);
        tv_avaliacao.setVisibility(View.VISIBLE);
        tv_periodo.setVisibility(View.VISIBLE);

        if (reserva.statusID == 1 && reserva.locadorID.equals(RecursosSharedPreferences.getUserID(VisualizarReserva.this)))
        {
            bt_cancelar.setVisibility(View.INVISIBLE);
            bt_confirmar.setVisibility(View.VISIBLE);
            bt_recusar.setVisibility(View.VISIBLE);
            bt_finalizar.setVisibility(View.INVISIBLE);
            tv_email.setVisibility(View.INVISIBLE);
        }
        else if (reserva.statusID == 1 && !reserva.locadorID.equals(RecursosSharedPreferences.getUserID(VisualizarReserva.this)))
        {
            bt_cancelar.setVisibility(View.VISIBLE);
            bt_confirmar.setVisibility(View.INVISIBLE);
            bt_recusar.setVisibility(View.INVISIBLE);
            bt_finalizar.setVisibility(View.INVISIBLE);
            tv_email.setVisibility(View.INVISIBLE);
        }
        else if (reserva.statusID == 2)
        {
            bt_cancelar.setVisibility(View.VISIBLE);
            bt_confirmar.setVisibility(View.INVISIBLE);
            bt_recusar.setVisibility(View.INVISIBLE);
            bt_finalizar.setVisibility(View.VISIBLE);
            tv_email.setVisibility(View.VISIBLE);
        }
        else
        {
            bt_cancelar.setVisibility(View.INVISIBLE);
            bt_confirmar.setVisibility(View.INVISIBLE);
            bt_recusar.setVisibility(View.INVISIBLE);
            bt_finalizar.setVisibility(View.INVISIBLE);
            tv_email.setVisibility(View.INVISIBLE);
        }


    }

    public void cancelar(View v)
    {
        final String reservaID = tv_modelo.getTag().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);
        builder.setTitle("Cancelar");
        builder.setMessage("Você tem certeza que deseja cancelar a reserva?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);

                                builder.setTitle("Reserva cancelada");
                                builder.setMessage("Sua reserva foi cancelada com sucesso.");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent i = new Intent(VisualizarReserva.this, Reservas.class);
                                        startActivity(i);
                                    }
                                });
                                alerta = builder.create();
                                alerta.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);

                                builder.setTitle("Erro ao cancelar reserva");
                                builder.setMessage("Ocorreu um erro ao cancelar a reserva. Por favor, entre em contato com o suporte.");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent i = new Intent(VisualizarReserva.this, Reservas.class);
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

                MudarStatusReservaRequisicao requisicao = new MudarStatusReservaRequisicao(reservaID, 4, responseListener);
                RequestQueue queue = Volley.newRequestQueue(VisualizarReserva.this);
                queue.add(requisicao);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alerta = builder.create();
        alerta.show();
    }

    public void confirmar(View v)
    {
        final String reservaID = tv_modelo.getTag().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);
        builder.setTitle("Confirmar");
        builder.setMessage("Você tem certeza que deseja confirmar a reserva?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);

                                builder.setTitle("Reserva confirmada");
                                builder.setMessage("Sua reserva foi confirmada com sucesso.");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent i = new Intent(VisualizarReserva.this, Reservas.class);
                                        startActivity(i);
                                    }
                                });
                                alerta = builder.create();
                                alerta.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);

                                builder.setTitle("Erro ao confirmar reserva");
                                builder.setMessage("Ocorreu um erro ao confirmar a reserva. Por favor, entre em contato com o suporte.");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent i = new Intent(VisualizarReserva.this, Reservas.class);
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

                MudarStatusReservaRequisicao requisicao = new MudarStatusReservaRequisicao(reservaID, 2, responseListener);
                RequestQueue queue = Volley.newRequestQueue(VisualizarReserva.this);
                queue.add(requisicao);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alerta = builder.create();
        alerta.show();
    }

    public void recusar(View v)
    {
        final String reservaID = tv_modelo.getTag().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);
        builder.setTitle("Recusar");
        builder.setMessage("Você tem certeza que deseja recusar a reserva?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);

                                builder.setTitle("Reserva recusada");
                                builder.setMessage("Sua reserva foi recusada com sucesso.");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent i = new Intent(VisualizarReserva.this, Reservas.class);
                                        startActivity(i);
                                    }
                                });
                                alerta = builder.create();
                                alerta.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);

                                builder.setTitle("Erro ao recusar reserva");
                                builder.setMessage("Ocorreu um erro ao recusar a reserva. Por favor, entre em contato com o suporte.");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent i = new Intent(VisualizarReserva.this, Reservas.class);
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

                MudarStatusReservaRequisicao requisicao = new MudarStatusReservaRequisicao(reservaID, 3, responseListener);
                RequestQueue queue = Volley.newRequestQueue(VisualizarReserva.this);
                queue.add(requisicao);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alerta = builder.create();
        alerta.show();
    }

    public void finalizar(View v)
    {
        final String reservaID = tv_modelo.getTag().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);
        builder.setTitle("Finalizar");
        builder.setMessage("Você tem certeza que deseja finalizar a reserva?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);

                                builder.setTitle("Reserva finalizada");
                                builder.setMessage("Sua reserva foi finalizada com sucesso.");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent i = new Intent(VisualizarReserva.this, Reservas.class);
                                        startActivity(i);
                                    }
                                });
                                alerta = builder.create();
                                alerta.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizarReserva.this);

                                builder.setTitle("Erro ao finalizar reserva");
                                builder.setMessage("Ocorreu um erro ao finalizar a reserva. Por favor, entre em contato com o suporte.");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent i = new Intent(VisualizarReserva.this, Reservas.class);
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

                MudarStatusReservaRequisicao requisicao = new MudarStatusReservaRequisicao(reservaID, 5, responseListener);
                RequestQueue queue = Volley.newRequestQueue(VisualizarReserva.this);
                queue.add(requisicao);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alerta = builder.create();
        alerta.show();
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(VisualizarReserva.this, Reservas.class);
        startActivity(i);
    }
}
