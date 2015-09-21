package br.com.gsn.sysbusapp.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import br.com.gsn.sysbusapp.R;

/**
 * Created by Geison on 31/08/2015.
 */
public class Configuracoes2Fragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.configuracoes);
    }
}
