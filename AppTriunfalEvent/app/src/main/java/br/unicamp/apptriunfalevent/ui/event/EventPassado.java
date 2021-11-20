package br.unicamp.apptriunfalevent.ui.event;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.FragmentEventsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventPassado extends Fragment {



    private GridView eventsGridView;
    private EventsVielModel homeViewModel;
    private FragmentEventsBinding binding;
    private AdapterEventsPassado adapter;


    public EventPassado() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_event_passado, container, false);
        //Download JSON via Retrofit
        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);

        Session session = new Session(getContext());

        //Pegar a rota do Json

        Call<List<Evento>> call = service.getEventosPass(session.getusename());
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {

                if(response.isSuccessful()){
                    populateGridView(response.body());
                }else{
                    String errorMessage = response.errorBody().toString();
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "entrou no else do response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                Toast.makeText(getContext(), messageProblem, Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "entrou no else do Failure", Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }

    private void populateGridView(List<Evento> lista) {
        eventsGridView = (GridView) getView().findViewById(R.id.gridEventoPassado);

        if (lista.size() > 0) {
            adapter = new AdapterEventsPassado(getContext(), getActivity(), lista);
            eventsGridView.setAdapter(adapter);
        } else {
            eventsGridView.setAdapter(null);
            //exibirErro();
        }
    }


    private void exibirErro() {

        new AlertDialog.Builder(getContext())
                .setTitle("Sem eventos!")
                .setMessage("Você não tem histórico de participação em eventos, caso participe de algum, o verá aqui após o seu término.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                //.setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_baseline_warning_24)
                .show();
    }
}