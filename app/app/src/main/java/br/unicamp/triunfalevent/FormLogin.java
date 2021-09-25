package br.unicamp.triunfalevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FormLogin extends AppCompatActivity {

    Button btnCadastro, btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        inicializarComponentes();

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, FormCadastra.class);
                startActivity(intent);                finish();
                finish();


            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLogin.this, Home.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void inicializarComponentes()
    {
        btnCadastro = (Button) findViewById(R.id.btnCadastro);
        btnEntrar   = (Button) findViewById(R.id.btnEntrar);
    }
}