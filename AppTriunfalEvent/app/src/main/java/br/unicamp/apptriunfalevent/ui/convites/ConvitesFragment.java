package br.unicamp.apptriunfalevent.ui.convites;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.*;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.FragmentConvitesBinding;
import br.unicamp.apptriunfalevent.databinding.*;
import br.unicamp.apptriunfalevent.ui.event.*;
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(ConvitesVielModel.class);

        binding = FragmentConvitesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tvResposta = (TextView) getActivity().findViewById(R.id.tvResposta);

        /*
        final Button btnLogoutProfile = binding.btnLogoutProfile;
        btnLogoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                startActivity(intent);
            }
        });
        */



        retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getUser();

        return root;
    }

    private void getUser(){

        Service service = retrofit.create(Service.class);
        Call<Usuario> call = service.getUsuario();
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Toast.makeText(getContext(), "SUCESSOO", Toast.LENGTH_LONG).show();

                if(response.isSuccessful()){

                    Toast.makeText(getContext(), "BOAAAAA", Toast.LENGTH_LONG).show();

                    Usuario user = (Usuario) response.body();
                    tvResposta.setText( user.getNome());
                    //tvResposta.setText(user.getNome() + " - " + user.getEmail());
                }
               /* else
                {
                    String errorMessage = response.errorBody().toString();
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();

                    Toast.makeText(getActivity(), "fudeu", Toast.LENGTH_LONG).show();
                }*/
               else {
                try {
                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                    Toast.makeText(getContext(), jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                String messageProblem = t.getMessage().toString();
                //
                //
                Toast.makeText(getActivity(), messageProblem, Toast.LENGTH_LONG).show();
                //tvResposta.setText("messageProblem");
                Toast.makeText(getActivity(), "err", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void populateGridView(List<Usuario> lista){
        userGridView = (GridView) getActivity().findViewById(R.id.userGridView);
        adapter = new GridViewAdapter(getContext(),lista);
        userGridView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}