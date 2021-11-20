package br.unicamp.apptriunfalevent.Forms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Calendar;


import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.Models.Convidado;
import br.unicamp.apptriunfalevent.Models.Convite;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.Models.Usuario;
import br.unicamp.apptriunfalevent.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConvidarAmigosAdapter extends BaseAdapter  {
    private List<Usuario> lista;
    private Context context;
    private Session session;
    private Activity app;
    private Spinner spinner_formEvent;
    private TextView tvNome_formEvent;


    public ConvidarAmigosAdapter(Context context, List<Usuario> lista) {
        this.lista = lista;
        this.context = context;
        this.session = new Session(this.context);
        this.app = (Activity) this.context;
        this.spinner_formEvent = this.app.findViewById(R.id.spinner_formEvent);
        this.tvNome_formEvent = this.app.findViewById(R.id.tvNome_formEvent);
    }


    @Override
    public int getCount() { return lista.size(); }

    @Override
    public Object getItem(int i) { return lista.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_convidar, parent,false);
        }

        TextView tvNomeUsuario_convidar = view.findViewById(R.id.tvNomeUsuario_convidar);
        View vConvidar_convidar = view.findViewById(R.id.tvConvidar_convidar);

        Usuario user = lista.get(i);

        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);


        tvNomeUsuario_convidar.setText(user.getNome());
        vConvidar_convidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo = tvNome_formEvent.getText().toString();

                //Convidado convidado = new Convidado(user.getUsername(), codigo);

                Calendar c = Calendar.getInstance();
                Date date = new Date();
                c.setTime(date);
                int dia = c.get(Calendar.DATE);
                int mes = c.get(Calendar.MONTH)+1;
                if (mes > 12) mes = mes - 12;
                int ano = c.get(Calendar.YEAR);
                String data =  dia + "/" + mes + "/" + ano;

                Convite convite = new Convite(user.getUsername(), codigo, "Estou te convidando para o meu evento!", data);

                Service service =  RetrofitConfig.getRetrofitInstance().create(Service.class);
                Call<Convite> call = service.postConvite(convite);

                call.enqueue(new Callback<Convite>() {
                    @Override
                    public void onResponse(Call<Convite> call, Response<Convite> response) {
                        if (response.isSuccessful()) {
                            new AlertDialog.Builder(context)
                            .setTitle("SUCESSO!")
                            .setMessage("Pessoa convidada com sucesso.")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            //.setNegativeButton(android.R.string.no, null)
                            .setIcon(R.drawable.ic_baseline_done_24)
                            .show();

                        }
                        else {
                            new AlertDialog.Builder(context)
                            .setTitle("ERRO!")
                            .setMessage("Esse usuário já é convidado desse evento\nOU\nesse evento não existe!")

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
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Convite> call, Throwable t) {
                        new AlertDialog.Builder(context)
                        .setTitle("FAILURE!")
                        .setMessage("Algo occorreu e não foi possível efetuar o convite.")

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
                        return;
                    }
                });
            }
        });

        return view;
    }
}
