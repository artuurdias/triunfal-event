package br.unicamp.apptriunfalevent.Forms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import br.unicamp.apptriunfalevent.*;
import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.Models.TipoEvento;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.ui.Activities.EventoCriado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormCriarEvento extends AppCompatActivity {

    private Button   btnCriarEvento_criarEV;
    private EditText edtNome_criarEV, edtTipo_criarEV, edtData_criarEV, edtLocal_criarEV;
    private TextView tvAviso_criarEV;
    //ArrayList<String> itens;
    String[] itens;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_criar_evento);

        btnCriarEvento_criarEV  = (Button)      findViewById(R.id.btnCriarEvento_criarEV);
        edtNome_criarEV         = (EditText)    findViewById(R.id.edtNome_criarEV);
        edtTipo_criarEV         = (EditText)    findViewById(R.id.edtTipo_criarEV);
        edtData_criarEV         = (EditText)    findViewById(R.id.edtData_criarEV);
        edtLocal_criarEV        = (EditText)    findViewById(R.id.edtLocal_criarEV);
        tvAviso_criarEV         = (TextView)    findViewById(R.id.tvAviso_criarEV);

        session = new Session(this);

        //get the spinner from the xml.
      /*
        //create a list of items for the spinner.


        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<List<TipoEvento>> call = service.getTipoEventos();


        try {
            Response<List<TipoEvento>> response = call.execute();
            int qtd = response.body().size();
            //itens = new ArrayList<String>(qtd);
            itens = new String[qtd];

            Toast.makeText(FormCriarEvento.this, "SUCESSO!", Toast.LENGTH_LONG).show();
            for(int i = 0; i < qtd; i++)
            {
                // itens.add(response.body().get(i).getNome());
                itens[i] = response.body().get(i).getNome();
                System.err.println(response.body().get(i).getNome());
            }
        }
        catch (Exception erro){
            Toast.makeText(FormCriarEvento.this, "KKKKKK FUDEU", Toast.LENGTH_LONG).show();
        }
*/
     /*   call.enqueue(new Callback<List<TipoEvento>>() {
            @Override
            public void onResponse(Call<List<TipoEvento>> call, Response<List<TipoEvento>> response) {
                if (response.isSuccessful()) {

                    int qtd = response.body().size();
                    //itens = new ArrayList<String>(qtd);
                    itens = new String[qtd];

                    Toast.makeText(FormCriarEvento.this, "SUCESSO!", Toast.LENGTH_LONG).show();
                    for(int i = 0; i < qtd; i++)
                    {
                        //itens.add(response.body().get(i).getNome());
                        itens[i] = response.body().get(i).getNome();
                        System.err.println(response.body().get(i).getNome());
                    }
                }
                else {
                    Toast.makeText(FormCriarEvento.this, "ERRO!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TipoEvento>> call, Throwable t) {
                Toast.makeText(FormCriarEvento.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
       // ArrayAdapter<TipoEvento> adapter = new ArrayAdapter<TipoEvento>(this, android.R.layout.simple_spinner_dropdown_item, itens);

        Spinner dropdown = findViewById(R.id.spinnerTipoEvento_criarEV);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itens);

        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);*/

        btnCriarEvento_criarEV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNome_criarEV.getText().toString().trim().equals("") || edtTipo_criarEV.getText().toString().trim().equals("")
                || edtData_criarEV.getText().toString().trim().equals("") || edtLocal_criarEV.getText().toString().trim().equals("")) {
                    tvAviso_criarEV.setText("Campos inválidos!");
                    return;
                }
                else {
                    tvAviso_criarEV.setVisibility(View.GONE);

                    String codigo = getRandomString(6);

                    Evento evento = null;// = new Evento(codigo, edtNome_criarEV.getText().toString(), edtTipo_criarEV.getText().toString(),
                          // edtData_criarEV.getText().toString(), edtLocal_criarEV.getText().toString(), session.getusename());

                    Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                    Call<Evento> call = service.postEvento(evento);


                    call.enqueue(new Callback<Evento>() {
                        @Override
                        public void onResponse(Call<Evento> call, Response<Evento> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(FormCriarEvento.this, "SUCESSO!", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(FormCriarEvento.this, EventoCriado.class);
                                intent.putExtra("codigo", (Serializable) codigo);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(FormCriarEvento.this, "ERRO!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Evento> call, Throwable t) {
                            Toast.makeText(FormCriarEvento.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
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