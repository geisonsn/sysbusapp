package br.com.gsn.sysbusapp.business;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.fragment.ListContentFragment;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.parcelable.LocalizacaoLinhaParcelable;

/**
 * Created by Geison on 06/09/2015.
 */
public class HomeBusiness extends BusinessTaskOperation<Void, Integer, SpringRestResponse> implements
        GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener {

    private ListContentFragment listFragment;

    private LinhasEmDeslocamentoBusiness linhasEmDeslocamentoBusiness;
    private LinhasEmDeslocamentoProximasBusiness linhasEmDeslocamentoProximasBusiness;

    private BusinessTaskOperation delegate;

    private GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    public Location currentLocation;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 4000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    public Boolean mRequestingLocationUpdates;


    public HomeBusiness(ListContentFragment context) {
        this.context = context.getActivity();
        this.listFragment = context;
    }

    public void listarTodasLinhas() {
        if (linhasEmDeslocamentoBusiness == null) {
            linhasEmDeslocamentoBusiness = new LinhasEmDeslocamentoBusiness(listFragment);
        }

        if (this.delegate != null) {
            delegate.cancelTaskOperation();
        }

        this.delegate = linhasEmDeslocamentoBusiness;

        linhasEmDeslocamentoBusiness.listarLinhas();

    }

    public void listarLinhasProximas() {
        if (linhasEmDeslocamentoProximasBusiness == null) {
            linhasEmDeslocamentoProximasBusiness = new LinhasEmDeslocamentoProximasBusiness(listFragment);
        }

        if (this.delegate != null) {
            delegate.cancelTaskOperation();
        }

        this.delegate = linhasEmDeslocamentoProximasBusiness;

        linhasEmDeslocamentoProximasBusiness.mostrarStatusRequisicao();

        if (currentLocation == null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (currentLocation == null) {
                        Toast.makeText(context,
                                R.string.localizacao_nao_capturada,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        linhasEmDeslocamentoProximasBusiness.listarLinhas(currentLocation);
                    }
                }
            }, 2000);
        } else {
            linhasEmDeslocamentoProximasBusiness.listarLinhas(currentLocation);
        }
    }

    @Override
    public void onPreExecute() {
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(Void... params) {
        //Nao faz nada por enquanto
        return null;
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
        //Nao faz nada aqui por enquanto
    }

    @Override
    public void cancelTaskOperation() {
        delegate.cancelTaskOperation();
    }

    public LocalizacaoLinhaDTO getLocalizacaoLinha() {
        LocalizacaoLinhaDTO localizacaoLinhaDTO;
        if (delegate instanceof  LinhasEmDeslocamentoBusiness) {
            localizacaoLinhaDTO = linhasEmDeslocamentoBusiness.localizacaoLinhaDTO;
        } else {
            localizacaoLinhaDTO = linhasEmDeslocamentoProximasBusiness.localizacaoLinhaDTO;
        }
        return localizacaoLinhaDTO;
    }

    public ArrayList<LocalizacaoLinhaParcelable> listLocalizacaoLinhas() {
        if (delegate instanceof  LinhasEmDeslocamentoBusiness) {
            return linhasEmDeslocamentoBusiness.listLocalizacaoLinhas();
        } else {
            return linhasEmDeslocamentoProximasBusiness.listLocalizacaoLinhas();
        }
    }

    public void inicializarServicoLocalizacao() {
        buildGoogleApiClient();
    }

    public void capturarLocalizacao() {
        mRequestingLocationUpdates = true;
        startLocationUpdates();
    }

    public void startarServicoLocalizacao() {
        mGoogleApiClient.connect();
    }

    public void restartarServicoLocalizacao() {
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    public void pausarServicoLocalizacao() {
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    public void pararServicoLocalizacao() {
        mGoogleApiClient.disconnect();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        //Define o nível de acurácia da localização obtida
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    private void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
//        Toast.makeText(context, "Localizacao atual " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
