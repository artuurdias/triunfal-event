package br.unicamp.apptriunfalevent.Forms.Evento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.*;
import br.unicamp.apptriunfalevent.Models.*;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.ActivityFormEvento3Binding;
import br.unicamp.apptriunfalevent.ui.Activities.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormEvento3 extends AppCompatActivity {

    private Evento evento;
    private Intent intent;
    private ActivityFormEvento3Binding binding;
    private List<TipoEvento> listTipoEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_evento3);

        binding = ActivityFormEvento3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("sessaoMaps");

        binding.btnProxTipoEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(FormEvento3.this, FormEvento4.class);
                intent.putExtra("sessaoTipoEvent", (Serializable) evento);
                startActivity(intent);
            }
        });

        binding.btnBackTipoEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(FormEvento3.this, FormEvento2.class);
                intent.putExtra("sessaoNome", (Serializable) evento);
                finish();
                startActivity(intent);
            }
        });

        binding.btnHomeTipoEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(FormEvento3.this, HomeActivity.class);
                finish();
                startActivity(intent);
            }
        });

        carregarDadosSpinner();

        binding.spinnerFormEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                evento.setTipo(listTipoEvento.get(pos).getNome());
                binding.tvDefinicaoFormEvent.setText(listTipoEvento.get(pos).getDefinicao());
                binding.tvExemplosFormEvento.setText(listTipoEvento.get(pos).getExemplos());

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void carregarDadosSpinner(){

        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);
        //Session session = new Session(FormEvento2.this);

        Call<List<TipoEvento>> call = service.getTipoEventos();
        call.enqueue(new Callback<List<TipoEvento>>() {
            @Override
            public void onResponse(Call<List<TipoEvento>> call, Response<List<TipoEvento>> response) {

                if(response.isSuccessful()){

                    listTipoEvento = response.body();
                    int qtd =  listTipoEvento.size();
                    String[] estados = new String[qtd];


                    for (int i = 0; i < listTipoEvento.size();i++)
                    {
                        estados[i] = listTipoEvento.get(i).getNome();
                    }
                    binding.tvDefinicaoFormEvent.setText(listTipoEvento.get(0).getDefinicao());
                    binding.tvExemplosFormEvento.setText(listTipoEvento.get(0).getExemplos());

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            FormEvento3.this, android.R.layout.simple_spinner_dropdown_item,
                            estados
                    );
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                    binding.spinnerFormEvent.setAdapter( adapter );



                }else{
                    String errorMessage = response.errorBody().toString();
                    Toast.makeText(FormEvento3.this, errorMessage, Toast.LENGTH_LONG).show();
                    Toast.makeText(FormEvento3.this, "entrou no else do response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<TipoEvento>> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                Toast.makeText(FormEvento3.this, messageProblem, Toast.LENGTH_SHORT).show();
                Toast.makeText(FormEvento3.this, "entrou no else do Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}