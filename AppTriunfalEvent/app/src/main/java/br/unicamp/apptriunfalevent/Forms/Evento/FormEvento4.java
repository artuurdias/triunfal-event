package br.unicamp.apptriunfalevent.Forms.Evento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.ui.Activities.EventoCriado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormEvento4 extends AppCompatActivity {



    private Intent intent;
    private Evento evento;
    Button btnCriar, btnHome;
    TextView tvNome, tvEndereco, tvData, tvDescricao, tvCategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_evento4);



        intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("sessaoTipoEvent");

        btnCriar = (Button) findViewById(R.id.btnConfirmar_confirmeEvent);
        btnHome = (Button) findViewById(R.id.btnHome_confirmeEvent);

        tvNome = (TextView) findViewById(R.id.tvNomeEvento_confirmeEvent);
        tvEndereco = (TextView) findViewById(R.id.tvEditEndereco_confirmeEvent);
        tvData = (TextView) findViewById(R.id.tvDataEvento_confirmeEvent);
        tvDescricao = (TextView) findViewById(R.id.tvDescricaoEvento_confirmeEvent);
        tvCategoria = (TextView) findViewById(R.id.tvCategoria_confirmeEvent);


        tvNome.setText(evento.getNome());
        tvEndereco.setText(evento.getEndereco());
        tvData.setText(evento.getData());
        tvDescricao.setText(evento.getDescricao());
        tvCategoria.setText(evento.getTipo());

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(FormEvento4.this, FormEvento3.class);
                intent.putExtra("sessaoTipoEvent", (Serializable) evento);
                finish();
                startActivity(intent);
            }
        });

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo = getRandomString(6);
                evento.setId(codigo);

                Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Call<Evento> call = service.postEvento(evento);
                call.enqueue(new Callback<Evento>() {
                    @Override
                    public void onResponse(Call<Evento> call, Response<Evento> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(FormEvento4.this, "Evento Criado com SUCESSO!", Toast.LENGTH_LONG).show();
                            intent = new Intent(FormEvento4.this, EventoCriado.class);
                            intent.putExtra("codigo", (Serializable) codigo);
                            finish();
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(FormEvento4.this, "ERRO!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Evento> call, Throwable t) {
                        Toast.makeText(FormEvento4.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    private String getRandomString(int i) {
        String theAlphaNumericS;
        StringBuilder builder;

        theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";

        //create the StringBuffer
        builder = new StringBuilder(i);

        for (int m = 0; m < i; m++) {

            // generate numeric
            int myindex = (int)(theAlphaNumericS.length() * Math.random());

            // add the characters
            builder.append(theAlphaNumericS.charAt(myindex));
        }

        return builder.toString();
    }

}