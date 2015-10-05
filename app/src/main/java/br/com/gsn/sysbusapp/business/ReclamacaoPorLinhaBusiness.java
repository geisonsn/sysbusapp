package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.content.DialogInterface;
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
import br.com.gsn.sysbusapp.abstraction.BusinessDialogTaskOperation;
import br.com.gsn.sysbusapp.adapter.ReclamacaoPorLinhaAdapter;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.ReclamacaoPorLinhaDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 06/09/2015.
 */
public class ReclamacaoPorLinhaBusiness extends BusinessDialogTaskOperation<Long, Integer, SpringRestResponse> {

    private Activity context;
    private ListFragment listFragment;
    private TemplateAsyncTask<Long, Integer, SpringRestResponse> task;
    private View view;

    public ReclamacaoPorLinhaBusiness(Activity context) {
        this.context = context;
    }

    public void listarReclamacoes(Long idLinha, View view) {
        this.view = view;
        task = new TemplateAsyncTask<>(this);
        task.execute(idLinha);
    }

    @Override
    public void onPreExecute() {
        /*listFragment.setListAdapter(null);
        LinearLayout header = (LinearLayout) view.findViewById(R.id.header);
        header.setVisibility(View.GONE);

        ProgressBar progressBar = (ProgressBar) context.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        TextView emptyView = (TextView) context.findViewById(android.R.id.empty);
        emptyView.setText(null);
        ListView listView = (ListView) context.findViewById(android.R.id.list);
        listView.setEmptyView(emptyView);*/
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(Long... params) {
        String url = UrlServico.URL_RECLAMACOES_POR_LINHA;
        url = url.replace("{idLinha}", String.valueOf(params[0]));
        url = url.replace("{quantidade}", "5");

        return new SpringRestClient()
                .showMessage(false)
                .getForObject(context, url, ReclamacaoPorLinhaDTO[].class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final TextView emptyView = (TextView) view.findViewById(android.R.id.empty);
        final ListView listView = (ListView) view.findViewById(android.R.id.list);
        final LinearLayout header = (LinearLayout) view.findViewById(R.id.header);

        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {
                ReclamacaoPorLinhaDTO[] linhas = (ReclamacaoPorLinhaDTO[]) response.getObjectReturn();
                List<ReclamacaoPorLinhaDTO> linhasFavoritas = Arrays.asList(linhas);
                listView.setAdapter(new ReclamacaoPorLinhaAdapter(context, linhasFavoritas));
//                listFragment.setListAdapter(new ReclamacaoPorLinhaAdapter(context, linhasFavoritas));
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

    @Override
    public void onConfirmButton(DialogInterface dialog) {

    }
}
