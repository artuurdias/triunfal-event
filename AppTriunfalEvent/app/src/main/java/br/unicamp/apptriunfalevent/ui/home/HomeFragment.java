package br.unicamp.apptriunfalevent.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.Forms.Evento.FormEvento;
import br.unicamp.apptriunfalevent.Forms.FormConvidar;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.FragmentHomeBinding;
import br.unicamp.apptriunfalevent.ui.Activities.HomeActivity;
import br.unicamp.apptriunfalevent.ui.Activities.IngressarEvento;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnCriarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FormEvento.class);
                startActivity(intent);
            }
        });

        binding.btnConvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Session session = new Session(getContext());
                Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);
                Call<List<Evento>> call = service.getEventosOrg(session.getusename());

                call.enqueue(new Callback<List<Evento>>() {
                    @Override
                    public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                        if (response.isSuccessful()) {
                            if (response.body().size() > 0) {
                                startActivity(new Intent(getActivity(), FormConvidar.class));
                            }
                            else {
                                new AlertDialog.Builder(getContext())
                                        .setTitle("ERRO!")
                                        .setMessage("Você não possui eventos, não é possível convidar outras pessoas!")

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
                                return;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Evento>> call, Throwable t) {
                        Toast.makeText(getActivity(), "ERRO! FAILURE!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.btnCriarLembrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), IngressarEvento.class));
            }
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}