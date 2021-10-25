package br.unicamp.apptriunfalevent.ui.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.FragmentEventsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class EventsFragment extends Fragment {

    private GridView eventsGridView;
    private EventsVielModel homeViewModel;
    private FragmentEventsBinding binding;
    private AdapterEvents adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(EventsVielModel.class);

        binding = FragmentEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //Download JSON via Retrofit
        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);

        //Pegar a rota do Json
        Call<List<Evento>> call = service.getEventos();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void populateGridView(List<Evento> listaDog){
        eventsGridView = (GridView) getView().findViewById(R.id.gridEvents);
        adapter = new AdapterEvents(getContext(), getActivity(), listaDog);
        eventsGridView.setAdapter(adapter);
    }

}