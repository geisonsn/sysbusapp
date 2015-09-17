package br.com.gsn.sysbusapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import br.com.gsn.sysbusapp.R;
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

    private void listarLinhasProximas() {
        ((HomeBusiness)delegate).listarLinhasProximas();
    }

    private void mostrarTodasLinhas() {
        ((HomeBusiness)delegate).listarLinhas();
    }
}
