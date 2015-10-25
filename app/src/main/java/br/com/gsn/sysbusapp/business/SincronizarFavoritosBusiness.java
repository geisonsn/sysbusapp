package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.SincronizarFavoritoDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.ConnectionUtil;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 06/09/2015.
 */
public class SincronizarFavoritosBusiness extends BusinessTaskOperation<SincronizarFavoritoDTO, Integer, SpringRestResponse> {

    private Mode mode;
    private FavoritosBusiness favoritosBusiness;
    private TemplateAsyncTask<SincronizarFavoritoDTO, Integer, SpringRestResponse> task;

    public SincronizarFavoritosBusiness(FavoritosBusiness favoritosBusiness, Activity context) {
        this.favoritosBusiness = favoritosBusiness;
        this.context = context;
    }

    public void sincronizarFavoritos(Mode mode, SincronizarFavoritoDTO usuarioWrapper) {
        this.mode = mode;
        if (Mode.NORMAL.equals(mode)) {
            if (ConnectionUtil.isNetworkConnected(context)) {
                task = new TemplateAsyncTask<>(this);
                task.execute(usuarioWrapper);
            } else {
                super.showNoConnectionMessage();
            }
        } else if (Mode.SILENCIOSO.equals(mode)) {
            task = new TemplateAsyncTask<>(this);
            task.execute(usuarioWrapper);
        }
    }

    @Override
    public void onPreExecute() {
        if (Mode.NORMAL.equals(mode)) {
            favoritosBusiness.showProgressRequest();
        }
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(SincronizarFavoritoDTO... params) {
        return new SpringRestClient()
                .showMessage(false)
                .post(context, UrlServico.URL_SINCRONIZAR_FAVORITOS, params[0], SincronizarFavoritoDTO.class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
        if (Mode.NORMAL.equals(mode)) {
            response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
                @Override
                public void doThis() {
                    Toast.makeText(context, "Favoritos sincronizados", Toast.LENGTH_SHORT).show();
                }
            });

            if (response.getConnectionFailed()) {
                Toast.makeText(context, context.getString(R.string.sem_conexao_com_internet), Toast.LENGTH_SHORT).show();
            }
            response.executeCallbacks();

            favoritosBusiness.hideProgressRequest();
        }
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }

    public enum Mode {
        NORMAL, SILENCIOSO;
    }
}
