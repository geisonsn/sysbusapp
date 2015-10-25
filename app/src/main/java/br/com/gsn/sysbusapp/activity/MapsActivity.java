package br.com.gsn.sysbusapp.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.parcelable.LocalizacaoLinhaParcelable;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private LocalizacaoLinhaParcelable linhaSelecionada;
    public Location localizacaoUsuario;
    private ArrayList<LocalizacaoLinhaParcelable> outrasLinhas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                linhaSelecionada = extras.getParcelable("linhaSelecionada");
                localizacaoUsuario = extras.getParcelable("localizacaoUsuario");
                outrasLinhas = extras.getParcelableArrayList("outrasLinhas");
            }
        } else {
            linhaSelecionada = savedInstanceState.getParcelable("linhaSelecionada");
            localizacaoUsuario = savedInstanceState.getParcelable("localizacaoUsuario");
            outrasLinhas = savedInstanceState.getParcelableArrayList("outrasLinhas");
        }

        setDadosLinhaSelecionada();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setDadosLinhaSelecionada() {
        String linha = ((TextView)findViewById(R.id.linha)).getText().toString() + linhaSelecionada.numeroLinha;
        String veiculo = ((TextView)findViewById(R.id.veiculo)).getText().toString() + linhaSelecionada.numeroRegistro;
        String atualizacao = ((TextView)findViewById(R.id.atualizacao)).getText().toString() + linhaSelecionada.dataHoraRegistro;

        ((TextView)findViewById(R.id.linha)).setText(linha);
        ((TextView)findViewById(R.id.veiculo)).setText(veiculo);
        ((TextView)findViewById(R.id.atualizacao)).setText(atualizacao);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("localizacaoUsuario", localizacaoUsuario);
        outState.putParcelable("linhaSelecionada", linhaSelecionada);
        outState.putParcelableArrayList("outrasLinhas", outrasLinhas);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        
        LatLng latLngLinha = new LatLng(Double.valueOf(linhaSelecionada.latitude), Double.valueOf(linhaSelecionada.longitude));
        LatLng latLngZoom = latLngLinha;

        if (localizacaoUsuario != null) {
            LatLng latLngUsuraio = new LatLng(Double.valueOf(localizacaoUsuario.getLatitude()), Double.valueOf(localizacaoUsuario.getLongitude()));
            MarkerOptions markerUsuario = new MarkerOptions()
                .title("Eu")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_black_36dp))
                .position(latLngUsuraio);
            map.addMarker(markerUsuario);
            latLngZoom = latLngUsuraio;
        }

        setLocationOutrasLinhas(map);

        map.setMyLocationEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngZoom, 15));

        map.addMarker(new MarkerOptions()
            .title("Linha " + linhaSelecionada.numeroLinha)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_bus_black_36dp))
            .snippet("Veículo " + linhaSelecionada.numeroRegistro)
            .position(latLngLinha));
    }

    private void setLocationOutrasLinhas(GoogleMap map) {
        for (LocalizacaoLinhaParcelable linha : outrasLinhas) {
            LatLng location = new LatLng(Double.valueOf(linha.latitude), Double.valueOf((linha.longitude)));
            map.addMarker(new MarkerOptions()
                    .title("Linha " + linha.numeroLinha)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_bus_black_36dp))
                    .snippet("Veículo " + linha.numeroRegistro)
                    .position(location));
        }

    }
}
