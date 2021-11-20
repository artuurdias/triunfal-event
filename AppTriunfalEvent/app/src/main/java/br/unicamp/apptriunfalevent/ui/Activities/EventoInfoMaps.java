package br.unicamp.apptriunfalevent.ui.Activities;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import br.unicamp.apptriunfalevent.Models.*;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.ActivityEventoInfoMapsBinding;

public class EventoInfoMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityEventoInfoMapsBinding binding;
    private Intent intent;
    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEventoInfoMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_infoMapEvent);
        mapFragment.getMapAsync(this);

        intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("sessaoInfoEvento");

        binding.btnBackInfoMapEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(EventoInfoMaps.this, EventoInfo.class);
                intent.putExtra("sessaoEventoPart", (Serializable)evento);
                startActivity(intent);
            }
        });

        setEdtEndereco(evento.getEndereco());

    }


    private void setEdtEndereco( String novoEndereco){

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault() );

        try {

            List<Address> listaEndereco = geocoder.getFromLocationName(novoEndereco, 3);
            if (listaEndereco != null && listaEndereco.size() > 0) {

                //populateGridView(listaEndereco);

                Address endereco = listaEndereco.get(0);
                Double lat = endereco.getLatitude();
                Double lon = endereco.getLongitude();

                mMap.clear();
                LatLng localUsuario = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(localUsuario).title("Meu local"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localUsuario, 18));
                binding.tvEnderecoInfoMapEvent.setText(evento.getEndereco());
                Log.d("local", "LOCAL DO EVENTO: " + endereco.getAddressLine(0));
                Toast.makeText(EventoInfoMaps.this, endereco.toString(), Toast.LENGTH_LONG);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        setEdtEndereco(evento.getEndereco());
    }
}