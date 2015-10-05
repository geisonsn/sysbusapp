package br.com.gsn.sysbusapp.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import java.io.IOException;

/**
 * Created by p001234 on 05/05/15.
 */
public final class ConnectionUtil {

    private ConnectionUtil() {}

    /**
     * Verifica se foi estabelecidade conexão em uma rede e se a internet está ao alcance
     * @param context
     * @return true se conectado a internet
     */
    public static boolean isOnline(Context context) {
        if (!isNetworkConnected(context)) {
            return false;
        }
        return isInternetConnected();
    }

    /**
     * Verifica se foi realizada conexão em alguma rede de conexão com a internet
     * @param context
     * @return true se conectado a alguma rede
     */
    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * Implementação localizada na Thread do
     * Stackoverflow: http://stackoverflow.com/a/27312494/2788975*
     * @return
     */
    private static boolean isInternetConnected() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8"); //Google DNS
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isGPSConnected(Context context) {
        LocationManager location = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return location.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void showMessageLocationDisabled(final Context context) {

        StringBuffer message = new StringBuffer()
            .append("Para utilizadar este recurso é necessário habilitar o serviço de localização.");

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Habilitar localização")
            .setMessage(message.toString())
            .setPositiveButton("Habilitar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        });
        dialog.show();
    }

}
