package br.unicamp.apptriunfalevent.ui.profile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.Models.Usuario;
import br.unicamp.apptriunfalevent.ui.Activities.WelcomeActivity;
import br.unicamp.apptriunfalevent.databinding.FragmentProfileBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*
 *
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private ProfileViewModel homeViewModel;
    private FragmentProfileBinding binding;

    private EditText edtDataProfile, edtSenhaProfile, edtEmailProfile, edtNomeProfile ;
    private TextView tvLogoutProfile, tvUsername;
    private Button btnAtualizarProfile;
    private Session session;//global variable


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inicializarComponents();

       //Download JSON via Retrofit
        Service service  = RetrofitConfig.getRetrofitInstance().create(Service.class);

        session = new Session(getContext()); //in oncreate
        //and now we set sharedpreference then use this like

        String username = session.getusename();

        //Pegar a rota do Json
        Call<Usuario> call = service.getUsuario(username);

         call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "deu certo", Toast.LENGTH_LONG).show();
                    tvUsername.setText(response.body().getUsername());
                    edtNomeProfile.setText(response.body().getNome());
                    edtDataProfile.setText(response.body().getNascimento());
                    edtEmailProfile.setText(response.body().getEmail());
                    edtSenhaProfile.setText(response.body().getSenha());
                }else{
                    String errorMessage = response.errorBody().toString();
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "entrou no else do response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getActivity(), "Erro dois", Toast.LENGTH_SHORT).show();
            }
        }); /**/

        tvLogoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                startActivity(intent);
            }
        });

        btnAtualizarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // updateUser();
            }
        });
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    public void updateUser(){
        String strId    = tvUsername.getText().toString();
        String strNome  = edtNomeProfile.getText().toString();
        String strData  = edtDataProfile.getText().toString();
        String strEmail = edtEmailProfile.getText().toString();
        String strSenha = edtSenhaProfile.getText().toString();

        Usuario usuario = new Usuario(strId, strNome, strData, strEmail, strSenha);
        Service service = RetrofitConfig.getRetrofitInstance().create(Service.class);

        Call<Usuario> call = service.putUsuario(strId,usuario);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "deu certo", Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(getActivity(), ProfileFragment.class);
                    //startActivity(intent);
                }else{
                    String errorMessage = response.errorBody().toString();
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "entrou no else do response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getActivity(), "Erro dois", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inicializarComponents()
    {
        btnAtualizarProfile = binding.btnAtualizarProfile;
        tvLogoutProfile = binding.tvLogoutProfile;
        edtDataProfile = binding.edtDataProfile;
        edtEmailProfile = binding.edtEmailProfile;
        edtNomeProfile = binding.edtNomeProfile;
        edtSenhaProfile = binding.edtSenhaProfile;
        tvUsername = binding.tvUsername;
    }


}