package br.com.gsn.sysbusapp.business;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.adapter.LinhaFavoritaAdapter;
import br.com.gsn.sysbusapp.cache.LinhaFavorita;
import br.com.gsn.sysbusapp.fragment.ListContentFragment;
import br.com.gsn.sysbusapp.model.LinhaFavoritaDTO;
import br.com.gsn.sysbusapp.persistence.LinhaFavoritaDao;
import br.com.gsn.sysbusapp.util.PreferencesUtil;

/**
 * Created by Geison on 06/09/2015.
 */
public class FavoritosBusiness extends BusinessTaskOperation<Void, Void, Void> {

    private ListFragment listFragment;
    private LinhaFavoritaDao linhaFavoritaDao;

    public FavoritosBusiness(ListContentFragment context) {
        this.context = context.getActivity();
        this.listFragment = context;
        this.linhaFavoritaDao = new LinhaFavoritaDao(context.getActivity());
    }

    public void listarLinhas(View view) {
        List<LinhaFavorita> list = linhaFavoritaDao.list(PreferencesUtil.getInstance(context).getIdUsuario());
        List<LinhaFavoritaDTO> linhasFavoritas = new ArrayList<>();
        for (LinhaFavorita l : list) {
            LinhaFavoritaDTO lo = new LinhaFavoritaDTO();
            lo.setIdLinha(l.getIdLinha().longValue());
            lo.setNumeroLinha(l.getNumeroLinha());
            lo.setDescricaoLinha(l.getDescricaoLinha());
            lo.setEmpresaLinha(l.getEmpresa());
            linhasFavoritas.add(lo);
        }

        if (list.isEmpty()) {
            TextView emptyView = (TextView) view.findViewById(android.R.id.empty);
            ListView listView = (ListView) view.findViewById(android.R.id.list);
            emptyView.setText(R.string.nenhum_favorito_adicionado);
            listView.setEmptyView(emptyView);
        } else {
            listFragment.setListAdapter(new LinhaFavoritaAdapter(context, linhasFavoritas));
        }
    }

    @Override
    public void onPreExecute() {
    }

    @Override
    public void onProgressUpdate(Void... values) {

    }

    @Override
    public Void doInBackground(Void... params) {
        return null;
    }

    @Override
    public void onPostExecute(final Void response) {
    }

    @Override
    public void cancelTaskOperation() {
    }
}
