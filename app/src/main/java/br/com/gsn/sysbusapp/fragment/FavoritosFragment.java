package br.com.gsn.sysbusapp.fragment;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.business.FavoritosBusiness;

/**
 * Created by Geison on 31/08/2015.
 */
public class FavoritosFragment extends ListContentFragment /*implements BusinessDelegate<BusinessTaskOperation>*/ {

    public FavoritosFragment() {}

    @Override
    public void setBusinessDelegate() {
        this.delegate = new FavoritosBusiness(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ((FavoritosBusiness)delegate).listarLinhas();

        ListView listView = (ListView)rootView.findViewById(android.R.id.list);

        registerForContextMenu(listView);

        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.contextmenu_linhas, menu);
    }

}
