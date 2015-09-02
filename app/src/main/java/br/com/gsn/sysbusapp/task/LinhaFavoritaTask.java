package br.com.gsn.sysbusapp.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

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
        return SpringRestClient.getForObject(context, url, LinhaFavoritaDTO[].class);
    }

    @Override
    protected void onPostExecute(final SprintRestResponse response) {
        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {
                LinhaFavoritaDTO[] linhas = (LinhaFavoritaDTO[]) response.getObjectReturn();
                List<LinhaFavoritaDTO> linhasFavoritas = Arrays.asList(linhas);
                ListView listView = (ListView) context.findViewById(android.R.id.list);
                if (linhasFavoritas.isEmpty()) {
                    listView.setEmptyView(context.findViewById(android.R.id.empty));
                } else {
                    listFragment.setListAdapter(new LinhaFavoritaAdapter(context, linhasFavoritas));
                    ProgressBar progressBar = (ProgressBar) context.findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
        response.executeCallbacks();
    }
}
