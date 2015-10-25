package br.com.gsn.sysbusapp.business;

import android.support.v4.app.ListFragment;
import android.view.MenuItem;
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
import br.com.gsn.sysbusapp.model.SincronizarFavoritoDTO;
import br.com.gsn.sysbusapp.persistence.LinhaFavoritaDao;
import br.com.gsn.sysbusapp.util.PreferencesUtil;

/**
 * Created by Geison on 06/09/2015.
 */
public class FavoritosBusiness extends BusinessTaskOperation<Void, Void, Void> {

    private View favoritosView;
    private ListFragment listFragment;
    private LinhaFavoritaDao linhaFavoritaDao;
    private List<LinhaFavoritaDTO> linhasFavoritas = new ArrayList<>();
    private SincronizarFavoritosBusiness sincronizarFavoritosBusiness;

    public FavoritosBusiness(ListContentFragment context) {
        this.context = context.getActivity();
        this.listFragment = context;
        this.linhaFavoritaDao = new LinhaFavoritaDao(context.getActivity());
    }

    public void listarLinhas(View view) {
        this.favoritosView = view;

        List<LinhaFavorita> list = linhaFavoritaDao.list(PreferencesUtil.getInstance(context).getIdUsuario());

        this.linhasFavoritas = new ArrayList<>();

        for (LinhaFavorita l : list) {
            LinhaFavoritaDTO lo = new LinhaFavoritaDTO();
            lo.setIdLinha(l.getIdLinha().longValue());
            lo.setNumeroLinha(l.getNumeroLinha());
            lo.setDescricaoLinha(l.getDescricaoLinha());
            lo.setEmpresaLinha(l.getEmpresa());
            lo.setIdUsuario(l.getIdUsuario().longValue());
            this.linhasFavoritas.add(lo);
        }

        if (list.isEmpty()) {
            TextView emptyView = (TextView) view.findViewById(android.R.id.empty);
            ListView listView = (ListView) view.findViewById(android.R.id.list);
            emptyView.setText(R.string.nenhum_favorito_adicionado);
            listView.setEmptyView(emptyView);
        } else {
            listFragment.setListAdapter(new LinhaFavoritaAdapter(context, this.linhasFavoritas));
        }
    }

    public void sincronizarFavoritos(SincronizarFavoritosBusiness.Mode mode) {
        if (sincronizarFavoritosBusiness == null) {
            sincronizarFavoritosBusiness = new SincronizarFavoritosBusiness(this, context);
        }

        cancelTaskOperation();//Cancela o que estiver rodando em paralelo

        Long idUsuario = PreferencesUtil.getInstance(context).getIdUsuario();
        SincronizarFavoritoDTO sincronizarFavoritoDTO = new SincronizarFavoritoDTO();
        sincronizarFavoritoDTO.setIdUsuario(idUsuario);
        sincronizarFavoritoDTO.setLinhasFavoritas(this.linhasFavoritas);

        sincronizarFavoritosBusiness.sincronizarFavoritos(mode, sincronizarFavoritoDTO);
    }

    public void excluirFavorito(LinhaFavoritaDTO linhaFavorita) {
        Long idUsuario = PreferencesUtil.getInstance(context).getIdUsuario();
        linhaFavoritaDao.delete(idUsuario, linhaFavorita.getIdLinha());
        listarLinhas(favoritosView);

        this.sincronizarFavoritos(SincronizarFavoritosBusiness.Mode.SILENCIOSO);
    }

    public void showProgressRequest() {
        MenuItem sincronizar = this.menu.findItem(R.id.action_sincronizar_favoritos);
        sincronizar.setVisible(false);
        MenuItem progressBar = menu.findItem(R.id.action_progresso);
        progressBar.setVisible(true);
    }

    public void hideProgressRequest() {
        MenuItem sincronizar = this.menu.findItem(R.id.action_sincronizar_favoritos);
        sincronizar.setVisible(true);
        MenuItem progressBar = menu.findItem(R.id.action_progresso);
        progressBar.setVisible(false);
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
        if (sincronizarFavoritosBusiness != null) {
            sincronizarFavoritosBusiness.cancelTaskOperation();
        }
    }
}
