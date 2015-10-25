package br.com.gsn.sysbusapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.activity.NovaReclamacaoActivity;
import br.com.gsn.sysbusapp.business.FavoritosBusiness;
import br.com.gsn.sysbusapp.business.SincronizarFavoritosBusiness;
import br.com.gsn.sysbusapp.model.LinhaFavoritaDTO;
import br.com.gsn.sysbusapp.util.PreferencesUtil;

/**
 * Created by Geison on 31/08/2015.
 */
public class FavoritosFragment extends ListContentFragment /*implements BusinessDelegate<BusinessTaskOperation>*/ {

    private LinhaFavoritaDTO linhaFavorita;
    private FavoritosBusiness favoritosBusiness;

    public FavoritosFragment() {}

    @Override
    public void setBusinessDelegate() {
        favoritosBusiness = new FavoritosBusiness(this);
        this.delegate = favoritosBusiness;
    }

    @Override
    public void onStart() {
        super.onStart();
        favoritosBusiness.sincronizarFavoritos(SincronizarFavoritosBusiness.Mode.SILENCIOSO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favoritos, container, false);

        ((FavoritosBusiness)delegate).listarLinhas(rootView);

        ListView listView = (ListView)rootView.findViewById(android.R.id.list);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                linhaFavorita = (LinhaFavoritaDTO) parent.getAdapter().getItem(position);
                return false;
            }
        });

        registerForContextMenu(listView);

        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.contextmenu_favoritos, menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favoritos, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        delegate.setMenu(menu);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sincronizar_favoritos) {
            favoritosBusiness.sincronizarFavoritos(SincronizarFavoritosBusiness.Mode.NORMAL);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_reclamacao) {
            PreferencesUtil.getInstance(getActivity()).setMenuCorrente(1);
            Bundle bundle = new Bundle();
            bundle.putLong("idLinha", linhaFavorita.getIdLinha());
            Intent intent = new Intent(this.getActivity(), NovaReclamacaoActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_excluir) {
            favoritosBusiness.excluirFavorito(linhaFavorita);
        }
        return super.onContextItemSelected(item);
    }

}
