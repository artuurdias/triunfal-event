package br.unicamp.apptriunfalevent.ui.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;

public class AdapterEventsPassado extends BaseAdapter {

    private List<Evento> lista;
    private Context context;
    private AlertDialog alerta;
    private Activity app;
    private Evento evento;

    public AdapterEventsPassado(Context context, Activity app, List<Evento>list){
        this.lista = list;
        this.context = context;
        this.app = app;
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_events_pass, parent,false);
        }

        TextView tvEventoNome = view.findViewById(R.id.tvEventoNome_gridPass);
        TextView tvEventoData = view.findViewById(R.id.tvEventoData_gridPass);

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
}
