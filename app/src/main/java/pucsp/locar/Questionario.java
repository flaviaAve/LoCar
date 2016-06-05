package pucsp.locar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import pucsp.locar.conexoes.BuscarAvaliacoesRealizadas;
import pucsp.locar.conexoes.ListarQuestoes;
import pucsp.locar.conexoes.SalvarQuestionario;
import pucsp.locar.objetos.Avaliacao;
import pucsp.locar.objetos.AvaliacaoAdapter;
import pucsp.locar.objetos.Questao;
import pucsp.locar.objetos.QuestaoAdapter;

public class Questionario extends AppCompatActivity {
    private QuestaoAdapter adapter;
    private ListView lv_questionario;
    private RadioGroup rg_respostas;
    private EditText et_comentario;
    private String questionarioID;
    private String reservaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        questionarioID = bundle.getString("questionarioID");
        reservaID = bundle.getString("reservaID");

        lv_questionario = (ListView) findViewById(R.id.lv_questionario);
        findViewById(R.id.layout_questionario).requestFocus();
        et_comentario = (EditText) findViewById(R.id.et_comentario);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray questoesJSON = jsonResponse.getJSONArray("questao");
                        adapter = new QuestaoAdapter(Questionario.this, Questao.fromJson(questoesJSON));
                        lv_questionario.setAdapter(adapter);
                    } else {
                        adapter = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ListarQuestoes requisicao = new ListarQuestoes(questionarioID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Questionario.this);
        queue.add(requisicao);
    }

    public void salvarResultado(View v)
    {
        final int child=lv_questionario.getChildCount();
        int soma = 0;

        for(int i=0;i<child;i++) {
            View rgg = lv_questionario.getChildAt(i);

            rg_respostas = (RadioGroup) rgg.findViewById(R.id.rg_resultado);

            int selectedId = rg_respostas.getCheckedRadioButtonId();

            RadioButton rb_selecionado = (RadioButton) rgg.findViewById(selectedId);

            soma += Integer.parseInt(rb_selecionado.getText().toString());
        }

        float resultado = (float) soma/child;
        String comentario = et_comentario.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Intent i = new Intent(Questionario.this, Avaliacoes.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(Questionario.this, Avaliacoes.class);
                        startActivity(i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SalvarQuestionario requisicao = new SalvarQuestionario(questionarioID, reservaID, comentario, resultado, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Questionario.this);
        queue.add(requisicao);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Questionario.this, Avaliacoes.class);
        startActivity(i);
    }

}
