package br.com.gsn.sysbusapp.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.com.gsn.sysbusapp.R;

/**
 * Created by Geison on 19/09/2015.
 */
public class Configuracoes2Activity extends AppCompatPreferenceActivity /*PreferenceActivity*/ {

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_configuracoes2);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Configurações");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        addPreferencesFromResource(R.xml.configuracoes);
    }
}