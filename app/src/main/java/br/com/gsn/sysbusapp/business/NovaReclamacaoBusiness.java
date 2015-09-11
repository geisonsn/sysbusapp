package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.os.AsyncTask;

import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.ReclamacaoRankingDTO;
import br.com.gsn.sysbusapp.model.ReclamacaoRequestDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 06/09/2015.
 */
public class NovaReclamacaoBusiness extends BusinessTaskOperation<String, Integer, SpringRestResponse> {

    private LinhaBusiness linhaBusiness;
    private OrigemReclamacaoBusiness origemReclamacaoBusiness;
    private TemplateAsyncTask<ReclamacaoRequestDTO, Integer, SpringRestResponse> task;

    public NovaReclamacaoBusiness(Activity context) {
        this.context = context;
    }

    public void saveReclamacao() {
        task = new TemplateAsyncTask<>(this);
        task.execute();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(String... params) {
        String url = UrlServico.URL_LISTAGEM_RECLAMACAO_RANKING;
        return new SpringRestClient()
                .showMessage(false)
                .getForObject(context, url, ReclamacaoRankingDTO[].class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
//        final ProgressBar progressBar = (ProgressBar) context.findViewById(R.id.progressBar);

        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {
                String resposta = (String) response.getObjectReturn();
            }
        });

        response.executeCallbacks();
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
            //Cancela outras tasks que estejam executando
            linhaBusiness.cancelTaskOperation();
            origemReclamacaoBusiness.cancelTaskOperation();
        }
    }

    public void initForm() {
        linhaBusiness = new LinhaBusiness(context);
        listarLinhas();
    }

    public void listarLinhas() {
        linhaBusiness.listarLinhas();
    }

    public void listarOrigemReclamacao(String reclamado) {
        if (origemReclamacaoBusiness == null) {
            origemReclamacaoBusiness = new OrigemReclamacaoBusiness(context);
        }
        origemReclamacaoBusiness.listarOrigemReclamacao(reclamado);
    }

    /*@Override
    public void closeDialog(OnCloseDialog onCloseDialog) {
        onCloseDialog = new OnCloseDialog() {
            @Override
            public void closeDialog() {

            }
        };
    }*/
}
