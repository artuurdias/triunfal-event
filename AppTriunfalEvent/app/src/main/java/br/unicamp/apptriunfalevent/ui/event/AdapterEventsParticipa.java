package br.unicamp.apptriunfalevent.ui.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import java.io.Serializable;
import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.*;
import br.unicamp.apptriunfalevent.ui.Activities.*;
import br.unicamp.apptriunfalevent.Models.*;
import br.unicamp.apptriunfalevent.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterEventsParticipa extends BaseAdapter {

    private List<Evento> lista;
    private Context context;
    private AlertDialog alerta;
    private Activity app;
    private Evento evento;
    private GridView oGrid;

    public AdapterEventsParticipa(Context context, Activity app, List<Evento>list){
        this.lista = list;
        this.context = context;
        this.app = app;
        this.oGrid = app.findViewById(R.id.gridEventsParticipa);
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

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.adapter_events_part, parent,false);
        }

        TextView tvEventoNome = view.findViewById(R.id.tvEventoNome_gridPart);
        TextView tvEventoData = view.findViewById(R.id.tvEventoData_gridPart);
        AppCompatButton btnEdit = view.findViewById(R.id.btnGerenciarEvento_gridPart);
        AppCompatButton btnSair = view.findViewById(R.id.btnSairEvento_gridPart);

        evento = lista.get(position);

        tvEventoNome.setText(evento.getNome());
        tvEventoData.setText(evento.getData());


       /* if((dog.getImagem() != null) && (dog.getImagem().length()>0)){
            Picasso.get().load(dog.getImagem()).into(dogImageView);
        }else{
            Toast.makeText(context, "Não carregou a imagem", Toast.LENGTH_LONG).show();
        }*/

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exibirEvento();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventoInfo.class);
                evento = lista.get(position);
                intent.putExtra("sessaoEventoPart", (Serializable) evento);
                context.startActivity(intent);
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evento = lista.get(position);

                Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Session session = new Session(context);
                Call<Convidado> call = service.deleteConvidado(session.getusename(),evento.getId());
                call.enqueue(new Callback<Convidado>() {
                    @Override
                    public void onResponse(Call<Convidado> call, Response<Convidado> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Você não participa mais do evento: " + evento.getNome(), Toast.LENGTH_LONG).show();
                        } else {
                            String errorMessage = response.errorBody().toString();
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                            Toast.makeText(context, "entrou no else do response", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Convidado> call, Throwable t) {
                        String messageProblem = t.getMessage().toString();
                        Toast.makeText(context, messageProblem, Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "entrou no else do Failure", Toast.LENGTH_LONG).show();
                    }
                });

                lista.remove(position);
                if (lista.size() == 0)
                {
                    oGrid.setAdapter(null);
                    notifyDataSetChanged();
                }
                else {
                    try {
                        AdapterEventsParticipa adapter = (AdapterEventsParticipa) cloneeee();
                        oGrid.setAdapter(adapter);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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


    private Object cloneeee ()
    {
        AdapterEventsParticipa ret = null;

        try
        {
            ret = new AdapterEventsParticipa(this);
        }
        catch (Exception erro)
        {}

        return ret;
    }

    private AdapterEventsParticipa(AdapterEventsParticipa modelo) throws Exception
    {
        this.lista = modelo.lista;
        this.context = modelo.context;
        this.app = modelo.app;
        this.oGrid = modelo.oGrid;
    }

}
