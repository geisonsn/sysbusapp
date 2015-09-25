package br.com.gsn.sysbusapp.business;

import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.fragment.ListContentFragment;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusapp.model.SpringRestResponse;

/**
 * Created by Geison on 06/09/2015.
 */
public class HomeBusiness extends BusinessTaskOperation<Void, Integer, SpringRestResponse> {

    private ListContentFragment listFragment;

    private LinhasEmDeslocamentoBusiness linhasEmDeslocamentoBusiness;
    private LinhasEmDeslocamentoProximasBusiness linhasEmDeslocamentoProximasBusiness;

    private BusinessTaskOperation delegate;

    public HomeBusiness(ListContentFragment context) {
        this.context = context.getActivity();
        this.listFragment = context;
    }

    public void listarLinhas() {
        if (linhasEmDeslocamentoBusiness == null) {
            linhasEmDeslocamentoBusiness = new LinhasEmDeslocamentoBusiness(listFragment);
        }

        this.delegate = linhasEmDeslocamentoBusiness;

        linhasEmDeslocamentoBusiness.listarLinhas();

    }

    public void listarLinhasProximas() {
        if (linhasEmDeslocamentoProximasBusiness == null) {
            linhasEmDeslocamentoProximasBusiness = new LinhasEmDeslocamentoProximasBusiness(listFragment);
        }

        this.delegate = linhasEmDeslocamentoProximasBusiness;

        linhasEmDeslocamentoProximasBusiness.listarLinhas();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(Integer... values) {

    }

    @Override
    public SpringRestResponse doInBackground(Void... params) {
        //Nao faz nada por enquanto
        return null;
    }

    @Override
    public void onPostExecute(final SpringRestResponse response) {
        //Nao faz nada aqui por enquanto
    }

    @Override
    public void cancelTaskOperation() {
        delegate.cancelTaskOperation();
    }

    public LocalizacaoLinhaDTO getLocalizacaoLinha() {
        LocalizacaoLinhaDTO localizacaoLinhaDTO;
        if (linhasEmDeslocamentoProximasBusiness != null) {
            localizacaoLinhaDTO = linhasEmDeslocamentoProximasBusiness.localizacaoLinhaDTO;
        } else {
            localizacaoLinhaDTO = linhasEmDeslocamentoBusiness.localizacaoLinhaDTO;
        }
        return localizacaoLinhaDTO;
    }
}
