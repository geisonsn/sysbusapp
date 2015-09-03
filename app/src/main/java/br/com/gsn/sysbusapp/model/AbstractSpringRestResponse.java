package br.com.gsn.sysbusapp.model;

import android.content.Context;
import android.widget.Toast;

import java.net.HttpURLConnection;

import br.com.gsn.sysbusapp.R;

/**
 * Created by Geison on 26/08/2015.
 */
public abstract class AbstractSpringRestResponse {

    public static final int CONNECTION_FAILED = -666;
    public static final int UNEXPECTED_ERROR = -667;

    private Context context;
    private int statusCode;
    private Object objectReturn;
    private boolean connectionFailed = false;
    private boolean serverError = false;

    private OnHttpOk onHttpOk;
    private OnHttpNotFound onHttpNotFound;
    private OnHttpCreated onHttpCreated;
    private OnHttpConflict onHttpConflict;

    protected AbstractSpringRestResponse(Context context, Object objectReturn, int statusCode) {
        this.context = context;
        this.objectReturn = objectReturn;
        this.statusCode = statusCode;
    }

    protected AbstractSpringRestResponse(Context context, int statusCode) {
        this.context = context;
        this.statusCode = statusCode;

        if (StatusCodeFamily.getFamily(this.statusCode) == StatusCodeFamily.SERVER_ERROR) {
            this.serverError = true;
        } else if (StatusCodeFamily.getFamily(this.statusCode) == StatusCodeFamily.OTHER) {
            this.connectionFailed = true;
        }

    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getObjectReturn() {
        return objectReturn;
    }

    public boolean getConnectionFailed() {
        return this.connectionFailed;
    }

    public boolean getServerError() {
        return this.serverError;
    }

    protected void onHttpOk() {
        if (onHttpOk == null) {
            Toast.makeText(context, "Requisição realizada com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            onHttpOk.doThis();
        }
    }

    protected void onHttpCreated() {
        if (onHttpCreated == null) {
            Toast.makeText(context, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            onHttpCreated.doThis();
        }
    }

    protected void onHttpConflict() {
        if (onHttpConflict == null) {
            Toast.makeText(context, R.string.msg_registro_ja_cadastrado, Toast.LENGTH_SHORT).show();
        } else {
            onHttpConflict.doThis();
        }
    }

    protected void onHttpUnavailable() {
        Toast.makeText(context, R.string.msg_servidor_indisponivel, Toast.LENGTH_SHORT).show();
    }

    protected void onHttpNotFound() {
        if (onHttpNotFound == null) {
            Toast.makeText(context, "Não encontrado", Toast.LENGTH_SHORT).show();
        } else {
            onHttpNotFound.doThis();
        }
    }

    protected void onConnectionFailed() {
        Toast.makeText(context, "Falha na conexão! Verifique sua conexão e tente novamente.", Toast.LENGTH_LONG).show();
    }

    protected void onUnexpectedError() {
        Toast.makeText(context, "Ops! Ocorreu um erro inesperado.", Toast.LENGTH_SHORT).show();
    }

    public void setOnHttpOk(OnHttpOk onHttpOk) {
        this.onHttpOk = onHttpOk;
    }

    public void setOnHttpNotFound(OnHttpNotFound onHttpNotFound) {
        this.onHttpNotFound = onHttpNotFound;
    }

    public void setOnHttpCreated(OnHttpCreated onHttpCreated) {
        this.onHttpCreated = onHttpCreated;
    }

    public void setOnHttpConflict(OnHttpConflict onHttpConflict) {
        this.onHttpConflict = onHttpConflict;
    }

    public void executeCallbacks() {

        if (StatusCodeFamily.getFamily(this.statusCode) == StatusCodeFamily.SUCCESSFUL) {
            if (this.statusCode == HttpURLConnection.HTTP_OK) {
                onHttpOk();
            } else if (this.statusCode == HttpURLConnection.HTTP_CREATED) {
                onHttpCreated();
            }
        } else if (StatusCodeFamily.getFamily(this.statusCode) == StatusCodeFamily.CLIENT_ERROR) {
            if (this.statusCode == HttpURLConnection.HTTP_CONFLICT) {
                onHttpConflict();
            } else if (this.statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
                onHttpNotFound();
            }
        } else if (StatusCodeFamily.getFamily(this.statusCode) == StatusCodeFamily.SERVER_ERROR) {
            if (this.statusCode == HttpURLConnection.HTTP_UNAVAILABLE) {
                onHttpUnavailable();
            }
        } else if (StatusCodeFamily.getFamily(this.statusCode) == StatusCodeFamily.OTHER) {
            if (this.statusCode == AbstractSpringRestResponse.CONNECTION_FAILED) {
                onConnectionFailed();
            } else if (this.statusCode == AbstractSpringRestResponse.UNEXPECTED_ERROR) {
                onUnexpectedError();
            }
        }
    }

    public interface HttpStatusCodeCallback {
        void doThis();
    }

    public interface OnHttpOk extends HttpStatusCodeCallback {
    }

    public interface OnHttpNotFound extends HttpStatusCodeCallback {
    }

    public interface OnHttpCreated extends HttpStatusCodeCallback {
    }

    public interface OnHttpConflict extends HttpStatusCodeCallback {
    }

    public static final class StatusCodeFamily {
        /**
         * Informational 1xx
         */
        public static int INFORMATIONAL = 1;

        /**
         * Successful 2xx
         */
        public static int SUCCESSFUL = 2;

        /**
         * Redirection 3xx
         */
        public static int REDIRECTION = 3;

        /**
         * Client Error 4xx
         */
        public static int CLIENT_ERROR = 4;

        /**
         * Server Error 5xx
         */
        public static int SERVER_ERROR = 5;

        /**
         * Other errors
         */
        public static int OTHER = 6;

        public static int getFamily(int statusCode) {
            return Math.abs(statusCode / 100);
        }
    }
}
