package br.unicamp.apptriunfalevent.ui.event;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.WelcomeActivity;
import br.unicamp.apptriunfalevent.databinding.FragmentEventsBinding;
import br.unicamp.apptriunfalevent.databinding.FragmentProfileBinding;
import br.unicamp.apptriunfalevent.ui.profile.ProfileViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class EventsFragment extends Fragment {


    private EventsVielModel homeViewModel;
    private FragmentEventsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(EventsVielModel.class);

        binding = FragmentEventsBinding.inflate(inflater, container, false);
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