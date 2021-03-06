package br.com.gsn.sysbusapp.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.business.LoginBusiness;
import br.com.gsn.sysbusapp.fragment.DialogContentFragment;

/**
 * Created by Geison on 05/09/2015.
 */
public class LoginDialog extends DialogContentFragment {

    @Override
    public void setBusinessDelegate() {
        this.delegate = new LoginBusiness(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login, container, false);

        Button botaoCancelar = (Button) view.findViewById(R.id.botao_cancelar);
        Button botaoLogin = (Button) view.findViewById(R.id.botao_login);

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
