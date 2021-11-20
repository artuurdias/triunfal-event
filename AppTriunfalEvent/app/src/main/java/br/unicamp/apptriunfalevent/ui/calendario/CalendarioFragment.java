package br.unicamp.apptriunfalevent.ui.calendario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.FragmentCalendarioBinding;
import br.unicamp.apptriunfalevent.databinding.FragmentConvitesBinding;
import br.unicamp.apptriunfalevent.databinding.FragmentEventsBinding;
import br.unicamp.apptriunfalevent.ui.event.EventsVielModel;


public class CalendarioFragment extends Fragment {

    private CalendarioViewModel homeViewModel;
    private FragmentCalendarioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(CalendarioViewModel.class);

        binding = FragmentCalendarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final Button btnLogoutProfile = binding.btnLogoutProfile;
        btnLogoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                startActivity(intent);
            }
        });*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}