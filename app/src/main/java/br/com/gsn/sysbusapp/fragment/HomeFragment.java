package br.com.gsn.sysbusapp.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.activity.MapsActivity;
import br.com.gsn.sysbusapp.activity.NovaReclamacaoActivity;
import br.com.gsn.sysbusapp.business.HomeBusiness;
import br.com.gsn.sysbusapp.dialog.CheckinDialog;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusapp.parcelable.LocalizacaoLinhaParcelable;
import br.com.gsn.sysbusapp.util.ConnectionUtil;

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

        if (item.getItemId() == R.id.action_checkin) {

            if (ConnectionUtil.isGPSConnected(getActivity())) {
                DialogFragment dialogLogin = new CheckinDialog();
                dialogLogin.show(getActivity().getSupportFragmentManager(), "checkin");
            } else {


                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Habilitar Location")
                        .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                                "use this app")
                        .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(myIntent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            }
                        });
                dialog.show();
            }

        }

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
            LocalizacaoLinhaDTO localizacaoLinha = ((HomeBusiness) delegate).getLocalizacaoLinha();
            Bundle b = new Bundle();
            b.putParcelable("localizacaoLinha", new LocalizacaoLinhaParcelable(localizacaoLinha));
            Intent intent = new Intent(this.getActivity(), MapsActivity.class);
            intent.putExtras(b);
            startActivity(intent);
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
