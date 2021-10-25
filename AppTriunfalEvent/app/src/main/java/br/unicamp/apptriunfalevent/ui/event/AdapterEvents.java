package br.unicamp.apptriunfalevent.ui.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;

public class AdapterEvents extends BaseAdapter {

    private List<Evento> lista;
    private Context context;
    private AlertDialog alerta;
    private Activity app;

    public AdapterEvents(Context context, Activity app, List<Evento>list){
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_events, parent,false);
        }

        TextView tvEventoNome = view.findViewById(R.id.tvEventoNome_grid);
        TextView tvEventoData = view.findViewById(R.id.tvEventoData_grid);

        Evento evento = lista.get(position);

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

             /*    Toast.makeText(context, evento.getNome(), Toast.LENGTH_LONG).show();
                Toast.makeText(context, evento.getData(), Toast.LENGTH_LONG).show();
                Toast.makeText(context, evento.getLocal(), Toast.LENGTH_LONG).show();*/

                final Dialog dialog = new Dialog(context);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_event);

                final TextView tvEvento_dialog  = dialog.findViewById(R.id.tvEvento_dialog);
                final TextView tvData_dialog    = dialog.findViewById(R.id.tvData_dialog);
                final TextView tvLocal_dialog   = dialog.findViewById(R.id.tvLocal_dialog);
                final TextView tvResumo_dialog  = dialog.findViewById(R.id.tvResumo_dialog);


                tvEvento_dialog.setText(evento.getNome());
                tvData_dialog.setText(evento.getData());
                tvLocal_dialog.setText(evento.getLocal());
                tvResumo_dialog.setText(evento.getTipo());

                exemplo_layout();
            }
        });
        return view;
    }


    private void exemplo_layout() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        LayoutInflater li = app.getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.dialog_event, null);
        //definimos para o botão do layout um clickListener


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Titulo");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();
    }
}
