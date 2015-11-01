package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.enums.ObjetoReclamadoEnum;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.OrigemReclamacaoDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.ConnectionUtil;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 10/09/2015.
 */
public class OrigemReclamacaoBusiness extends BusinessTaskOperation<String, Integer, SpringRestResponse> {

    private TemplateAsyncTask<String, Integer, SpringRestResponse> task;
    private List<OrigemReclamacaoDTO> lista = new ArrayList<>();
    private NovaReclamacaoBusiness novaReclamacaoBusiness;
    private ObjetoReclamadoEnum reclamado;

    public OrigemReclamacaoBusiness(Activity context, NovaReclamacaoBusiness novaReclamacaoBusiness) {
        this.context = context;
        this.novaReclamacaoBusiness = novaReclamacaoBusiness;
    }

    public void listarOrigemReclamacao(String reclamado) {
//        this.cancelTaskOperation();
        inicializarComboOrigemReclamacao(context);

        this.task = new TemplateAsyncTask<>(this);

        if (ConnectionUtil.isNetworkConnected(context)) {
            task.execute(reclamado);
        } else {
            showNoConnectionMessage();
            toogleRefreshButton(true);
        }
    }

    public void inicializarComboOrigemReclamacao(Activity context) {
        OrigemReclamacaoDTO origemReclamacao = new OrigemReclamacaoDTO();
        origemReclamacao.setDescricaoTipoReclamacao("Selecione");

        synchronized (this) {
            this.lista = new ArrayList<>();
            lista.add(origemReclamacao);
        }

        Spinner spinner = (Spinner) context.findViewById(R.id.reclamacao);
        spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, lista));
    }

    @Override
    public void onPreExecute() {
        this.toogleProgressBar();
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
                OrigemReclamacaoDTO[] source = (OrigemReclamacaoDTO[]) response.getObjectReturn();
                lista.addAll(Arrays.asList(source));
                Spinner spinner = (Spinner) context.findViewById(R.id.reclamacao);
                spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, lista));

                Spinner reclamacao = (Spinner) context.findViewById(R.id.reclamacao);
                reclamacao.getAdapter().getCount();
            }
        });

        if (response.getConnectionFailed()) {
            Toast.makeText(context, "Não foi possível carregar os tipos de reclamações devido a falha em sua conexão.", Toast.LENGTH_LONG).show();
        }

        response.executeCallbacks();

        toogleRefreshButton(response.getConnectionFailed());

        toogleProgressBar();
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }

    public void toogleProgressBar() {
        ProgressBar progressBarReclamacao = (ProgressBar) context
                .findViewById(R.id.progressBarReclamacao);

        if (progressBarReclamacao.getVisibility() == View.VISIBLE) {
            progressBarReclamacao.setVisibility(View.GONE);
        } else {
            progressBarReclamacao.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Exibe icone para recarregar combo em caso de falha de conexao
     * @param connectionFailed
     */
    private void toogleRefreshButton(boolean connectionFailed) {
        if (connectionFailed) {
            novaReclamacaoBusiness.getMenu().findItem(R.id.action_recarregar_dados).setVisible(true);
        } else {
            Spinner linha = (Spinner) context.findViewById(R.id.linha);
            if (linha.getAdapter().getCount() > 1) {
                novaReclamacaoBusiness.getMenu().findItem(R.id.action_recarregar_dados).setVisible(false);
            }
        }
    }
}
