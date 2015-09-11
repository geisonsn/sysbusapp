package br.com.gsn.sysbusapp.business;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

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
        if (isValidForm()) {
            if (ConnectionUtil.isNetworkConnected(context)) {
                task = new TemplateAsyncTask<>(this);
                task.execute(this.buildDTO());
            }
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
//        return SpringRestClient.post(context, UrlServico.URL_NOVA_RECLAMACAO, params[0], ReclamacaoRequestDTO.class);
        return SpringRestClient.postForObject(context, UrlServico.URL_NOVA_RECLAMACAO, params[0], ReclamacaoRequestDTO.class);
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
//        final ProgressBar progressBar = (ProgressBar) context.findViewById(R.id.progressBar);

        response.setOnHttpOk(new AbstractSpringRestResponse.OnHttpOk() {
            @Override
            public void doThis() {
                String resposta = (String) response.getObjectReturn();
            }
        });

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

    /*@Override
    public void closeDialog(OnCloseDialog onCloseDialog) {
        onCloseDialog = new OnCloseDialog() {
            @Override
            public void closeDialog() {

            }
        };
    }*/


    public boolean isValidForm() {

        boolean isValid = true;

        Spinner reclamado, reclamacao, linha;
        EditText placa, dataOcorrencia, descricaoReclamacao;

        reclamado = (Spinner) context.findViewById(R.id.objetoReclamado);
        reclamacao = (Spinner) context.findViewById(R.id.reclamacao);
        linha = (Spinner) context.findViewById(R.id.linha);
        placa = (EditText) context.findViewById(R.id.placa);
        descricaoReclamacao = (EditText) context.findViewById(R.id.descricaoReclamacao);

        List<View> invalidFields = new ArrayList<>();

        int reclamadoSelecionado = reclamado.getSelectedItemPosition();

        if (reclamadoSelecionado == 0) {
            placa.setError("Selecione o reclamado");
            invalidFields.add(reclamado);
            isValid = false;
        }

        if (reclamadoSelecionado != 4
                && reclamadoSelecionado != 0) { //Diferente da opçao SELECIONE e OUTROS

            if (reclamacao.getSelectedItem() == null) {
                descricaoReclamacao.setError("Informe o motivo da reclamação");
                invalidFields.add(reclamacao);
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

        Spinner reclamado, reclamacao, linha;
        EditText placa, dataOcorrencia, descricaoReclamacao;

        reclamado = (Spinner) context.findViewById(R.id.objetoReclamado);
        reclamacao = (Spinner) context.findViewById(R.id.reclamacao);
        linha = (Spinner) context.findViewById(R.id.linha);
        placa = (EditText) context.findViewById(R.id.placa);
        dataOcorrencia = (EditText) context.findViewById(R.id.dataOcorrencia);
        descricaoReclamacao = (EditText) context.findViewById(R.id.descricaoReclamacao);

        ReclamacaoRequestDTO rec = new ReclamacaoRequestDTO();

        String[] objetoReclamadoArray = context
            .getResources().getStringArray(R.array.objeto_reclamado_valores);

        rec.setObjetoReclamado(ObjetoReclamadoEnum.getFromDescricao((String)reclamado.getSelectedItem()).name());
        rec.setIdOrigemReclamacao(((OrigemReclamacaoDTO)reclamacao.getSelectedItem()).getIdOrigemReclamacao());//TODO resolver para outros
        if (linha.getSelectedItem() != null) {
            rec.setIdLinha(((LinhaDTO)linha.getSelectedItem()).getIdLinha());
        }
        if (!TextUtils.isEmpty(placa.getText())) {
            rec.setPlaca(placa.getText().toString());
        }
        if (!TextUtils.isEmpty(dataOcorrencia.getText())) {
            rec.setDataOcorrencia(dataOcorrencia.getText().toString());
        } else {
            rec.setDataOcorrencia(dataOcorrencia.getHint().toString());
        }

        if (!TextUtils.isEmpty(descricaoReclamacao.getText())) {
            rec.setDescricao(descricaoReclamacao.getText().toString());
        }

        rec.setDataHoraRegistro(Dates.getCurrentDate(Dates.FORMAT_PT_BR_DATE_HOUR));

        return rec;
    }

}
