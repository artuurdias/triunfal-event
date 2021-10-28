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
import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.ui.Activities.HomeActivity;
import br.unicamp.apptriunfalevent.Models.Usuario;
import br.unicamp.apptriunfalevent.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormLogin extends AppCompatActivity {

    Button btnEntrar_login;
    private EditText edtUsername_login, edtSenha_login;
    private TextView tvCadastro_login;
    private Intent intent;
    private Session session;//global variable


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

                String user = edtUsername_login.getText().toString();
                String password = edtSenha_login.getText().toString();
                getUser(user,password);
            }
        });
    }

    private void setUserSession(Usuario user){

        session = new Session(this); //in oncreate
        //and now we set sharedpreference then use this like
        session.setusename(user.getUsername());

    }

    private void getUser(String username, String senha){
        try {
            Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
            Call<Usuario> call = service.getUsuario(username);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response)
                {
                    if(response.isSuccessful())
                    {
                        Usuario user = (Usuario) response.body();

                        if(user != null)
                        {
                            if(user.getSenha().equals(senha))
                            {
                                setUserSession(user);
                                intent = new Intent(FormLogin.this, HomeActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(FormLogin.this, "Senha invalida", Toast.LENGTH_LONG).show();
                                // Toast.makeText(FormLogin.this, user.toString(), Toast.LENGTH_LONG).show();
                                //  Toast.makeText(FormLogin.this, senha, Toast.LENGTH_LONG).show();

                            }
                        }
                        else{
                            Toast.makeText(FormLogin.this, "Usuario inexistente!", Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {
                        try
                        {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String erro = jObjError.getJSONObject("error").getString("message");
                            Toast.makeText(FormLogin.this, erro, Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(FormLogin.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }@Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    String messageProblem = t.getMessage().toString();
                    Toast.makeText(FormLogin.this, messageProblem, Toast.LENGTH_LONG).show();
                    Toast.makeText(FormLogin.this, "Erro de conexão com API", Toast.LENGTH_LONG).show();

                }
            });
        }catch (Exception erro){
            System.err.println(erro);
            erro.printStackTrace();
        }



    }


    private void inicializarComponentes()
    {
        edtUsername_login = (EditText) findViewById(R.id.edtUsername_login);
        edtSenha_login = (EditText) findViewById(R.id.edtPassworld_login);
        tvCadastro_login = (TextView) findViewById(R.id.tvCadastro_login);
        btnEntrar_login   = (Button) findViewById(R.id.btnEntrar_login);
    }

}