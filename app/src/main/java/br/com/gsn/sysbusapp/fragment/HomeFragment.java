package br.com.gsn.sysbusapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import br.com.gsn.sysbusapp.activity.MapsActivity;
import br.com.gsn.sysbusapp.activity.NovaReclamacaoActivity;
import br.com.gsn.sysbusapp.business.HomeBusiness;
import br.com.gsn.sysbusapp.dialog.CheckinDialog;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusapp.parcelable.LocalizacaoLinhaParcelable;
import br.com.gsn.sysbusapp.util.ConnectionUtil;

public class HomeFragment extends ListContentFragment {

    private HomeBusiness homeBusiness;
    public HomeFragment() {}

    @Override
    public void setBusinessDelegate() {
        this.delegate = new HomeBusiness(this);
        this.homeBusiness = (HomeBusiness) delegate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list_localizacao_linha, container, false);

        ListView listView = (ListView)rootView.findViewById(android.R.id.list);

        registerForContextMenu(listView);

        homeBusiness.mRequestingLocationUpdates = false;
        homeBusiness.inicializarServicoLocalizacao();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        homeBusiness.startarServicoLocalizacao();

        this.listarTodasLinhas();

        /*if (ConnectionUtil.isGPSConnected(getActivity()) &&
            PreferencesUtil.getInstance(getActivity()).getPesquisaLinha().equals(PreferencesUtil.MOSTRAR_LOCALIZACAO_PROXIMOS)) {
            homeBusiness.capturarLocalizacao();
            this.listarLinhasProximas();
        } else {
            this.listarTodasLinhas();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        homeBusiness.restartarServicoLocalizacao();
    }

    @Override
    public void onPause() {
        super.onPause();
        homeBusiness.pausarServicoLocalizacao();
    }

    @Override
    public void onStop() {
        homeBusiness.pausarServicoLocalizacao();
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);

        delegate.setMenuItemProgressBar(menu.findItem(R.id.action_progresso));

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_checkin) {

            if (ConnectionUtil.isGPSConnected(getActivity())) {
                DialogFragment dialogLogin = new CheckinDialog();
                dialogLogin.show(getActivity().getSupportFragmentManager(), "checkin");
            } else {
                ConnectionUtil.showMessageLocationDisabled(getActivity());
            }
        }

        if (item.getItemId() == R.id.action_linhas_proximas_a_mim) {
            if (ConnectionUtil.isNetworkConnected(getActivity())) {
                if (ConnectionUtil.isGPSConnected(getActivity())) {
//                    PreferencesUtil.getInstance(getActivity()).setPesquisaLinha(PreferencesUtil.MOSTRAR_LOCALIZACAO_PROXIMOS);
                    homeBusiness.capturarLocalizacao();
                    this.listarLinhasProximas();
                } else {
                    ConnectionUtil.showMessageLocationDisabled(getActivity());
                }
            } else {
                Toast.makeText(getActivity(), R.string.voce_nao_esta_conectado, Toast.LENGTH_SHORT).show();
            }
        }

        if (item.getItemId() == R.id.action_todas_as_linhas) {
//            PreferencesUtil.getInstance(getActivity()).setPesquisaLinha(PreferencesUtil.MOSTRAR_LOCALIZACAO_TODOS);
            if (ConnectionUtil.isNetworkConnected(getActivity())) {
                this.listarTodasLinhas();
            } else {
                Toast.makeText(getActivity(), R.string.voce_nao_esta_conectado, Toast.LENGTH_SHORT).show();
            }
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
            LocalizacaoLinhaDTO localizacaoLinha = homeBusiness.getLocalizacaoLinha();
            Bundle b = new Bundle();

            b.putParcelable("linhaSelecionada", new LocalizacaoLinhaParcelable(localizacaoLinha));
            b.putParcelable("localizacaoUsuario", homeBusiness.currentLocation);
            b.putParcelableArrayList("outrasLinhas", homeBusiness.listLocalizacaoLinhas());
            Intent intent = new Intent(this.getActivity(), MapsActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }

        return super.onContextItemSelected(item);
    }

    private void listarLinhasProximas() {
        homeBusiness.listarLinhasProximas();
    }

    private void listarTodasLinhas() {
        homeBusiness.listarTodasLinhas();
    }

}
