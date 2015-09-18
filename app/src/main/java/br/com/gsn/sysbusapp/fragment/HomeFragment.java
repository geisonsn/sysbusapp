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
import android.widget.ListView;
import android.widget.Toast;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.activity.NovaReclamacaoActivity;
import br.com.gsn.sysbusapp.business.HomeBusiness;

public class HomeFragment extends ListContentFragment {

    public HomeFragment() {}

    @Override
    public void setBusinessDelegate() {
        this.delegate = new HomeBusiness(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView)rootView.findViewById(android.R.id.list);

        registerForContextMenu(listView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mostrarTodasLinhas();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);

        delegate.setMenuItemProgressBar(menu.findItem(R.id.action_progresso));

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_linhas_proximas_a_mim) {
            this.listarLinhasProximas();
        }

        if (item.getItemId() == R.id.action_todas_as_linhas) {
            this.mostrarTodasLinhas();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater  inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.contextmenu_linhas, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_reclamacao) {
            startActivity(new Intent(this.getActivity(), NovaReclamacaoActivity.class));
        }

        if (item.getItemId() == R.id.menu_mostrar_mapa) {
            Toast.makeText(this.getActivity(), "Será implementado em breve", Toast.LENGTH_SHORT).show();
        }

        return super.onContextItemSelected(item);
    }

    private void listarLinhasProximas() {
        ((HomeBusiness)delegate).listarLinhasProximas();
    }

    private void mostrarTodasLinhas() {
        ((HomeBusiness)delegate).listarLinhas();
    }
}
