package br.unicamp.apptriunfalevent.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.unicamp.apptriunfalevent.Forms.FormCadastro;
import br.unicamp.apptriunfalevent.Forms.FormLogin;
import br.unicamp.apptriunfalevent.R;

public class WelcomeActivity extends AppCompatActivity {

    Button btnCadastro_welcome, btnLogin_welcome;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /*Handler handle = new Handler();
        handle.postDelayed(new Runnable()
        {
            @Override public void run()
            {
                intent = new Intent(WelcomeActivity.this, FormCadastro.class);
                startActivity(intent);
            }
        }, 3000);*/

        inicializarComponents();

        btnCadastro_welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(WelcomeActivity.this, FormCadastro.class);
                startActivity(intent);
            }
        });

        btnLogin_welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(WelcomeActivity.this, FormLogin.class);
                startActivity(intent);
            }
        });

    }


    void inicializarComponents()
    {
        btnCadastro_welcome = (Button) findViewById(R.id.btnCadastro_welcome);
        btnLogin_welcome    = (Button) findViewById(R.id.btnLogin_welcome);
    }

}