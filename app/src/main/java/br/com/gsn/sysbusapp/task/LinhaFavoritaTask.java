package br.com.gsn.sysbusapp.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.adapter.LinhaFavoritaAdapter;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.LinhaFavoritaDTO;
import br.com.gsn.sysbusapp.model.SprintRestResponse;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by geison on 07/05/15.
 */
public class LinhaFavoritaTask extends AsyncTask<Void, Integer, SprintRestResponse> {

    private ListFragment listFragment;
    private Activity context;

    public LinhaFavoritaTask(ListFragment context) {
        this.context = context.getActivity();
        this.listFragment = context;
    }

    @Override
    protected SprintRestResponse doInBackground(Void... param) {
        String url = UrlServico.URL_LISTAGEM_LINHA_FAVORITA;
        url = url.replace("{idUsuario}", String.valueOf(1));

        return new SpringRestClient()
            .showMessage(false)
            .getForObject(context, url, LinhaFavoritaDTO[].class);
    }

    @Override
    protected void onPostExecute(final SprintRestResponse response) {
        final ProgressBar progressBar = (ProgressBar) context.findViewById(R.id.progressBar);
        final TextView emptyView = (TextView) context.findViewById(android.R.id.empty);
        final ListView listView = (ListView) context.findViewById(android.R.id.list);

        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {
                LinhaFavoritaDTO[] linhas = (LinhaFavoritaDTO[]) response.getObjectReturn();
                List<LinhaFavoritaDTO> linhasFavoritas = Arrays.asList(linhas);
                listFragment.setListAdapter(new LinhaFavoritaAdapter(context, linhasFavoritas));
            }
        });
        response.setOnHttpNotFound(new AbstractSpringRestResponse.OnHttpNotFound() {
            @Override
            public void doThis() {
                emptyView.setText(R.string.nenhum_favorito_adicionado);
                listView.setEmptyView(emptyView);
            }
        });

        if (response.getConnectionFailed()) {
            emptyView.setText(R.string.msg_falha_na_conexao);
            listView.setEmptyView(emptyView);
        } else if (response.getServerError()) {
            emptyView.setText(R.string.msg_servidor_indisponivel);
            listView.setEmptyView(emptyView);
        }

        response.executeCallbacks();

        progressBar.setVisibility(View.GONE);
    }
}
