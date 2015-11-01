package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.enums.ObjetoReclamadoEnum;
import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.LinhaDTO;
import br.com.gsn.sysbusapp.model.OrigemReclamacaoDTO;
import br.com.gsn.sysbusapp.model.ReclamacaoRequestDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;
import br.com.gsn.sysbusapp.task.TemplateAsyncTask;
import br.com.gsn.sysbusapp.util.ConnectionUtil;
import br.com.gsn.sysbusapp.util.Dates;
import br.com.gsn.sysbusapp.util.RegexValidatorUtil;
import br.com.gsn.sysbusapp.util.SpringRestClient;
import br.com.gsn.sysbusapp.util.UrlServico;

/**
 * Created by Geison on 06/09/2015.
 */
public class NovaReclamacaoBusiness extends BusinessTaskOperation<ReclamacaoRequestDTO, Integer, SpringRestResponse> {

    private LinhaBusiness linhaBusiness;
    private OrigemReclamacaoBusiness origemReclamacaoBusiness;
    private TemplateAsyncTask<ReclamacaoRequestDTO, Integer, SpringRestResponse> task;
    private Long idLinha;
    private ObjetoReclamadoEnum objetoReclamadoEnum;

    public NovaReclamacaoBusiness(Activity context) {
        this.context = context;
    }

    private LinhaBusiness getLinhaBusiness() {
        if (linhaBusiness == null) {
            linhaBusiness = new LinhaBusiness(context, this);
        }
        return linhaBusiness;
    }

    private OrigemReclamacaoBusiness getOrigemReclamacaoBusiness() {
        if (origemReclamacaoBusiness == null) {
            origemReclamacaoBusiness = new OrigemReclamacaoBusiness(context, this);
        }
        return origemReclamacaoBusiness;
    }

    public void inicializarCombos() {
        getLinhaBusiness().inicializarComboLinha(context);
        getOrigemReclamacaoBusiness().inicializarComboOrigemReclamacao(context);
    }

