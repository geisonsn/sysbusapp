package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
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

    public NovaReclamacaoBusiness(Activity context) {
        this.context = context;
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
        View botaoSave = context.findViewById(R.id.action_save_reclamacao);
        View botaoRecarregarDados = context.findViewById(R.id.action_recarregar_dados);
        if (this.menuItemProgressBar.isVisible()) {
            botaoSave.setVisibility(View.GONE);
            botaoRecarregarDados.setVisibility(View.GONE);
        } else {
            botaoSave.setVisibility(View.VISIBLE);
            botaoRecarregarDados.setVisibility(View.VISIBLE);
        }
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
            }
        });

        handleShowActionButton(); //Ocultando progress bar
        hideMenuItemProgressBar(); //Exibindo botão salvar

        response.executeCallbacks();
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
            //Cancela outras tasks que estejam executando
            linhaBusiness.cancelTaskOperation();
            origemReclamacaoBusiness.cancelTaskOperation();
        }
    }

    public void initForm() {
        linhaBusiness = new LinhaBusiness(context);
        listarLinhas();
    }

    public void listarLinhas() {
        linhaBusiness.listarLinhas();
    }

    public void listarOrigemReclamacao(String reclamado) {
        if (origemReclamacaoBusiness == null) {
            origemReclamacaoBusiness = new OrigemReclamacaoBusiness(context);
        }
        origemReclamacaoBusiness.listarOrigemReclamacao(reclamado);
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

            if (reclamacao.getSelectedItem() == null) {
                TextView viewReclamacao = (TextView)reclamacao.getSelectedView();
                viewReclamacao.setError("Informe o motivo da reclamação");
                invalidFields.add(viewReclamacao);
                isValid = false;
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
            rec.setIdOrigemReclamacao(((OrigemReclamacaoDTO) reclamacao.getSelectedItem()).getIdOrigemReclamacao());//TODO resolver para outros
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
