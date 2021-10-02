package br.unicamp.apptriunfalevent.Forms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.unicamp.apptriunfalevent.HomeActivity;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.WelcomeActivity;


public class FormCadastro extends AppCompatActivity {

    Button btnCadastrar_cadastro;
    TextView tvLogin_cadastro;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        inicializarComponentes();

        tvLogin_cadastro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(FormCadastro.this, FormLogin.class);
                startActivity(intent);
            }
        });

        btnCadastrar_cadastro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(FormCadastro.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }


    private void inicializarComponentes()
    {
        tvLogin_cadastro = (TextView) findViewById(R.id.tvLogin_cadastro);
        btnCadastrar_cadastro   = (Button) findViewById(R.id.btnCadastrar_cadastro);
    }
}