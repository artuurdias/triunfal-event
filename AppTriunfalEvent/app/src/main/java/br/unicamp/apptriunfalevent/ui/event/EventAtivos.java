package br.unicamp.apptriunfalevent.ui.event;

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

public class EventAtivos extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GridView eventsGridView;
    private EventsVielModel homeViewModel;
    private FragmentEventsBinding binding;
    private AdapterEvents adapter;

    public EventAtivos() {
        // Required empty public constructor
    }

    public static EventAtivos newInstance(String param1, String param2) {
        EventAtivos fragment = new EventAtivos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_event_ativos, container, false);
        //Download JSON via Retrofit
        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);

        Session session = new Session(getContext());

        //Pegar a rota do Json

        Call<List<Evento>> call = service.getEventosUser(session.getusename());
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "deu certo", Toast.LENGTH_LONG).show();
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

    private void populateGridView(List<Evento> listaDog){
        eventsGridView = (GridView) getView().findViewById(R.id.gridEvents);
        adapter = new AdapterEvents(getContext(), getActivity(), listaDog);
        eventsGridView.setAdapter(adapter);
    }
}