    public void saveReclamacao() {

        if (ConnectionUtil.isNetworkConnected(context)) {
            if (isValidForm()) {
                if (ConnectionUtil.isNetworkConnected(context)) {
                    showMenuItemProgressBar();
                    handleShowActionButton();
                    task = new TemplateAsyncTask<>(this);
                    task.execute(this.buildDTO());
                } else {
                    Toast.makeText(context, R.string.sem_conexao_com_internet, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(context, R.string.voce_nao_esta_conectado, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleShowActionButton() {
        Menu menu = getMenu();
        MenuItem botaoSalvar = menu.findItem(R.id.action_save_reclamacao);
        MenuItem botaoRecarregar = menu.findItem(R.id.action_recarregar_dados);

        if (this.menuItemProgressBar.isVisible()) {
            botaoSalvar.setVisible(false);
            botaoRecarregar.setVisible(false);
        } else {
            botaoSalvar.setVisible(true);
//            botaoRecarregar.setVisible(true);
        }



        /*View botaoSave = context.findViewById(R.id.action_save_reclamacao);
        View botaoRecarregarDados = context.findViewById(R.id.action_recarregar_dados);
        if (this.menuItemProgressBar.isVisible()) {
            botaoSave.setVisibility(View.GONE);
            botaoRecarregarDados.setVisibility(View.GONE);
        } else {
            botaoSave.setVisibility(View.VISIBLE);
            botaoRecarregarDados.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(ReclamacaoRequestDTO... params) {
        return SpringRestClient.post(context, UrlServico.URL_NOVA_RECLAMACAO, params[0], ReclamacaoRequestDTO.class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {

        response.setOnHttpCreated(new AbstractSpringRestResponse.OnHttpCreated() {
            @Override
            public void doThis() {
            Toast.makeText(context, "Sua reclamação foi registrada com sucesso", Toast.LENGTH_SHORT).show();
            inicializarForm();
            }
        });

        if (response.getConnectionFailed()) {
            Toast.makeText(context, R.string.sem_conexao_com_internet, Toast.LENGTH_SHORT).show();
        }

        hideMenuItemProgressBar();
        handleShowActionButton();

        response.executeCallbacks();
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
        //Cancela outras tasks que estejam executando
        linhaBusiness.cancelTaskOperation();
        origemReclamacaoBusiness.cancelTaskOperation();
    }

    public void listarLinhas(Long idLinha) {
        this.idLinha = idLinha;
        getLinhaBusiness().listarLinhas(idLinha);
    }

    public void listarOrigemReclamacao(ObjetoReclamadoEnum objetoReclamadoEnum) {
        this.objetoReclamadoEnum = objetoReclamadoEnum;
        getOrigemReclamacaoBusiness().listarOrigemReclamacao(objetoReclamadoEnum.name());
    }

    public void recarregarCombos() {
        if (ConnectionUtil.isNetworkConnected(context)) {
            Spinner linha = (Spinner) context.findViewById(R.id.linha);
            Spinner reclamacao = (Spinner) context.findViewById(R.id.reclamacao);
            if (linha.getAdapter().getCount() < 2) {
                getLinhaBusiness().listarLinhas(idLinha);
            }

            Spinner reclamado = (Spinner) context.findViewById(R.id.objetoReclamado);
            if (reclamado.getSelectedItemPosition() != 0 && reclamado.getSelectedItemPosition() != 4) {
                getOrigemReclamacaoBusiness().listarOrigemReclamacao(objetoReclamadoEnum.name());
            }
            getMenu().findItem(R.id.action_recarregar_dados).setVisible(false);
        } else {
            showNoConnectionMessage();
        }
    }

    public void inicializarComboOrigemReclamacao() {
        getOrigemReclamacaoBusiness().inicializarComboOrigemReclamacao(context);
    }

    public void changeComboOrigemReclamacao(int position) {
        if (position > 0) {
            String[] source = context.getResources().getStringArray(R.array.objeto_reclamado_valores);
            String reclamado = source[position];
            ObjetoReclamadoEnum objetoReclamadoEnum = ObjetoReclamadoEnum.getFromDescricao(reclamado);

            toogleContainerMotivoReclamacao(objetoReclamadoEnum);

            if (!ObjetoReclamadoEnum.OUTROS.equals(objetoReclamadoEnum)) {
                this.listarOrigemReclamacao(objetoReclamadoEnum);
            }

            getLinhaBusiness().toogleRefreshButton(false);
        } else {
            getOrigemReclamacaoBusiness().inicializarComboOrigemReclamacao(context);
        }
    }

    /**
     * Exibe ou oculta o container motivo reclamação
     * @param reclamado
     */
    public void toogleContainerMotivoReclamacao(ObjetoReclamadoEnum reclamado) {
        View containerMotivoReclamacao = context.findViewById(R.id.container_motivo_reclamacao);
        View dividerMotivoReclamacao = context.findViewById(R.id.divider_motivo_reclamacao);
        if (ObjetoReclamadoEnum.OUTROS.equals(reclamado)) {
            containerMotivoReclamacao.setVisibility(View.GONE);
            dividerMotivoReclamacao.setVisibility(View.GONE);
        } else {
            containerMotivoReclamacao.setVisibility(View.VISIBLE);
            dividerMotivoReclamacao.setVisibility(View.VISIBLE);
        }
    }

    public boolean isValidForm() {

        boolean isValid = true;

        Spinner reclamado, reclamacao, linha;
        EditText placa, descricaoReclamacao;

        reclamado = (Spinner) context.findViewById(R.id.objetoReclamado);
        reclamacao = (Spinner) context.findViewById(R.id.reclamacao);
        linha = (Spinner) context.findViewById(R.id.linha);
        placa = (EditText) context.findViewById(R.id.placa);
        descricaoReclamacao = (EditText) context.findViewById(R.id.descricaoReclamacao);

        List<View> invalidFields = new ArrayList<>();

        int reclamadoSelecionado = reclamado.getSelectedItemPosition();

        if (reclamadoSelecionado == 0) {
            TextView viewReclamado = (TextView)reclamado.getSelectedView();
            viewReclamado.setError("Selecione o reclamado");
            invalidFields.add(viewReclamado);
            isValid = false;
        }

        if (reclamadoSelecionado != 4
                && reclamadoSelecionado != 0) { //Diferente da opçao SELECIONE e OUTROS

            int reclamacaoSelecionada = reclamacao.getSelectedItemPosition();
            if (reclamacaoSelecionada == 0) {
                synchronized (this) {
                    TextView viewReclamacao = (TextView)reclamacao.getSelectedView();
                    if (viewReclamacao != null) {
                        viewReclamacao.setError("Informe o motivo da reclamação");
                        invalidFields.add(viewReclamacao);
                        isValid = false;
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(placa.getText())) {
            if (!RegexValidatorUtil.isValidPlaca(placa.getText().toString())) {
                placa.setError("Informe uma placa válida");
                invalidFields.add(placa);
                isValid = false;
            }
        }

        if (!isValid) {
            invalidFields.get(0).requestFocus();
        }

        return isValid;
    }

    private void inicializarForm() {
        Spinner reclamado, reclamacao, linha;
        EditText placa, descricaoReclamacao, dataOcorrencia, horaOcorrencia;
        reclamado = (Spinner) context.findViewById(R.id.objetoReclamado);
        reclamacao = (Spinner) context.findViewById(R.id.reclamacao);
        linha = (Spinner) context.findViewById(R.id.linha);
        placa = (EditText) context.findViewById(R.id.placa);
        descricaoReclamacao = (EditText) context.findViewById(R.id.descricaoReclamacao);
        dataOcorrencia = (EditText) context.findViewById(R.id.dataOcorrencia);
        horaOcorrencia = (EditText) context.findViewById(R.id.horaOcorrencia);

        getOrigemReclamacaoBusiness().inicializarComboOrigemReclamacao(context);

        reclamado.setSelection(0);
        reclamacao.setSelection(0);
        linha.setSelection(0);
        placa.setText(null);
        dataOcorrencia.setText(null);
        horaOcorrencia.setText(null);
        descricaoReclamacao.setText(null);
    }

    private ReclamacaoRequestDTO buildDTO() {

        Spinner reclamado, reclamacao, linhaSpinner;
        EditText placa, dataOcorrencia, horaOcorrencia, descricaoReclamacao;

        reclamado = (Spinner) context.findViewById(R.id.objetoReclamado);
        reclamacao = (Spinner) context.findViewById(R.id.reclamacao);
        linhaSpinner = (Spinner) context.findViewById(R.id.linha);
        placa = (EditText) context.findViewById(R.id.placa);
        dataOcorrencia = (EditText) context.findViewById(R.id.dataOcorrencia);
        horaOcorrencia = (EditText) context.findViewById(R.id.horaOcorrencia);
        descricaoReclamacao = (EditText) context.findViewById(R.id.descricaoReclamacao);

        ReclamacaoRequestDTO rec = new ReclamacaoRequestDTO();

        String[] objetoReclamadoArray = context
            .getResources().getStringArray(R.array.objeto_reclamado_valores);

        ObjetoReclamadoEnum objetoReclamadoEnum = ObjetoReclamadoEnum.getFromDescricao((String) reclamado.getSelectedItem());
        rec.setObjetoReclamado(objetoReclamadoEnum.name());

        if (!objetoReclamadoEnum.equals(ObjetoReclamadoEnum.OUTROS)) {
            rec.setIdOrigemReclamacao(((OrigemReclamacaoDTO) reclamacao.getSelectedItem()).getIdOrigemReclamacao());
        }

        if (linhaSpinner.getSelectedItem() != null) {
            LinhaDTO linha = (LinhaDTO) linhaSpinner.getSelectedItem();
            if (!linha.getNumeroLinha().equalsIgnoreCase("Selecione")) {
                rec.setIdLinha(linha.getIdLinha());
            }
        }

        if (!TextUtils.isEmpty(placa.getText())) {
            rec.setPlaca(placa.getText().toString().replaceAll("-", ""));
        }
        if (!TextUtils.isEmpty(dataOcorrencia.getText())) {
            rec.setDataOcorrencia(dataOcorrencia.getText().toString());
        } else {
            rec.setDataOcorrencia(dataOcorrencia.getHint().toString());
        }
        if (!TextUtils.isEmpty(horaOcorrencia.getText())) {
            rec.setHora(horaOcorrencia.getText().toString());
        }

        if (!TextUtils.isEmpty(descricaoReclamacao.getText())) {
            rec.setDescricao(descricaoReclamacao.getText().toString());
        }

        rec.setDataHoraRegistro(Dates.getCurrentDate(Dates.FORMAT_PT_BR_DATE_HOUR));

        return rec;
    }

}
