package br.com.gsn.sysbusapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessDelegate;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.business.NovaReclamacaoBusiness;
import br.com.gsn.sysbusapp.dialog.DatePickerFragment;
import br.com.gsn.sysbusapp.enums.ObjetoReclamadoEnum;

/**
 * Created by Geison on 09/09/2015.
 */
public class NovaReclamacaoActivity extends AppCompatActivity implements BusinessDelegate<BusinessTaskOperation> {

    private NovaReclamacaoBusiness delegate;
    private MenuItem menuItemProgressBar;
    private ProgressBar progressBar;
    private Spinner reclamado;
    private EditText dataOcorrencia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_reclamacao);

        reclamado = (Spinner) findViewById(R.id.objetoReclamado);
        reclamado.setOnItemSelectedListener(onItemSelectedListener);

        dataOcorrencia = (EditText) findViewById(R.id.dataOcorrencia);
        dataOcorrencia.setOnTouchListener(touchListener);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Reclamar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Registrando o delegate
        setBusinessDelegate();

        delegate.initForm();
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
//            NavUtils.navigateUpFromSameTask(this);
            delegate.saveReclamacao();
        }

        if (item.getItemId() == R.id.action_recarregar) {
            DialogFragment calendar = new DatePickerFragment();
            calendar.show(getSupportFragmentManager(), "calendar");
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

    @Override
    public BusinessTaskOperation getBusinessDelegate() {
        return delegate;
    }

    @Override
    public void setBusinessDelegate() {
        this.delegate = new NovaReclamacaoBusiness(this);
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                String[] source = getResources().getStringArray(R.array.objeto_reclamado_valores);
                String reclamado = source[position];
                delegate.listarOrigemReclamacao(ObjetoReclamadoEnum.getFromDescricao(reclamado).name());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

    public void showCalendar(View view) {

    }
}
