package br.unicamp.apptriunfalevent.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;

public class EventoInfo extends AppCompatActivity {


    private Intent intent;
    private Evento evento;
    Button btnMaps, btnHome;
    TextView tvNome, tvEndereco, tvData, tvOrganizador, tvDescricao, tvCategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_info);


        intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("sessaoEventoPart");

        btnMaps = (Button) findViewById(R.id.btnProx_infoEvent);
        btnHome = (Button) findViewById(R.id.btnHome_infoEvent);
        tvNome = (TextView) findViewById(R.id.tvNomeEvento_infoEvent);
        tvEndereco = (TextView) findViewById(R.id.tvEndereco_infoEvent);
        tvData = (TextView) findViewById(R.id.tvDataEvento_infoEvent);
        tvOrganizador = (TextView) findViewById(R.id.tvOrganizador_infoEvent);
        tvDescricao = (TextView) findViewById(R.id.tvDescricaoEvento_infoEvent);
        tvCategoria = (TextView) findViewById(R.id.tvTipoEvento_infoEvent);


        tvNome.setText(evento.getNome());
        tvEndereco.setText(evento.getEndereco());
        tvData.setText(evento.getData());
        tvOrganizador.setText(evento.getOrganizador());
        tvDescricao.setText(evento.getDescricao());
        tvCategoria.setText(evento.getTipo());


        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(EventoInfo.this, EventoInfoMaps.class);
                intent.putExtra("sessaoInfoEvento", (Serializable) evento);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventoInfo.this, HomeActivity.class));
            }
        });
    }
}