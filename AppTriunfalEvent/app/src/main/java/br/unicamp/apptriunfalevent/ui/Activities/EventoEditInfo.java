package br.unicamp.apptriunfalevent.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.Data;
import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.Models.TipoEvento;
import br.unicamp.apptriunfalevent.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventoEditInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Intent intent;
    private Evento evento;
    Button btnAtualizar, btnConvidados, btnHome;
    EditText edtNomeEvento, edtDescricao;
    TextView tvData, tvEndereco;
    Spinner spinner;
    private List<TipoEvento> listTipoEvento;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_edit_info);

        intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("sessaoEvento");
        if(evento == null)
        {
            evento = (Evento) intent.getSerializableExtra("sessaoMapsEdit");
        }

        btnAtualizar = (Button) findViewById(R.id.btnAtualizar_editEvent);
        btnConvidados = (Button) findViewById(R.id.btnConvidados_editEvent);
        btnHome = (Button) findViewById(R.id.btnHome_editEvent);


        edtDescricao = findViewById(R.id.edtDescricaoEvento_editEvent);
        edtNomeEvento = findViewById(R.id.edtNomeEvento_editEvent);
        tvEndereco = findViewById(R.id.tvEditEndereco_editEvent);
        tvData = findViewById(R.id.tvDataEvento_editEvent);
        spinner = findViewById(R.id.spinner_editEvent);

        edtNomeEvento.setText(evento.getNome());
        edtDescricao.setText(evento.getDescricao());
        tvEndereco.setText(evento.getEndereco());
        tvData.setText(evento.getData());

        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar();
            }
        });

        carregarDadosSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                evento.setTipo(listTipoEvento.get(pos).getNome());
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tvEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(EventoEditInfo.this, EventoEditMaps.class);
                intent.putExtra("sessaoEditEvento", (Serializable) evento);
                startActivity(intent);
            }
        });

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirConfirmacao();

            }
        });

        btnConvidados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(EventoEditInfo.this, EventoInfoConvidados.class);
                intent.putExtra("sessaoEditEvento", (Serializable) evento);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventoEditInfo.this, HomeActivity.class));
            }
        });
    }

    private void showCalendar(){

        DatePickerDialog calendario = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        calendario.show();


    }


    private void carregarDadosSpinner(){

        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);


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


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            EventoEditInfo.this, android.R.layout.simple_spinner_dropdown_item,
                            estados
                    );
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                    spinner.setAdapter( adapter );



                }else{
                    String errorMessage = response.errorBody().toString();
                    Toast.makeText(EventoEditInfo.this, errorMessage, Toast.LENGTH_LONG).show();
                    Toast.makeText(EventoEditInfo.this, "entrou no else do response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<TipoEvento>> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                Toast.makeText(EventoEditInfo.this, messageProblem, Toast.LENGTH_SHORT).show();
                Toast.makeText(EventoEditInfo.this, "entrou no else do Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {


        try{
            Data dataAtual = new Data();
            Data dataEscolhido = new Data((byte)dia, (byte)(mes+1), (short)ano);

            if(dataAtual.compareTo(dataEscolhido) == 0 || dataAtual.compareTo(dataEscolhido) > 0) {
                tvData.setText(dataAtual.toString());
                exibirErro();
            }
            else {
                tvData.setText(dataEscolhido.toString());
            }
        }
        catch (Exception erro){
            System.err.println(erro);
        }

    }


    private void exibirErro() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        new AlertDialog.Builder(EventoEditInfo.this)
                .setTitle("Data definida inválida!")
                .setMessage("A data escolhida é hoje ou é um data passada, por favor selecione um data válida.")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.

                .setIcon(R.drawable.ic_baseline_warning_24)
                .show();
    }

    private void exibirConfirmacao() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        new AlertDialog.Builder(EventoEditInfo.this)
                .setTitle("Atualização de Evento!")
                .setMessage("Você tem certeza que deseja alterar as informações do evento?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                        Call<Evento> call = service.putEvento(evento.getId(), evento);

                        call.enqueue(new Callback<Evento>() {
                            @Override
                            public void onResponse(Call<Evento> call, Response<Evento> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(EventoEditInfo.this, "Evento editado com sucesso!", Toast.LENGTH_LONG).show();
                                    intent = new Intent(EventoEditInfo.this, HomeActivity.class);
                                    finish();
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(EventoEditInfo.this, "ERRO ao editar evento!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Evento> call, Throwable t) {
                                Toast.makeText(EventoEditInfo.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(EventoEditInfo.this, "Atualização cancelada!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(R.drawable.ic_baseline_warning_24)
                .show();
    }
}