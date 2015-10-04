package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessDialogTaskOperation;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaInsertDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.model.VeiculoDTO;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.ConnectionUtil;
import br.com.gsn.sysbusapp.util.Dates;
import br.com.gsn.sysbusapp.util.PreferencesUtil;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 06/09/2015.
 */
public class CheckinBusiness extends BusinessDialogTaskOperation<LocalizacaoLinhaInsertDTO, Integer, SpringRestResponse> implements
        GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener {

    private DialogInterface dialog;
    private TemplateAsyncTask<LocalizacaoLinhaInsertDTO, Integer, SpringRestResponse> task;
    private VeiculoBusiness veiculoBusiness;
    public VeiculoDTO veiculo;


    private GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    private Location currentLocation;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 4000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    public CheckinBusiness(Activity context) {
        this.context = context;
    }


    public void listarVeiculos(String linha, View dialog) {
        if (veiculoBusiness == null) {
            this.veiculoBusiness = new VeiculoBusiness(context, dialog);
        }
        this.veiculoBusiness.listarVeiculos(linha, dialog);
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(LocalizacaoLinhaInsertDTO... params) {
        return SpringRestClient.post(context, UrlServico.URL_CHECKIN, params[0], LocalizacaoLinhaInsertDTO.class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {

        response.setOnHttpCreated(new AbstractSpringRestResponse.OnHttpCreated() {
            @Override
            public void doThis() {
                Toast.makeText(context, "Checkin realizado.", Toast.LENGTH_SHORT).show();
                stopLocationUpdates();
                onCloseDialog();
            }
        });

        response.executeCallbacks();
        super.handleProgressRequest();
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }

    @Override
    public void onConfirmButton(DialogInterface dialog) {
        this.dialog = dialog;
        Dialog dialogFragment = ((Dialog) dialog);

        if (ConnectionUtil.isNetworkConnected(context)) {
            if (formValid(dialogFragment)) {
                if (ConnectionUtil.isNetworkConnected(context)) {
                    if (currentLocation == null) {
                        Toast.makeText(context, "Sua localização ainda não foi capturada", Toast.LENGTH_SHORT).show();
                    } else {

                        LocalizacaoLinhaInsertDTO l = new LocalizacaoLinhaInsertDTO();

                        String lotacaoVeiculo = null;
                        RadioGroup lotacao = (RadioGroup) dialogFragment.findViewById(R.id.lotacao);
                        int lotacaoEscolhida = lotacao.getCheckedRadioButtonId();
                        if (lotacaoEscolhida != -1) {
                            RadioButton opcaoSelecionada = (RadioButton) dialogFragment.findViewById(lotacaoEscolhida);
                            int index = lotacao.indexOfChild(opcaoSelecionada);
                            lotacaoVeiculo = String.valueOf((index + 1));
//                        Toast.makeText(context, " lotacao " + index, Toast.LENGTH_SHORT).show();
                        }

                        EditText edtStatus = (EditText) dialogFragment.findViewById(R.id.status);
                        String status = null;
                        if (!TextUtils.isEmpty(edtStatus.getText())) {
                            status = edtStatus.getText().toString();
                            l.setStatus(status);
                        }

                        l.setIdLinha(this.veiculo.getIdLinha());
                        l.setIdVeiculoLinha(this.veiculo.getIdVeiculo());
                        l.setLotacaoVeiculo(lotacaoVeiculo);
                        l.setLatitude(String.valueOf(currentLocation.getLatitude()));
                        l.setLongitude(String.valueOf(currentLocation.getLongitude()));
                        l.setIdUsuario(PreferencesUtil.getInstance(context).getIdUsuario());
                        l.setDataHoraRegistro(Dates.getCurrentDate(Dates.FORMAT_PT_BR_DATE_HOUR));

                        super.handleProgressRequest();
                        task = new TemplateAsyncTask(this);
                        task.execute(l);
                    }
                } else {
                    Toast.makeText(context, R.string.sem_conexao_com_internet, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(context, R.string.voce_nao_esta_conectado, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelButton(DialogInterface dialog) {
        super.onCancelButton(dialog);
    }

    private boolean formValid(Dialog dialog) {
        AutoCompleteTextView linha = (AutoCompleteTextView) dialog.findViewById(R.id.linha);
        if (veiculo == null) {
            linha.setError("Selecione uma linha a partir da lista exibida aqui");
            linha.requestFocus();
            return false;
        }
        return true;
    }

    public void inicializarServicoLocalizacao() {
        buildGoogleApiClient();
    }

    public void startarServicoLocalizacao() {
        mGoogleApiClient.connect();
    }

    public void restartarServicoLocalizacao() {
        if (mGoogleApiClient.isConnected()) {
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
        startLocationUpdates();
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
