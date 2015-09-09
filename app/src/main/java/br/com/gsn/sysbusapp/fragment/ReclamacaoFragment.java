package br.com.gsn.sysbusapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.business.ReclamacaoRankingBusiness;

public class ReclamacaoFragment extends ListContentFragment /*implements BusinessDelegate<BusinessTaskOperation>*/ {

    public ReclamacaoFragment() {}

    @Override
    public void setDelegate() {
        this.delegate = new ReclamacaoRankingBusiness(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_reclamacao_ranking, container, false);

        //Ocultando cabeçalho
        LinearLayout header = (LinearLayout) rootView.findViewById(R.id.header);
        header.setVisibility(View.GONE);

        ((ReclamacaoRankingBusiness)delegate).listarReclamacoes();

        return rootView;
    }

}
