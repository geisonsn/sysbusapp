package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.OrigemReclamacaoDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 10/09/2015.
 */
public class OrigemReclamacaoBusiness extends BusinessTaskOperation<String, Integer, SpringRestResponse> {

    private TemplateAsyncTask<String, Integer, SpringRestResponse> task;

    public OrigemReclamacaoBusiness(Activity context) {
        this.context = context;
    }

    public void listarOrigemReclamacao(String reclamado) {
        this.task = new TemplateAsyncTask<>(this);
        task.execute(reclamado);
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(String... params) {

        String url = UrlServico.URL_LISTAGEM_ORIGEM_RECLAMACAO;
        url = url.replace("{objetoReclamado}", params[0]);

        return new SpringRestClient()
                .showMessage(false)
                .getForObject(context, url, OrigemReclamacaoDTO[].class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {
                OrigemReclamacaoDTO[] origemReclamacao = (OrigemReclamacaoDTO[]) response.getObjectReturn();
                Spinner spinner = (Spinner) context.findViewById(R.id.reclamacao);
                spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, origemReclamacao));
            }
        });
        response.executeCallbacks();
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }
}
