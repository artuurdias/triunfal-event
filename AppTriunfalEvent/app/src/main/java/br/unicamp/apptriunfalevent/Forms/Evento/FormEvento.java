package br.unicamp.apptriunfalevent.Forms.Evento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.Calendar;

import br.unicamp.apptriunfalevent.APIconfig.Data;
import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;

public class FormEvento extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView tvDataEvento;
    private FloatingActionButton btnProx;
    private Session session;
    private EditText edtNome, edtDescricao;
    private ProgressBar progressBar;
    private int currentProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_evento);


        edtDescricao = (EditText) findViewById(R.id.edtDescricaoEvento_formEvento);
        edtNome = (EditText) findViewById(R.id.edtNomeEvento_formEvento);
        tvDataEvento         = (TextView)    findViewById(R.id.tvDataEvento_formEvento);
        btnProx         = (FloatingActionButton)    findViewById(R.id.btnProx_formEvent);


        session = new Session(this);


        tvDataEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar();
            }
        });

        btnProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FormEvento.this, FormEvento2.class);
                String nomeVento = edtNome.getText().toString();
                String data = tvDataEvento.getText().toString();
                String descricao = edtDescricao.getText().toString();
                Session session = new Session(FormEvento.this);
                Evento objAluno = new Evento( "", nomeVento, "", data, descricao, "", session.getusename());
                intent.putExtra("sessaoNome", (Serializable) objAluno);
                startActivity(intent);
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

    @Override
    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {


        try{
            Data dataAtual = new Data();
            Data dataEscolhido = new Data((byte)dia, (byte)(mes+1), (short)ano);

            if(dataAtual.compareTo(dataEscolhido) == 0 || dataAtual.compareTo(dataEscolhido) > 0) {
                tvDataEvento.setText(dataAtual.toString());
                exibirErro();
            }
            else {
                tvDataEvento.setText(dataEscolhido.toString());
            }
        }
        catch (Exception erro){
            System.err.println(erro);
        }

    }

    private void exibirErro() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        new AlertDialog.Builder(FormEvento.this)
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
                //.setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_baseline_warning_24)
                .show();
    }
}