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
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.LinhaDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.ConnectionUtil;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 10/09/2015.
 */
public class LinhaBusiness extends BusinessTaskOperation<Void, Integer, SpringRestResponse> {

    private Long idLinha;
    private TemplateAsyncTask<Void, Integer, SpringRestResponse> task;
    public List<LinhaDTO> linhas = new ArrayList<>();

    private NovaReclamacaoBusiness novaReclamacaoBusiness;

    public LinhaBusiness(Activity context, NovaReclamacaoBusiness novaReclamacaoBusiness) {
        this.context = context;
        this.novaReclamacaoBusiness = novaReclamacaoBusiness;
    }

    public void listarLinhas(Long idLinha) {
        this.idLinha = idLinha;

//        this.cancelTaskOperation();

        inicializarComboLinha(context);

        this.task = new TemplateAsyncTask<>(this);

        if (ConnectionUtil.isNetworkConnected(context)) {
            task.execute();
        } else {
            showNoConnectionMessage();
            toogleRefreshButton(true);
        }
    }

    public void inicializarComboLinha(Activity context) {
        LinhaDTO linhaDTO = new LinhaDTO();
        linhaDTO.setNumeroLinha("Selecione");
        this.linhas = new ArrayList<>();
        this.linhas.add(linhaDTO);
        Spinner spinnerLinhas = (Spinner)context.findViewById(R.id.linha);
        spinnerLinhas.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, linhas));
    }

    @Override
    public void onPreExecute() {
        toogleProgressBar();
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
                carregarCombo(response);
            }
        });

        if (response.getConnectionFailed()) {
            Toast.makeText(context, "Não foi possível carregar a lista de linhas devido a falha em sua conexão.", Toast.LENGTH_LONG).show();
        }

        response.executeCallbacks();

        toogleRefreshButton(response.getConnectionFailed());
        toogleProgressBar();
    }

    /**
     * Exibe ícone para recarregar combo em caso de falha na conexão
     * @param connectionFailed
     */
    public void toogleRefreshButton(boolean connectionFailed) {
        if (connectionFailed) {
            novaReclamacaoBusiness.getMenu().findItem(R.id.action_recarregar_dados).setVisible(true);
        } else {
            Spinner linha = (Spinner) context.findViewById(R.id.reclamacao);
            if (linha.getAdapter().getCount() > 1) {
                novaReclamacaoBusiness.getMenu().findItem(R.id.action_recarregar_dados).setVisible(false);
            }
        }
    }

    private void carregarCombo(SpringRestResponse response) {
        LinhaDTO[] source = (LinhaDTO[]) response.getObjectReturn();
        Spinner spinnerLinhas = (Spinner) context.findViewById(R.id.linha);

        linhas.addAll(Arrays.asList(source));

        spinnerLinhas.setAdapter(new ArrayAdapter<LinhaDTO>(context, android.R.layout.simple_spinner_item, linhas));

        if (idLinha != null) {
            Spinner linha = (Spinner) context.findViewById(R.id.linha);
            linha.setSelection(getPosition(linhas, idLinha));
        }
    }

    public int getPosition(List<LinhaDTO> source, Long idLinha) {
        int position = 1;
        for (LinhaDTO linha : source) {
            if (linha.getIdLinha() == idLinha) {
               return position;
            }
            position++;
        }
        return position;
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }

    public void toogleProgressBar() {
        ProgressBar progressBar = (ProgressBar) context
                .findViewById(R.id.progressBarLinha);

        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
