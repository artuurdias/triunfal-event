package br.unicamp.apptriunfalevent.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.Models.Convidado;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.Models.Usuario;
import br.unicamp.apptriunfalevent.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngressarEvento extends AppCompatActivity {

    private Button btnIngressarEvento_ingressarEV;
    private EditText edtCodigo_ingressarEV;
    private TextView tvAviso_ingressarEV;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingressar_evento);

        btnIngressarEvento_ingressarEV = (Button) findViewById(R.id.btnIngressarEvento_ingressarEV);
        edtCodigo_ingressarEV = (EditText) findViewById(R.id.edtCodigo_ingressarEV);
        tvAviso_ingressarEV = (TextView) findViewById(R.id.tvAviso_ingressarEV);

        session = new Session(this);

        btnIngressarEvento_ingressarEV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo = edtCodigo_ingressarEV.getText().toString().toUpperCase(Locale.ROOT);
                if (codigo.length() != 6) {
                    tvAviso_ingressarEV.setText("Código inválido!");
                    return;
                }

                //Convidado convidado = new Convidado((int) 5, codigo, session.getusename());

                Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Call<Usuario> call = service.getUsuario(session.getusename());

                try {
                    //Response<Usuario> response = call.execute();
                    Toast.makeText(IngressarEvento.this, "SUCESSO", Toast.LENGTH_SHORT).show();
                }
                catch (Exception erro) {
                    Toast.makeText(IngressarEvento.this, erro.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}