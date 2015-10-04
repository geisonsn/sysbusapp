package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.model.VeiculoDTO;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.ConnectionUtil;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 10/09/2015.
 */
public class VeiculoBusiness extends BusinessTaskOperation<String, Integer, SpringRestResponse> {

    private final View dialog;
    private TemplateAsyncTask<String, Integer, SpringRestResponse> task;

    public VeiculoBusiness(Activity context, View dialogView) {
        this.context = context;
        this.dialog = dialogView;
    }

    public void listarVeiculos(String linha, View dialog) {
        if (ConnectionUtil.isNetworkConnected(context)) {
            ProgressBar progressBarLinha = (ProgressBar) dialog.findViewById(R.id.progressBarLinha);
            progressBarLinha.setVisibility(View.VISIBLE);
            this.task = new TemplateAsyncTask<>(this);
            task.execute(linha);
        } else {
            Toast.makeText(context, R.string.sem_conexao_com_internet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(String... params) {
        String url = UrlServico.URL_LISTAGEM_VEICULOS_POR_LINHA;
        url = url.replace("{numeroLinha}", params[0]);

        return new SpringRestClient()
                .showMessage(false)
                .getForObject(context, url, VeiculoDTO[].class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {

        ProgressBar progressBarLinha = (ProgressBar) dialog.findViewById(R.id.progressBarLinha);
        progressBarLinha.setVisibility(View.GONE);

        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {
                VeiculoDTO[] veiculos = (VeiculoDTO[]) response.getObjectReturn();
                AutoCompleteTextView spinner = (AutoCompleteTextView) dialog.findViewById(R.id.linha);
                spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, veiculos));
                spinner.showDropDown();
            }
        });
        response.setOnHttpNotFound(new AbstractSpringRestResponse.OnHttpNotFound() {
                                       @Override
                                       public void doThis() {
                                           Toast.makeText(context, R.string.linha_nao_cadastrada, Toast.LENGTH_SHORT).show();
                                       }
                                   }
        );
        response.executeCallbacks();
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }
}
