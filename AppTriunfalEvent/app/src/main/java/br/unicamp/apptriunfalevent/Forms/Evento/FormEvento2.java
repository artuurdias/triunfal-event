package br.unicamp.apptriunfalevent.Forms.Evento;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

import br.unicamp.apptriunfalevent.APIconfig.*;

import br.unicamp.apptriunfalevent.Models.*;
import br.unicamp.apptriunfalevent.R;
import br.unicamp.apptriunfalevent.databinding.ActivityFormEvento2Binding;
import br.unicamp.apptriunfalevent.ui.Activities.*;

public class FormEvento2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions marcador;
    private LatLng local;
    private ActivityFormEvento2Binding binding;
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private LocationManager  locationManager;
    private LocationListener locationListener;
    private EditText edtEndereco;
    private TextView tvLogradouro;
    private Evento evento;
    private Address localEvento;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFormEvento2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Permissoes.validarPermissoes(permissoes, this, 1);

        intent = getIntent();
        evento = (Evento) intent.getSerializableExtra("sessaoNome");
        tvLogradouro = (TextView) findViewById(R.id.tvInfoMaps_mapaEvento);
        edtEndereco = (EditText) findViewById(R.id.edtEndereco_mapaEvento);


        edtEndereco.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if (event.getRawX() >= edtEndereco.getTotalPaddingEnd()){
                        setEdtEndereco(binding.edtEnderecoMapaEvento.getText().toString());
                        binding.tvInfoMapsMapaEvento.setText(evento.getEndereco());                    }
                    return false;
                }

                return false;
            }
        });



        binding.btnProxMapaEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertaValidacaoEndereco();
            }
        });


        binding.btnBackMapaEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormEvento2.this, FormEvento.class);
                finish();
                startActivity(intent);
            }
        });

        binding.btnHomeMapaEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormEvento2.this, HomeActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }


    private void alertaValidacaoEndereco() {

        new AlertDialog.Builder(FormEvento2.this)
                .setTitle("Confirmação de endereço")
                .setMessage(evento.getEndereco())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        proxTela();
                    }
                })
                .setIcon(R.drawable.ic_baseline_warning_24)
                .show();

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LatLng latLng = new LatLng( location.getLatitude(), location.getLongitude());
                Log.d("Localizacao", "onLocationChanged: " + location.toString() );
                setEdtEndereco(latLng);
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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;
                Toast.makeText(FormEvento2.this, "lat: " + latitude + " long:" + longitude , Toast.LENGTH_SHORT).show();
                setEdtEndereco(latLng);
                binding.tvInfoMapsMapaEvento.setText(evento.getEndereco());
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000,
                    100,
                    locationListener
            );
        }
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
                binding.tvInfoMapsMapaEvento.setText(evento.getEndereco());
                Log.d("local", "LOCAL DO EVENTO: " + endereco.getAddressLine(0));
                Toast.makeText(FormEvento2.this, endereco.toString(), Toast.LENGTH_LONG);
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
                binding.tvInfoMapsMapaEvento.setText(evento.getEndereco());
                Log.d("local", "LOCAL DO EVENTO: " + endereco.getAddressLine(0));
                Toast.makeText(FormEvento2.this, endereco.toString(), Toast.LENGTH_LONG);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResultado : grantResults)
        {
            //permission denied (negada)
            if (permissaoResultado == PackageManager.PERMISSION_DENIED)
            {
                //Alerta
                alertaValidacaoPermissao();
            }
            else if (permissaoResultado == PackageManager.PERMISSION_GRANTED)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            100000,
                            100,
                            locationListener
                    );
                }
            }
        }
    }


    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FormEvento2.this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // finish();
                Toast.makeText(FormEvento2.this, evento.getEndereco(), Toast.LENGTH_SHORT).show();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void proxTela(){


        intent = new Intent(FormEvento2.this, FormEvento3.class);
        intent.putExtra("sessaoMaps", (Serializable) evento);
        startActivity(intent);
    }
}