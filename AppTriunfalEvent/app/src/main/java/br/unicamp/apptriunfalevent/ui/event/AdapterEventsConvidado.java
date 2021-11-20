package br.unicamp.apptriunfalevent.ui.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.Serializable;
import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.*;
import br.unicamp.apptriunfalevent.Models.*;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.ui.Activities.*;
import br.unicamp.apptriunfalevent.ui.convites.AdapterConvites;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterEventsConvidado extends BaseAdapter {

    private List<Convidado> listConvidado;
    private Context context;
    private Activity app;
    private Convidado convidado;
    private GridView oGrid;

    public AdapterEventsConvidado(Context context, Activity app, List<Convidado> list){
        this.listConvidado = list;
        this.context = context;
        this.app = app;
        this.oGrid = this.app.findViewById(R.id.gridEventsConvidados);
    }

    @Override
    public int getCount() {
        return listConvidado.size();
    }

    @Override
    public Object getItem(int position) {
        return listConvidado.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.adapter_convidado, parent,false);
        }

        TextView tvEventoNome = view.findViewById(R.id.tvNome_convidado);
        Button btnExcluir = view.findViewById(R.id.btnExcluir_convidado);

        convidado = listConvidado.get(position);

        tvEventoNome.setText(convidado.getNomeUsuario());


       /* if((dog.getImagem() != null) && (dog.getImagem().length()>0)){
            Picasso.get().load(dog.getImagem()).into(dogImageView);
        }else{
            Toast.makeText(context, "Não carregou a imagem", Toast.LENGTH_LONG).show();
        }*/


        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convidado = listConvidado.get(position);

                Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Session session = new Session(context);
                Call<Convidado> call = service.deleteConvidado(convidado.getNomeUsuario(), convidado.getIdEvento());
                call.enqueue(new Callback<Convidado>() {

                    @Override
                    public void onResponse(Call<Convidado> call, Response<Convidado> response) {

                        if (response.isSuccessful()) {
                            Toast.makeText(context, convidado.getNomeUsuario() + " não participa mais do evento", Toast.LENGTH_LONG).show();
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

                listConvidado.remove(position);
                if (listConvidado.size() == 0)
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

    private Object cloneeee ()
    {
        AdapterEventsConvidado ret = null;

        try
        {
            ret = new AdapterEventsConvidado(this);
        }
        catch (Exception erro)
        {}

        return ret;
    }



    public AdapterEventsConvidado(AdapterEventsConvidado modelo) throws Exception
    {
        this.listConvidado = modelo.listConvidado;
        this.context = modelo.context;
        this.app = modelo.app;
        this.oGrid = modelo.oGrid;
    }

}
