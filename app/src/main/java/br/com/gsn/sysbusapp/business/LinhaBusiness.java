package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.LinhaDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 10/09/2015.
 */
public class LinhaBusiness extends BusinessTaskOperation<Void, Integer, SpringRestResponse> {

    private TemplateAsyncTask<Void, Integer, SpringRestResponse> task;

    public LinhaBusiness(Activity context) {
        this.context = context;
    }

    public void listarLinhas() {
        showProgressBar();
        this.task = new TemplateAsyncTask<>(this);
        task.execute();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(Void... params) {
        return new SpringRestClient()
                .showMessage(false)
                .getForObject(context, UrlServico.URL_LISTAGEM_LINHA, LinhaDTO[].class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {
                LinhaDTO[] linhas = (LinhaDTO[]) response.getObjectReturn();
                Spinner spinnerLinhas = (Spinner) context.findViewById(R.id.linha);
                spinnerLinhas.setAdapter(new ArrayAdapter<LinhaDTO>(context, android.R.layout.simple_spinner_item, linhas));
            }
        });
        response.executeCallbacks();
        showProgressBar();
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }

    public void showProgressBar() {
        ProgressBar progressBar = (ProgressBar) context
                .findViewById(R.id.progressBarLinha);

        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
