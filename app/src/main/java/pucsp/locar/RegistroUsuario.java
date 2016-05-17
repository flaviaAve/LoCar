package pucsp.locar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class RegistroUsuario extends AppCompatActivity {
    EditText nome;
    EditText email;
    EditText cpf;
    EditText cnh;
    EditText nascimento;
    EditText rg;
    EditText orgao_emissor;
    EditText senha;
    EditText confirma_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nome = (EditText) findViewById(R.id.etNome);
        email = (EditText) findViewById(R.id.etEmail);
        cpf = (EditText) findViewById(R.id.etCPF);
        cnh = (EditText) findViewById(R.id.etCNH);
        nascimento = (EditText) findViewById(R.id.etNascimento);
        rg = (EditText) findViewById(R.id.etRG);
        orgao_emissor = (EditText) findViewById(R.id.etOrgaoEmissor);
        senha = (EditText) findViewById(R.id.etSenha);
        confirma_senha = (EditText)findViewById(R.id.etConfirmaSenha);

        MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", cpf);
        MaskEditTextChangedListener maskData = new MaskEditTextChangedListener("##/##/##", nascimento);
        MaskEditTextChangedListener maskRG = new MaskEditTextChangedListener("##.###.###-#", rg);

        cpf.addTextChangedListener(maskCPF);
        nascimento.addTextChangedListener(maskData);
        rg.addTextChangedListener(maskRG);
    }

    public void registrar(View v)
    {
        //TODO: Obter valores dos campos e enviar para serviço de inclusão de usuário
        startActivity(new Intent(this, Principal.class));
    }
}
