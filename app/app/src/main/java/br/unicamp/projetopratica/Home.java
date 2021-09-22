package br.unicamp.projetopratica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    Button btnCriarEvento, btnConvidar, btnCriarLembrete;
    View view3Linhas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnCriarEvento = (Button) findViewById(R.id.btnCriarEvento);
        btnConvidar = (Button) findViewById(R.id.btnConvidar);
        btnCriarLembrete = (Button) findViewById(R.id.btnCriarLembrete);

        view3Linhas = (View) findViewById(R.id.view3Linhas);

        btnCriarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, CriarEvento.class);
                startActivity(intent);
            }
        });

        btnConvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Convidar.class);
                startActivity(intent);
            }
        });

        btnCriarLembrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, CriarLembrete.class);
                startActivity(intent);
            }
        });

        view3Linhas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Clicou nas 3 linhas", Toast.LENGTH_LONG);
            }
        });
    }
}