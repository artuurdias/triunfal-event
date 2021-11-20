package br.unicamp.apptriunfalevent.ui.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.Models.Convidado;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterEventsLivre extends BaseAdapter {

    private List<Evento> lista;
    private Context context;
    private AlertDialog alerta;
    private Activity app;
    private Evento evento;
    private GridView oGrid;

    public AdapterEventsLivre(Context context, Activity app, List<Evento>list){
        this.lista = list;
        this.context = context;
        this.app = app;
        this.oGrid = app.findViewById(R.id.gridEventsLivre);
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_event_disp, parent,false);
        }

        TextView tvEventoNome = view.findViewById(R.id.tvEventoNome_gridDisp);
        TextView tvEventoData = view.findViewById(R.id.tvEventoData_gridDisp);
        AppCompatButton btnEdit = view.findViewById(R.id.btnParticiparEvento_gridDisp);

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
                evento = lista.get(position);
                exibirEvento();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evento = lista.get(position);

                Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Session session = new Session(context);
                Convidado convidado = new Convidado(session.getusename(), evento.getId());
                Call<Convidado> call = service.postConvidado(convidado);
                call.enqueue(new Callback<Convidado>() {
                    @Override
                    public void onResponse(Call<Convidado> call, Response<Convidado> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Você agora participa do evento: " + evento.getNome(), Toast.LENGTH_LONG).show();
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
                        AdapterEventsLivre adapter = (AdapterEventsLivre) cloneeee();
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
        AdapterEventsLivre ret = null;

        try
        {
            ret = new AdapterEventsLivre(this);
        }
        catch (Exception erro)
        {}

        return ret;
    }

    private AdapterEventsLivre(AdapterEventsLivre modelo) throws Exception
    {
        this.lista = modelo.lista;
        this.context = modelo.context;
        this.app = modelo.app;
        this.oGrid = modelo.oGrid;
    }

}
