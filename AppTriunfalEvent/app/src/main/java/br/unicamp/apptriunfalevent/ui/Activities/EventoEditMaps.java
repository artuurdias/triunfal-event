package br.unicamp.apptriunfalevent.ui.Activities;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
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

import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.ActivityEventoEditMapsBinding;

public class EventoEditMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityEventoEditMapsBinding binding;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Intent intent;
    private Evento evento;
    private Evento eventoAntigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEventoEditMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_editMapEvent);
        mapFragment.getMapAsync(this);

        intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("sessaoEditEvento");
        try {
            eventoAntigo = new Evento(evento);
        } catch (Exception e) {
            e.printStackTrace();
        }



        setEdtEndereco(evento.getEndereco());

        binding.btnBackEditMapEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(EventoEditMaps.this, EventoEditInfo.class );
                intent.putExtra("sessaoMapsEdit", (Serializable) eventoAntigo);
                finish();
                startActivity(intent);
            }
        });

        binding.btnProxEditMapEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(EventoEditMaps.this, EventoEditInfo.class );
                intent.putExtra("sessaoMapsEdit", (Serializable) evento);
                finish();
                startActivity(intent);
            }
        });


        binding.edtEnderecoEditMapEvent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if (event.getRawX() >= binding.edtEnderecoEditMapEvent.getTotalPaddingEnd()){
                        setEdtEndereco(binding.edtEnderecoEditMapEvent.getText().toString());
                        binding.tvEnderecoEditMapEvent.setText(evento.getEndereco());                    }
                    return false;
                }

                return false;
            }
        });

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
                evento.setEndereco(endereco.getAddressLine(0));
                binding.tvEnderecoEditMapEvent.setText(evento.getEndereco());
                Log.d("local", "LOCAL DO EVENTO: " + endereco.getAddressLine(0));
                Toast.makeText(EventoEditMaps.this, endereco.toString(), Toast.LENGTH_LONG);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setEdtEndereco(LatLng latLong){


        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault() );

        try {

            List<Address> listaEndereco = geocoder.getFromLocation(latLong.latitude, latLong.longitude,1);
            if (listaEndereco != null && listaEndereco.size() > 0) {

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLong).title("Meu local"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 18));
                Address endereco = listaEndereco.get(0);
                evento.setEndereco(endereco.getAddressLine(0));
                binding.tvEnderecoEditMapEvent.setText(evento.getEndereco());
            Log.d("local", "LOCAL DO EVENTO: " + endereco.getAddressLine(0));
                Toast.makeText(EventoEditMaps.this, endereco.toString(), Toast.LENGTH_LONG);

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

        // Add a marker in Sydney and move the camera
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.d("Localizacao", "onLocationChanged: " + location.toString() );
                setEdtEndereco(evento.getEndereco());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        setEdtEndereco(evento.getEndereco());
        binding.tvEnderecoEditMapEvent.setText(evento.getEndereco());


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;
                Toast.makeText(EventoEditMaps.this, "lat: " + latitude + " long:" + longitude , Toast.LENGTH_SHORT).show();
                setEdtEndereco(latLng);
            }
        });
    }
}