package br.com.gsn.sysbusapp.business;

import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.fragment.ListContentFragment;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.adapter.ReclamacaoRankingAdapter;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.ReclamacaoRankingDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 06/09/2015.
 */
public class ReclamacaoRankingBusiness extends BusinessTaskOperation<Void, Integer, SpringRestResponse> {

    private ListFragment listFragment;
    private TemplateAsyncTask<Void, Integer, SpringRestResponse> task;

    public ReclamacaoRankingBusiness(ListContentFragment context) {
        this.context = context.getActivity();
        this.listFragment = context;
    }

    public void listarReclamacoes() {
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
    public SpringRestResponse doInBackground(Void... params) {
        String url = UrlServico.URL_LISTAGEM_RECLAMACAO_RANKING;
        return new SpringRestClient()
                .showMessage(false)
                .getForObject(context, url, ReclamacaoRankingDTO[].class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
        final ProgressBar progressBar = (ProgressBar) context.findViewById(R.id.progressBar);
        final TextView emptyView = (TextView) context.findViewById(android.R.id.empty);
        final ListView listView = (ListView) context.findViewById(android.R.id.list);
        final LinearLayout header = (LinearLayout) context.findViewById(R.id.header);

        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {
                ReclamacaoRankingDTO[] linhas = (ReclamacaoRankingDTO[]) response.getObjectReturn();
                List<ReclamacaoRankingDTO> linhasFavoritas = Arrays.asList(linhas);
                listFragment.setListAdapter(new ReclamacaoRankingAdapter(context, linhasFavoritas));
                header.setVisibility(View.VISIBLE);
            }
        });

        response.setOnHttpNotFound(new AbstractSpringRestResponse.OnHttpNotFound() {
            @Override
            public void doThis() {
                emptyView.setText("Ranking não criado por falta de reclamações");
                listView.setEmptyView(emptyView);
            }
        });

        if (response.getConnectionFailed()) {
            emptyView.setText(R.string.msg_falha_na_conexao);
            listView.setEmptyView(emptyView);
            header.setVisibility(View.GONE);
        } else if (response.getServerError()) {
            emptyView.setText(R.string.msg_servidor_indisponivel);
            listView.setEmptyView(emptyView);
            header.setVisibility(View.GONE);
        }

        response.executeCallbacks();

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }
}
