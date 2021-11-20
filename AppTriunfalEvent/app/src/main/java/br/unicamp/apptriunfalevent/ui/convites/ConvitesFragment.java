package br.unicamp.apptriunfalevent.ui.convites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.*;
import br.unicamp.apptriunfalevent.ui.Activities.HomeActivity;
import br.unicamp.apptriunfalevent.Models.Convite;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConvitesFragment extends Fragment {

    private ConvitesVielModel homeViewModel;
    private FragmentConvitesBinding binding;
    private GridView conviteGridView;
    private AdapterConvites adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(ConvitesVielModel.class);

        binding = FragmentConvitesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inicializarTela();

        binding.refreshConvite.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                inicializarTela();
                binding.refreshConvite.setRefreshing(false);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void populateGridView(List<Convite> lista){
        conviteGridView = (GridView) getView().findViewById(R.id.gridConvites);

        if (lista.size() > 0) {
            adapter = new AdapterConvites(getContext(), getActivity(), lista);
            conviteGridView.setAdapter(adapter);
        }
        else {
            conviteGridView.setAdapter(null);
            exibirErro();
        }
    }

    public void inicializarTela(){

        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);
        Session session = new Session(getContext());

        Call<List<Convite>> call = service.getConvitesUser(session.getusename());
        call.enqueue(new Callback<List<Convite>>() {
            @Override
            public void onResponse(Call<List<Convite>> call, Response<List<Convite>> response) {

                if(response.isSuccessful()){

                    List<Convite> listConvite = response.body();
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
    }

    private void exibirErro() {
        //LayoutInflater é utilizado para inflar nosso layout em uma view.
        //-pegamos nossa instancia da classe
        new AlertDialog.Builder(getContext())
                .setTitle("Nenhum novo convite!")
                .setMessage("Quando você tiver novos convites de eventos, você os verá aqui.")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                //.setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.ic_baseline_warning_24)
                .show();
    }
}