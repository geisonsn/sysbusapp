package br.com.gsn.sysbusapp.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.business.CheckinBusiness;
import br.com.gsn.sysbusapp.business.VeiculoBusiness;
import br.com.gsn.sysbusapp.fragment.DialogContentFragment;
import br.com.gsn.sysbusapp.model.VeiculoDTO;

/**
 * Created by Geison on 05/09/2015.
 */
public class CheckinDialog extends DialogContentFragment {

    private AutoCompleteTextView linha;

    @Override
    public void setBusinessDelegate() {
        this.delegate = new CheckinBusiness(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.checkin, container, false);

        Button botaoCancelar = (Button) view.findViewById(R.id.botao_cancelar);
        Button botaoLogin = (Button) view.findViewById(R.id.botao_checkin);

        linha = (AutoCompleteTextView) view.findViewById(R.id.linha);
        linha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new VeiculoBusiness(getActivity(), view).listarVeiculos(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        linha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VeiculoDTO v = (VeiculoDTO) parent.getItemAtPosition(position);
                Toast.makeText(parent.getContext(), "linha selecionada " + v.getNumeroRegistro(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Nenhuma linha selecionada", Toast.LENGTH_SHORT).show();
            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.onCancelButton(getDialog());
            }
        });

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.onConfirmButton(getDialog());
            }
        });

        return view;
    }

}
