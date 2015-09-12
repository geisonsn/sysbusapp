package br.com.gsn.sysbusapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessDelegate;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.business.NovaReclamacaoBusiness;
import br.com.gsn.sysbusapp.enums.ObjetoReclamadoEnum;
import br.com.gsn.sysbusapp.util.DatePickerUtil;
import br.com.gsn.sysbusapp.util.Dates;
import br.com.gsn.sysbusapp.util.TimePickerUtil;

/**
 * Created by Geison on 09/09/2015.
 */
public class NovaReclamacaoActivity extends AppCompatActivity implements BusinessDelegate<BusinessTaskOperation> {

    private NovaReclamacaoBusiness delegate;
    private ProgressBar progressBar;
    private Spinner reclamado, reclamacao, linha;
    private EditText dataOcorrencia, horaOcorrencia;
    private String[] emptySource = new String[] {"Selecione"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_reclamacao);

        reclamado = (Spinner) findViewById(R.id.objetoReclamado);
        reclamado.setOnItemSelectedListener(onItemSelectedListener);

        reclamacao = (Spinner) findViewById(R.id.reclamacao);
        reclamacao.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, emptySource));

        linha = (Spinner) findViewById(R.id.reclamacao);
        linha.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, emptySource));

        dataOcorrencia = (EditText) findViewById(R.id.dataOcorrencia);
        dataOcorrencia.setHint(Dates.getCurrentDate());
        dataOcorrencia.setOnClickListener(onClickListener);

        horaOcorrencia = (EditText) findViewById(R.id.horaOcorrencia);
        horaOcorrencia.setOnClickListener(onClickListener);

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
    public void onBackPressed() {
        super.onBackPressed();
        delegate.cancelTaskOperation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nova_reclamacao, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        delegate.setMenuItemProgressBar(menu.findItem(R.id.action_progresso));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            delegate.cancelTaskOperation();
//            NavUtils.navigateUpFromSameTask(this);
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
        }

        if (item.getItemId() == R.id.action_save_reclamacao) {
//            NavUtils.navigateUpFromSameTask(this);
            this.save();
        }

        if (item.getItemId() == R.id.action_recarregar_dados) {
            Toast.makeText(this, "Será implementado em breve", Toast.LENGTH_SHORT).show();
            //TODO implementar recarremento de dados
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public BusinessTaskOperation getBusinessDelegate() {
        return delegate;
    }

    @Override
    public void setBusinessDelegate() {
        this.delegate = new NovaReclamacaoBusiness(this);
    }

    public void showCalendar() {
        new DatePickerUtil(this, R.id.dataOcorrencia).show();
    }

    public void showTime() {
        new TimePickerUtil(this, R.id.horaOcorrencia).show();
    }

    private void save() {
        delegate.saveReclamacao();
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                String[] source = getResources().getStringArray(R.array.objeto_reclamado_valores);
                String reclamado = source[position];
                ObjetoReclamadoEnum objetoReclamadoEnum = ObjetoReclamadoEnum.getFromDescricao(reclamado);

                handleContainerMotivoReclamacao(objetoReclamadoEnum);

                if (!ObjetoReclamadoEnum.OUTROS.equals(objetoReclamadoEnum)) {
                    delegate.listarOrigemReclamacao(objetoReclamadoEnum.name());
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    public void handleContainerMotivoReclamacao(ObjetoReclamadoEnum reclamado) {
        View containerMotivoReclamacao = findViewById(R.id.container_motivo_reclamacao);
        View dividerMotivoReclamacao = findViewById(R.id.divider_motivo_reclamacao);
        if (ObjetoReclamadoEnum.OUTROS.equals(reclamado)) {
            containerMotivoReclamacao.setVisibility(View.GONE);
            dividerMotivoReclamacao.setVisibility(View.GONE);
        } else {
            containerMotivoReclamacao.setVisibility(View.VISIBLE);
            dividerMotivoReclamacao.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.dataOcorrencia) {
                showCalendar();
            } else if (v.getId() == R.id.horaOcorrencia){
                showTime();
            }
        }
    };

}
