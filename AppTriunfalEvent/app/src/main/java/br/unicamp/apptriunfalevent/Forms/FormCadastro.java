package br.unicamp.apptriunfalevent.Forms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.APIconfig.Usuario;
import br.unicamp.apptriunfalevent.HomeActivity;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.WelcomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FormCadastro extends AppCompatActivity {

    Button btnCadastrar_cadastro;
    TextView tvLogin_cadastro;
    EditText edtUsername_cadastro, edtEmail_cadastro, edtNome_cadastro, edtData_cadastro, edtPassworld_cadastro;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        inicializarComponentes();

        tvLogin_cadastro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(FormCadastro.this, FormLogin.class);
                startActivity(intent);
            }
        });

        btnCadastrar_cadastro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = edtUsername_cadastro.getText().toString();
                String nome = edtNome_cadastro.getText().toString();
                String senha = edtPassworld_cadastro.getText().toString();
                String data = edtData_cadastro.getText().toString();
                String email = edtEmail_cadastro.getText().toString();

                Usuario novoUsuario = new Usuario(username,nome,data,email,senha);

                incluirUsuario(novoUsuario);
            }
        });
    }

    private void incluirUsuario(Usuario user){
        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Usuario> call = service.postUsuario(user.getUsername(), user);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response)
            {
                if(response.isSuccessful())
                {
                    Usuario user = (Usuario) response.body();
                    Toast.makeText(FormCadastro.this, user.toString(), Toast.LENGTH_LONG).show();
                    intent = new Intent(FormCadastro.this, HomeActivity.class);
                    startActivity(intent);
                }
                else
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String erro = jObjError.getJSONObject("error").getString("message");
                        Toast.makeText(FormCadastro.this, erro, Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(FormCadastro.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                Toast.makeText(FormCadastro.this, messageProblem, Toast.LENGTH_LONG).show();
                Toast.makeText(FormCadastro.this, "Erro de conexão com API", Toast.LENGTH_LONG).show();

            }
        });
    }


    private void inicializarComponentes()
    {
        edtUsername_cadastro = (EditText) findViewById(R.id.edtUsername_login);
        edtEmail_cadastro = (EditText) findViewById(R.id.edtEmail_cadastro);
        edtNome_cadastro = (EditText) findViewById(R.id.edtNome_cadastro);
        edtData_cadastro = (EditText) findViewById(R.id.edtData_cadastro);
        edtPassworld_cadastro = (EditText) findViewById(R.id.edtPassworld_cadastro);
        tvLogin_cadastro = (TextView) findViewById(R.id.tvLogin_cadastro);
        btnCadastrar_cadastro   = (Button) findViewById(R.id.btnCadastrar_cadastro);
    }
}