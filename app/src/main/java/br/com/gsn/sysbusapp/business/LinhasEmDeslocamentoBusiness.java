package br.com.gsn.sysbusapp.business;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.adapter.LinhasEmDeslocamentoAdapter;
import br.com.gsn.sysbusapp.fragment.ListContentFragment;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.parcelable.LocalizacaoLinhaParcelable;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.PreferencesUtil;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 06/09/2015.
 */
public class LinhasEmDeslocamentoBusiness extends BusinessTaskOperation<Void, Integer, SpringRestResponse> {

    private ListContentFragment listFragment;
    private TemplateAsyncTask<Void, Integer, SpringRestResponse> task;
    public LocalizacaoLinhaDTO localizacaoLinhaDTO;
    private List<LocalizacaoLinhaDTO> linhas;

    public LinhasEmDeslocamentoBusiness(ListContentFragment context) {
        this.context = context.getActivity();
        this.listFragment = context;
    }

    public void listarLinhas() {
        task = new TemplateAsyncTask<>(this);
        task.execute();
    }

    @Override
    public void onPreExecute() {
        listFragment.setListAdapter(null);
        View view = listFragment.getView();
        TextView cabecalho = (TextView) view.findViewById(R.id.cabecalho);
        cabecalho.setVisibility(View.GONE);
        TextView emptyView = (TextView) view.findViewById(android.R.id.empty);
        emptyView.setText(null);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        listView.setEmptyView(emptyView);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(Void... params) {

        String intervalo = PreferencesUtil.getInstance(context).getString(PreferencesUtil.INTERVALO);

        String url = UrlServico.URL_VEICULOS_EM_DESLOCAMENTO;
        url = url.replace("{intervalo}", intervalo);

        return new SpringRestClient()
                .showMessage(false)
                .getForObject(context, url, LocalizacaoLinhaDTO[].class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
        final View view = listFragment.getView();
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final TextView emptyView = (TextView) view.findViewById(android.R.id.empty);
        final ListView listView = (ListView) view.findViewById(android.R.id.list);

        listView.setOnItemLongClickListener(itemLongClickListener);

        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {
                LocalizacaoLinhaDTO[] wrapper = (LocalizacaoLinhaDTO[]) response.getObjectReturn();
                linhas = Arrays.asList(wrapper);

                listFragment.setListAdapter(new LinhasEmDeslocamentoAdapter(context, linhas));

                TextView cabecalho = (TextView) view.findViewById(R.id.cabecalho);
                cabecalho.setVisibility(View.VISIBLE);
                cabecalho.setText("Mostrando todas as linhas");
            }
        });
        response.setOnHttpNotFound(new AbstractSpringRestResponse.OnHttpNotFound() {
            @Override
            public void doThis() {
                listFragment.setListAdapter(null);
                emptyView.setText(R.string.nenhuma_com_localizacao_informada);
                listView.setEmptyView(emptyView);
            }
        });

        if (response.getConnectionFailed()) {
            emptyView.setText(R.string.msg_falha_na_conexao);
            listView.setEmptyView(emptyView);
        } else if (response.getServerError()) {
            if (response.getStatusCode() == HttpURLConnection.HTTP_UNAVAILABLE) {
                emptyView.setText(R.string.msg_servidor_indisponivel);
            } else {
                emptyView.setText(R.string.msg_erro_no_servidor);
            }
            listView.setEmptyView(emptyView);
        }

        response.executeCallbacks();

        progressBar.setVisibility(View.GONE);

    }

    public ArrayList<LocalizacaoLinhaParcelable> listLocalizacaoLinhas() {
        ArrayList<LocalizacaoLinhaParcelable> list = new ArrayList<>();
        if (linhas != null) {
            for (LocalizacaoLinhaDTO l : linhas) {
                LocalizacaoLinhaParcelable linhaParcelable = new LocalizacaoLinhaParcelable(l);
                list.add(linhaParcelable);
            }
        }
        return list;
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }

    private AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            LocalizacaoLinhaDTO item = (LocalizacaoLinhaDTO) parent.getAdapter().getItem(position);
            localizacaoLinhaDTO = item;
            return false;
        }
    };
}
