package br.unicamp.apptriunfalevent.Forms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.unicamp.apptriunfalevent.HomeActivity;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.WelcomeActivity;

public class FormLogin extends AppCompatActivity {

    Button btnEntrar_login;
    TextView tvCadastro_login;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        inicializarComponentes();



        tvCadastro_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(FormLogin.this, FormCadastro.class);
                startActivity(intent);
            }
        });

        btnEntrar_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(FormLogin.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }



    private void inicializarComponentes()
    {
        tvCadastro_login = (TextView) findViewById(R.id.tvCadastro_login);
        btnEntrar_login   = (Button) findViewById(R.id.btnEntrar_login);
    }

}