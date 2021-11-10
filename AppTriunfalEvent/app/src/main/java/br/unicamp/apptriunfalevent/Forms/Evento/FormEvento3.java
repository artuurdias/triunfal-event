package br.unicamp.apptriunfalevent.Forms.Evento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import java.io.Serializable;
import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.Permissoes;
import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.Forms.FormCriarEvento;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.Models.TipoEvento;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.ActivityFormEvento3Binding;
import br.unicamp.apptriunfalevent.ui.Activities.EventoCriado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormEvento3 extends AppCompatActivity {

    private Spinner campoEstado;
    private Evento evento;
    private Intent intent;
    private String[] descricaoEvento;
    private String[] exemplos;
    private String[] nome;
    private TextView tvExemplos, tvDescricao;
    private ActivityFormEvento3Binding binding;
    private List<TipoEvento> listTipoEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_evento3);

        binding = ActivityFormEvento3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCadastrarFormEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo = getRandomString(6);
                evento.setId(codigo);
                tvDescricao.setText(evento.toString());

                Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Call<Evento> call = service.postEvento(evento);

                call.enqueue(new Callback<Evento>() {
                    @Override
                    public void onResponse(Call<Evento> call, Response<Evento> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(FormEvento3.this, "SUCESSO!", Toast.LENGTH_LONG).show();

                            intent = new Intent(FormEvento3.this, EventoCriado.class);
                            intent.putExtra("codigo", (Serializable) codigo);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(FormEvento3.this, "ERRO!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Evento> call, Throwable t) {
                        Toast.makeText(FormEvento3.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("sessaoMaps");
        tvDescricao = (TextView) findViewById(R.id.tvDefinicao_formEvent);
        tvExemplos = (TextView) findViewById(R.id.tvExemplos_formEvento);
        campoEstado = (Spinner) findViewById(R.id.spinner_formEvent);


        carregarDadosSpinner();

        /**/campoEstado.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        evento.setTipo(listTipoEvento.get(pos).getNome());
                        tvDescricao.setText(listTipoEvento.get(pos).getDefinicao());
                        tvExemplos.setText(listTipoEvento.get(pos).getExemplos());

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
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
                    tvDescricao.setText(listTipoEvento.get(0).getDefinicao());
                    tvExemplos.setText(listTipoEvento.get(0).getExemplos());
                    /*
                    //Configura spinner de estados
                    String[] estados = getResources().getStringArray(R.array.estados);
                    */
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            FormEvento3.this, android.R.layout.simple_spinner_dropdown_item,
                            estados
                    );
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                    campoEstado.setAdapter( adapter );



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