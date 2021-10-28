package br.unicamp.apptriunfalevent.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.unicamp.apptriunfalevent.R;

public class EventoCriado extends AppCompatActivity {
    private TextView tvCodigo_evCriado;
    private Button btnVoltarHome_evCriado, btnConvidarAmigos_evCriado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_criado);

        tvCodigo_evCriado = findViewById(R.id.tvCodigo_evCriado);

        btnVoltarHome_evCriado = (Button) findViewById(R.id.btnVoltarHome_evCriado);
        btnConvidarAmigos_evCriado = (Button) findViewById(R.id.btnConvidarAmigos_evCriado);

        btnVoltarHome_evCriado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventoCriado.this, IngressarEvento.class));
            }
        });

        btnConvidarAmigos_evCriado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(EventoCriado.this, ConvidarAmigos.class));
            }
        });

        Intent intent = getIntent();
        String codigo = (String) intent.getSerializableExtra("codigo");
        tvCodigo_evCriado.setText(codigo);
    }


}