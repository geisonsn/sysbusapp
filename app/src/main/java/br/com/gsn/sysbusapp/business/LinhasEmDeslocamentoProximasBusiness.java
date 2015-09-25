package br.com.gsn.sysbusapp.business;

import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.adapter.LinhasEmDeslocamentoAdapter;
import br.com.gsn.sysbusapp.fragment.ListContentFragment;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaWrapperDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.PreferencesUtil;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 06/09/2015.
 */
public class LinhasEmDeslocamentoProximasBusiness extends BusinessTaskOperation<Void, Integer, SpringRestResponse> {

    private ListFragment listFragment;
    private TemplateAsyncTask<Void, Integer, SpringRestResponse> task;
    public LocalizacaoLinhaDTO localizacaoLinhaDTO;

    public LinhasEmDeslocamentoProximasBusiness(ListContentFragment context) {
        this.context = context.getActivity();
        this.listFragment = context;
    }

    public void listarLinhas() {
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

        Long idUsuario = PreferencesUtil.getInstance(context).getLong(PreferencesUtil.ID_USUARIO);
        String intervalo = PreferencesUtil.getInstance(context).getString(PreferencesUtil.INTERVALO);
        String distancia = PreferencesUtil.getInstance(context).getString(PreferencesUtil.DISTANCIA);

        String url = UrlServico.URL_VEICULOS_EM_DESLOCAMENTO_PROXIMOS;
        url = url.replace("{idUsuario}", String.valueOf(idUsuario));
        url = url.replace("{intervalo}", intervalo);
        url = url.replace("{distancia}", distancia);

        //Ponto de referencia: Bola da Suframa (-3.130969, -59.987002)
        url = url.replace("{latitude}", "-3.130969");
        url = url.replace("{longitude}", "-59.987002");

        return new SpringRestClient()
                .showMessage(false)
                .getForObject(context, url, LocalizacaoLinhaWrapperDTO.class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
        final ProgressBar progressBar = (ProgressBar) context.findViewById(R.id.progressBar);
        final TextView emptyView = (TextView) context.findViewById(android.R.id.empty);
        final ListView listView = (ListView) context.findViewById(android.R.id.list);

        listView.setOnItemLongClickListener(itemLongClickListener);

        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {

                LocalizacaoLinhaWrapperDTO wrapper = (LocalizacaoLinhaWrapperDTO) response.getObjectReturn();

                List<LocalizacaoLinhaDTO> linhas = new ArrayList<>();
                if (!wrapper.getLinhasFavoritas().isEmpty()) {
                    linhas.addAll(wrapper.getLinhasFavoritas());
                }
                linhas.addAll(wrapper.getLinhasNaoFavoritas());

                listFragment.setListAdapter(new LinhasEmDeslocamentoAdapter(context, linhas));
            }
        });
        response.setOnHttpNotFound(new AbstractSpringRestResponse.OnHttpNotFound() {
            @Override
            public void doThis() {
                listFragment.setListAdapter(null);
                emptyView.setText(R.string.nenhuma_linha_proxima);
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
