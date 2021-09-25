package br.unicamp.triunfalevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FormCadastra extends AppCompatActivity {

    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastra);

        inicializarComponentes();


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormCadastra.this, MenuActivity.class);
                startActivity(intent);
            }
        });

    }

    private void inicializarComponentes()
    {
        tvLogin = findViewById(R.id.tvLogin);
    }
}