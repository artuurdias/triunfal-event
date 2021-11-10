package br.unicamp.apptriunfalevent.ui.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ConcurrentModificationException;
import java.util.List;

import br.unicamp.apptriunfalevent.APIconfig.RetrofitConfig;
import br.unicamp.apptriunfalevent.APIconfig.Service;
import br.unicamp.apptriunfalevent.APIconfig.Session;
import br.unicamp.apptriunfalevent.Models.Convidado;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.FragmentEventsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2, tab3;
    private PagerAdapter pagerAdapter;
    private View rootView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.fragment_events, container, false);


        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);

        tab1 =  (TabItem) rootView.findViewById(R.id.eventAtuais);
        tab2 = (TabItem) rootView.findViewById(R.id.eventDisponiveis);
        tab3 = (TabItem) rootView.findViewById(R.id.eventPassados);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        pagerAdapter = new PageAdapter(getActivity().getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
    }

}