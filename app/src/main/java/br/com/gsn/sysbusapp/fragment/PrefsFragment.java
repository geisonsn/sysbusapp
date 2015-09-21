package br.com.gsn.sysbusapp.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.gsn.sysbusapp.R;

public class PrefsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        getActivity().setTheme(R.style.SettingsTheme);
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.configuracoes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_configuracoes, container, false);

//        ((FavoritosBusiness)delegate).listarLinhas();

        ListView listView = (ListView)rootView.findViewById(android.R.id.list);
        addPreferencesFromResource(R.xml.configuracoes);
//        registerForContextMenu(listView);

        return rootView;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }
}