package br.com.gsn.sysbusapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.business.ReclamacaoRankingBusiness;

public class ReclamacaoFragment extends ListContentFragment /*implements BusinessDelegate<BusinessTaskOperation>*/ {

    public ReclamacaoFragment() {}

    @Override
    public void setBusinessDelegate() {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_reclamacao, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_nova_reclamacao) {
            startActivity(new Intent(getActivity(), NovaReclamacaoActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }*/
}
