package br.com.gsn.sysbusapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.abstraction.HostBusinessDelegate;
import br.com.gsn.sysbusapp.fragment.FavoritosFragment;
import br.com.gsn.sysbusapp.fragment.FragmentDrawer;
import br.com.gsn.sysbusapp.fragment.HomeFragment;
import br.com.gsn.sysbusapp.fragment.ReclamacaoFragment;
import br.com.gsn.sysbusapp.util.PreferencesUtil;


public class MainActivity extends AppCompatActivity implements HostBusinessDelegate, FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    /**
     * Identifica o fragmento apresentado na view
     */
    private BusinessTaskOperation mCurrentBusinessDelegate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

        //Carrega o nome e o email do usúario no menu
        carregarIdentificacaoUsuario();
    }

    private void carregarIdentificacaoUsuario() {
        ((TextView)findViewById(R.id.nome_usuario)).setText(PreferencesUtil.getInstance(this).getString(PreferencesUtil.NOME_USUARIO));
        ((TextView)findViewById(R.id.email_usuario)).setText(PreferencesUtil.getInstance(this).getString(PreferencesUtil.EMAIL_USUARIO));
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        //Opçao utilizada no fragmento de reclamaçao
        if (item.getItemId() == R.id.action_nova_reclamacao) {
            startActivity(new Intent(this, NovaReclamacaoActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
            displayView(position);
    }

    private void displayView(int position) {
        int menuCorrente = PreferencesUtil.getInstance(this).getMenuCorrente();
        if (menuCorrente >= 0) {
            loadMenu(menuCorrente);
            PreferencesUtil.getInstance(this).setMenuCorrente(-1);
        } else {
            loadMenu(position);
        }
    }

    private void loadMenu(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new FavoritosFragment();
                title = getString(R.string.title_favoritos);
                break;
            case 2:
                fragment = new ReclamacaoFragment();
                title = getString(R.string.title_reclamacoes);
                break;
            case 3:
                startActivity(new Intent(this, ConfiguracoesActivity.class));
                PreferencesUtil.getInstance(this).setMenuCorrente(position);
                title = getString(R.string.title_configuracoes);
                break;
            case 4:
                title = getString(R.string.title_desconectar);
                mCurrentBusinessDelegate.cancelTaskOperation();
                PreferencesUtil.getInstance(this).restaurarPreferencias();
                this.finish();
                startActivity(new Intent(this, InicialActivity.class));
                break;
            default:
                break;
        }

        if (fragment != null) {

            if (mCurrentBusinessDelegate != null) {
                mCurrentBusinessDelegate.cancelTaskOperation();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void seCurrentBusinessDelegate(BusinessTaskOperation mCurrentBusinessDelegate) {
        this.mCurrentBusinessDelegate = mCurrentBusinessDelegate;
    }

}
