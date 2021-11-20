package br.unicamp.apptriunfalevent.ui.convites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.Models.Convidado;
import br.unicamp.apptriunfalevent.Models.Convite;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.ui.Activities.HomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterConvites extends BaseAdapter {

    private List<Convite> lista;
    private Context context;
    private AlertDialog alerta;
    private Activity app;
    private Session session;
    private Evento evento;
    private Convidado convidado;
    private Convite convite;
    private GridView oGrid;

    private Context mContext;
    private List<Convite> web;

    public AdapterConvites(Context context, Activity app, List<Convite> list) {
        this.lista = list;
        this.context = context;
        this.app = app;
        this.oGrid = app.findViewById(R.id.gridConvites);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        convite = lista.get(position);

        if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.adapter_convite, parent, false);
            }

            TextView tvEventoNome = view.findViewById(R.id.tvNomeEvento_convite);
            TextView tvOK_convite = view.findViewById(R.id.tvOK_convite);
            TextView tvDELETE_convite = view.findViewById(R.id.tvDELETE_convite);


            Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);

            Call<Evento> call = service.getEvento(convite.getIdEvento());
            call.enqueue(new Callback<Evento>() {
                @Override
                public void onResponse(Call<Evento> call, Response<Evento> response) {

                    if (response.isSuccessful()) {
                        evento = (Evento) response.body();
                        tvEventoNome.setText(evento.getNome());
                    } else {
                        Toast.makeText(context, "ERRO EVENTO!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Evento> call, Throwable t) {
                    Toast.makeText(context, "ERRO EVENTO FAILURE!", Toast.LENGTH_LONG).show();
                }
            });


       /* if((dog.getImagem() != null) && (dog.getImagem().length()>0)){
            Picasso.get().load(dog.getImagem()).into(dogImageView);
        }else{
            Toast.makeText(context, "Não carregou a imagem", Toast.LENGTH_LONG).show();
        }*/



            tvOK_convite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aceitarConvite(position);
                    deletarConvite(position);
                }
            });

            tvDELETE_convite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deletarConvite(position);
                    notifyDataSetChanged();
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exibirEvento();
                }
            });

            return view;
    }


    private void exibirEvento() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = app.getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.dialog_event, null);
        //definimos para o botão do layout um clickListener

        TextView tvEvento_dialog  = view.findViewById(R.id.tvEvento_dialog);
        TextView tvData_dialog    = view.findViewById(R.id.tvData_dialog);
        TextView tvLocal_dialog   = view.findViewById(R.id.tvLocal_dialog);
        TextView tvResumo_dialog  = view.findViewById(R.id.tvDescricao_dialog);
        TextView tvOrganizador_dialog  = view.findViewById(R.id.tvOrganizador_dialog);
        TextView tvTipoEvento_dialog  = view.findViewById(R.id.tvTipoEvento_dialog);


        tvEvento_dialog.setText(evento.getNome());
        tvData_dialog.setText(evento.getData());
        tvLocal_dialog.setText(evento.getEndereco());
        tvResumo_dialog.setText(evento.getDescricao());
        tvOrganizador_dialog.setText(evento.getOrganizador());
        tvTipoEvento_dialog.setText(evento.getTipo());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Informações do evento:");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();
    }

    private void deletarConvite(int pos){
        convite = lista.get(pos);

        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Convite> call = service.deleteConvite(convite.getNomeUsuario(), convite.getIdEvento() );
        call.enqueue(new Callback<Convite>() {
            @Override
            public void onResponse(Call<Convite> call, Response<Convite> response)
            {
                if (response.isSuccessful())
                {
                    Toast.makeText(context, "Convite excluido!" + convite.getIdEvento(), Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(context, "Erro ao excluir convite!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Convite> call, Throwable t) {
                Toast.makeText(context, "Erro de conexão com API", Toast.LENGTH_LONG).show();
            }
        });

        lista.remove(pos);
        if (lista.size() == 0) {
            oGrid.setAdapter(null);
            exibirErro();
        }
        else {
            try {
                AdapterConvites adapter = (AdapterConvites) this.clonee();
                oGrid.setAdapter(adapter);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void aceitarConvite(int pos){

        convite = lista.get(pos);

        session = new Session(context);

        convidado = new Convidado(session.getusename(),convite.getIdEvento());

        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Call<Convidado> call = service.postConvidado(convidado);
        call.enqueue(new Callback<Convidado>() {
            @Override
            public void onResponse(Call<Convidado> call, Response<Convidado> response)
            {
                if (response.isSuccessful())
                {
                    Toast.makeText(context, "Convite aceito!" +  convidado.getIdEvento(), Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(context, "Erro ao aceitar convite!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Convidado> call, Throwable t)
            {
                Toast.makeText(context, "Erro de conexão com API", Toast.LENGTH_LONG).show();
            }
        });
    }



    public void inicializarTela(){

        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Session session = new Session(context);

        Call<List<Convite>> call = service.getConvitesUser(session.getusename());
        call.enqueue(new Callback<List<Convite>>() {
            @Override
            public void onResponse(Call<List<Convite>> call, Response<List<Convite>> response) {

                if(response.isSuccessful()){

                    List<Convite> listConvite = response.body();
                    populateGridView(listConvite);

                }else{
                    String errorMessage = response.errorBody().toString();
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "entrou no else do response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Convite>> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                Toast.makeText(context, messageProblem, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "entrou no else do Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void populateGridView(List<Convite> lista){
        GridView conviteGridView = (GridView) app.findViewById(R.id.gridConvites);

        if(lista.size() > 0) {
            AdapterConvites adapter = new AdapterConvites(context, app, lista);
            conviteGridView.setAdapter(adapter);
        }
        else{
            exibirErro();
        }
    }

    private void exibirErro() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        new AlertDialog.Builder(context)
                .setTitle("Nenhum novo convite!")
                .setMessage("Quando você tiver novos convites de eventos, você os verá aqui.")

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

    private Object clonee ()
    {
        AdapterConvites ret = null;

        try
        {
            ret = new AdapterConvites(this);
        }
        catch (Exception erro)
        {}

        return ret;
    }

    public AdapterConvites(AdapterConvites modelo) throws Exception
    {
        this.lista = modelo.lista;
        this.context = modelo.context;
        this.app = modelo.app;
        this.oGrid = modelo.oGrid;
    }
}
