package br.unicamp.apptriunfalevent.ui.convites;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.*;
import br.unicamp.apptriunfalevent.HomeActivity;
import br.unicamp.apptriunfalevent.Models.Convite;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.FragmentConvitesBinding;
import br.unicamp.apptriunfalevent.databinding.*;
import br.unicamp.apptriunfalevent.ui.event.*;
import br.unicamp.apptriunfalevent.ui.home.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConvitesFragment extends Fragment {


    private ConvitesVielModel homeViewModel;
    private FragmentConvitesBinding binding;
    private GridView conviteGridView;
    private AdapterConvites adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(ConvitesVielModel.class);

        binding = FragmentConvitesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.fbHomeConvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });


        //Download JSON via Retrofit
        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);

        //Pegar a rota do Json
        Call<List<Convite>> call = service.getConvites();
        call.enqueue(new Callback<List<Convite>>() {
            @Override
            public void onResponse(Call<List<Convite>> call, Response<List<Convite>> response) {

                if(response.isSuccessful()){

                    List<Convite> listConvite = response.body();
                    Toast.makeText(getContext(), listConvite.get(0).getNomeUsuario(), Toast.LENGTH_LONG).show();
                    populateGridView(listConvite);
                }else{
                    String errorMessage = response.errorBody().toString();
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "entrou no else do response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Convite>> call, Throwable t) {
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

    private void populateGridView(List<Convite> lista){
        conviteGridView = (GridView) getView().findViewById(R.id.gridConvites);
        adapter = new AdapterConvites(getContext(), getActivity(), lista);
        conviteGridView.setAdapter(adapter);
    }

}