package br.com.gsn.sysbusapp.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.business.ReclamacaoPorLinhaBusiness;
import br.com.gsn.sysbusapp.fragment.DialogContentFragment;

/**
 * Created by Geison on 05/10/2015.
 */
public class ReclamacaoPorLinhaDialog extends DialogContentFragment {

    private ReclamacaoPorLinhaBusiness reclamacaoPorLinhaBusiness;
    public Long idLinha;
    public String linha;

    @Override
    public void setBusinessDelegate() {
        this.delegate = new ReclamacaoPorLinhaBusiness(this.getActivity());
        this.reclamacaoPorLinhaBusiness = (ReclamacaoPorLinhaBusiness) this.delegate;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.principais_reclamacoes_por_linha, container, false);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(title.getText().toString() + " " + linha);

        Button botaoFechar = (Button) view.findViewById(R.id.botao_fechar);

        botaoFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.onCancelButton(getDialog());
            }
        });

        reclamacaoPorLinhaBusiness.listarReclamacoes(idLinha, view);

        this.setCancelable(true);

        return view;
    }

}
