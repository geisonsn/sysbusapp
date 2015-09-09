package br.com.gsn.sysbusapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

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

    /**
     * Este método contém erros relacionados ao uso de  ConnectivityManager.getAllNetworkInfo(). Este
     * está depreciado conforme a documentação
     * http://developer.android.com/reference/android/net/ConnectivityManager.html#getAllNetworkInfo()
     * @param context
     * @return
     */
    @Deprecated
    public static boolean verifyConnectionHaviTamada(Context context){

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {

                for (int i = 0; i < info.length; i++) {
                    NetworkInfo networkInfo = info[i];
                    boolean connected = networkInfo.isConnected();
                    boolean connectedOrConnecting = networkInfo.isConnectedOrConnecting();

                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    @Deprecated
    public static void verifyConnectionStackoverflow(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo Info = cm.getActiveNetworkInfo();
        if (Info == null || !Info.isConnectedOrConnecting()) {
            Log.i("CONNECT", "Nao conectado");
        } else {
            int netType = Info.getType();
            int netSubtype = Info.getSubtype();

            if (netType == ConnectivityManager.TYPE_WIFI) {

                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                int linkSpeed = wifiManager.getConnectionInfo().getLinkSpeed();
                //Need to get wifi strength
                Log.i("CONNECT", "sinal de wifi " + linkSpeed);
            } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                Log.i("CONNECT", "tipo de conexao 3G");

                //Need to get differentiate between 3G/GPRS
            }
        }
    }
}
