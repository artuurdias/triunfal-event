package br.unicamp.apptriunfalevent.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.*;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.Models.*;
import br.unicamp.apptriunfalevent.ui.event.AdapterEventsConvidado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventoInfoConvidados extends AppCompatActivity {

    private Intent intent;
    private Evento evento;
    private Button btnBack;
    private GridView gridView;
    private AdapterEventsConvidado adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_info_convidados);

        intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("sessaoEditEvento");
        btnBack = findViewById(R.id.btnBack_convEvent);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(EventoInfoConvidados.this, EventoEditInfo.class);
                intent.putExtra("sessaoEvento", (Serializable)evento);
                startActivity(intent);
            }
        });

        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);

        Call<List<Convidado>> call = service.getConvidadosEvento(evento.getId());
        call.enqueue(new Callback<List<Convidado>>() {
            @Override
            public void onResponse(Call<List<Convidado>> call, Response<List<Convidado>> response) {

                if(response.isSuccessful()){
                    populateGridView(response.body());
                }else{
                    String errorMessage = response.errorBody().toString();
                    Toast.makeText(EventoInfoConvidados.this, errorMessage, Toast.LENGTH_LONG).show();
                    Toast.makeText(EventoInfoConvidados.this, "entrou no else do response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Convidado>> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                Toast.makeText(EventoInfoConvidados.this, messageProblem, Toast.LENGTH_SHORT).show();
                Toast.makeText(EventoInfoConvidados.this, "entrou no else do Failure", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void populateGridView(List<Convidado> lista){
        gridView = (GridView) findViewById(R.id.gridEventsConvidados);

        if (lista.size() > 0) {
            adapter = new AdapterEventsConvidado(EventoInfoConvidados.this, EventoInfoConvidados.this, lista);
            gridView.setAdapter(adapter);
        }
        else {
            gridView.setAdapter(null);
            //exibirErro();
        }
    }

}