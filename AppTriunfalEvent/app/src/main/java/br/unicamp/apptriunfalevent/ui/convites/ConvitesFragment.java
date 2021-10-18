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
    private GridView userGridView;
    private GridViewAdapter adapter;
    private Retrofit retrofit;
    private TextView tvResposta;
    private RecyclerView recyclerViewConvites;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(ConvitesVielModel.class);

        binding = FragmentConvitesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tvResposta = (TextView) getActivity().findViewById(R.id.tvConvites_convite);

        binding.fbHomeConvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // adaptador = new MeuAdaptador(list);
        // recyclerView.setAdapter(adaptador);
        // recyclerViewConvites = getActivity().findViewById(R.id.recyclerConvites_convite);
        // recyclerViewConvites.setLayoutManager(new LinearLayoutManager(get()));
        // recyclerViewConvites.setHasFixedSize(true);

/*
        AdapterConvites adapter = new AdapterConvites();
        recyclerViewConvites.setAdapter(adapter);
 */
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}