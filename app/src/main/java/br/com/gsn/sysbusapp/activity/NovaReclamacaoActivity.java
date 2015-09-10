package br.com.gsn.sysbusapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import br.com.gsn.sysbusapp.R;

/**
 * Created by Geison on 09/09/2015.
 */
public class NovaReclamacaoActivity extends AppCompatActivity {

    private MenuItem menuItemProgressBar;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_reclamacao);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Reclamar");
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nova_reclamacao, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menuItemProgressBar = menu.findItem(R.id.action_progresso);
//        ProgressBar viewById = (ProgressBar) findViewById(item.getItemId());
//        progressBar = (ProgressBar) MenuItemCompat.getActionView(item);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
        }

        if (item.getItemId() == R.id.action_save_reclamacao) {
            NavUtils.navigateUpFromSameTask(this);
        }

        if (item.getItemId() == R.id.action_recarregar) {
            showProgressBar();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showProgressBar() {
        menuItemProgressBar.setVisible(true);
    }

    private void HideProgressBar() {
        menuItemProgressBar.setVisible(false);
    }
}
