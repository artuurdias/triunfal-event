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

import br.unicamp.apptriunfalevent.APIconfig.*;
import br.unicamp.apptriunfalevent.Models.*;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.FragmentEventsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventParticipa extends Fragment {


    private GridView eventsGridView;
    private EventsVielModel homeViewModel;
    private FragmentEventsBinding binding;
    private AdapterEventsParticipa adapter;

    public EventParticipa() {
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
        View root = inflater.inflate(R.layout.fragment_event_participa, container, false);
        //Download JSON via Retrofit
        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);

        Session session = new Session(getContext());


        Call<List<Evento>> call = service.getEventosPart(session.getusename());
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

    private void populateGridView(List<Evento> lista){
        eventsGridView = (GridView) getView().findViewById(R.id.gridEventsParticipa);

        if (lista.size() > 0) {
            adapter = new AdapterEventsParticipa(getContext(), getActivity(), lista);
            eventsGridView.setAdapter(adapter);
        }
        else {
            eventsGridView.setAdapter(null);
            //exibirErro();
        }
    }

    private void exibirErro() {

        new AlertDialog.Builder(getContext())
                .setTitle("Sem eventos!")
                .setMessage("Você não participa de eventos, caso participar de algum o verá ele aqui.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                //.setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_baseline_warning_24)
                .show();
    }
}