package br.unicamp.apptriunfalevent.Forms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.*;

import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.Forms.*;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.Models.Usuario;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.ui.Activities.HomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormConvidar extends AppCompatActivity {
    private GridView gdvAmigos_Convidar;
    private ConvidarAmigosAdapter adapter;
    private Session session;

    private Spinner spinner_formEvent;
    private List<Evento> list;
    private TextView tvNome_formEvent;
    private Evento eventSelected;

    private boolean temEventos = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_convidar);

        gdvAmigos_Convidar = (GridView) findViewById(R.id.gdvAmigos_Convidar);

        session = new Session(this);

        tvNome_formEvent = findViewById(R.id.tvNome_formEvent);
        spinner_formEvent = findViewById(R.id.spinner_formEvent);

        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<List<Usuario>> call = service.getUsuarios();

        preencherSpinner();
        if (!temEventos) {
            startActivity(new Intent(FormConvidar.this, HomeActivity.class));
        }

        spinner_formEvent.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    eventSelected = list.get(pos);
                    tvNome_formEvent.setText(eventSelected.getId());
                }
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    List<Usuario> list = response.body();

                    List<Usuario> stack = new Stack<>();
                    for (Usuario u : list)
                        if (!(u.getUsername().toString().equals(session.getusename().toString())))
                            stack.add(u);

                    adapter = new ConvidarAmigosAdapter(FormConvidar.this, stack);
                    gdvAmigos_Convidar.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(FormConvidar.this, "ERRO! FAILURE!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void preencherSpinner() {
        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);

        Call<List<Evento>> call = service.getEventosOrg(session.getusename());
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {

                if(response.isSuccessful()){

                    if (response.body().size() == 0) {
                        new AlertDialog.Builder(FormConvidar.this)
                            .setTitle("ERRO!")
                            .setMessage("Você não possui eventos.")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    temEventos = false;
                                    //startActivity(new Intent(FormConvidar.this, HomeActivity.class));
                                    // Continue with delete operation
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    temEventos = false;
                                    //startActivity(new Intent(FormConvidar.this, HomeActivity.class));
                                }
                            })
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .show();

                        temEventos = false;

                        return;
                    }


                    list = response.body();
                    int qtd =  list.size();
                    String[] estados = new String[qtd];


                    for (int i = 0; i < list.size();i++)
                    {
                        estados[i] = list.get(i).getNome();
                    }
                    tvNome_formEvent.setText(list.get(0).getNome());

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        FormConvidar.this, android.R.layout.simple_spinner_dropdown_item,
                        estados
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_formEvent.setAdapter(adapter);



                }
                else {
                    String errorMessage = response.errorBody().toString();
                    Toast.makeText(FormConvidar.this, errorMessage, Toast.LENGTH_LONG).show();
                    Toast.makeText(FormConvidar.this, "entrou no else do response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                Toast.makeText(FormConvidar.this, messageProblem, Toast.LENGTH_SHORT).show();
                Toast.makeText(FormConvidar.this, "entrou no else do Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}