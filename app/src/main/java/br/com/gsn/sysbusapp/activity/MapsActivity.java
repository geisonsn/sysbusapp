package br.com.gsn.sysbusapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.parcelable.LocalizacaoLinhaParcelable;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private LocalizacaoLinhaParcelable localizacaoLinha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                localizacaoLinha = extras.getParcelable("localizacaoLinha");
            }
        } else {
            localizacaoLinha = savedInstanceState.getParcelable("localizacaoLinha");
        }

        setDadosLinha();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setDadosLinha() {
        String linha = ((TextView)findViewById(R.id.linha)).getText().toString() + localizacaoLinha.numeroLinha;
        String veiculo = ((TextView)findViewById(R.id.veiculo)).getText().toString() + localizacaoLinha.numeroRegistro;
        String atualizacao = ((TextView)findViewById(R.id.atualizacao)).getText().toString() + localizacaoLinha.dataHoraRegistro;

        ((TextView)findViewById(R.id.linha)).setText(linha);
        ((TextView)findViewById(R.id.veiculo)).setText(veiculo);
        ((TextView)findViewById(R.id.atualizacao)).setText(atualizacao);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("localizacaoLinha", localizacaoLinha);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng latLng = new LatLng(Double.valueOf(localizacaoLinha.latitude), Double.valueOf(localizacaoLinha.longitude));

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

        map.addMarker(new MarkerOptions()
                .title("Linha " + localizacaoLinha.numeroLinha)
                .snippet("Veículo " + localizacaoLinha.numeroRegistro)
                .position(latLng));
    }
}
