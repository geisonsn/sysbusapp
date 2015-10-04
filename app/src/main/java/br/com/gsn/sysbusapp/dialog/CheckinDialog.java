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

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.business.CheckinBusiness;
import br.com.gsn.sysbusapp.fragment.DialogContentFragment;
import br.com.gsn.sysbusapp.model.VeiculoDTO;

/**
 * Created by Geison on 05/09/2015.
 */
public class CheckinDialog extends DialogContentFragment {

    private CheckinBusiness checkinBusiness;
    private AutoCompleteTextView linha;
    private View view;

    @Override
    public void setBusinessDelegate() {
        this.delegate = new CheckinBusiness(getActivity());
        checkinBusiness = ((CheckinBusiness)delegate);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.checkin, container, false);

        checkinBusiness.inicializarServicoLocalizacao();

        Button botaoCancelar = (Button) view.findViewById(R.id.botao_cancelar);
        Button botaoLogin = (Button) view.findViewById(R.id.botao_checkin);

        linha = (AutoCompleteTextView) view.findViewById(R.id.linha);
        linha.addTextChangedListener(textWatcherListener);
        linha.setOnItemClickListener(itemClickListener);

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

    @Override
    public void onStart() {
        super.onStart();
        checkinBusiness.startarServicoLocalizacao();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.
        checkinBusiness.restartarServicoLocalizacao();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        checkinBusiness.pausarServicoLocalizacao();
    }

    @Override
    public void onStop() {
        checkinBusiness.pararServicoLocalizacao();
        super.onStop();
    }

    private TextWatcher textWatcherListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkinBusiness.veiculo = null;
            if (s.length() == 3 && !linha.isPerformingCompletion()) {
                checkinBusiness.listarVeiculos(s.toString(), view);
            }
            if (s.length() > 3 && !linha.isPerformingCompletion()) {
                linha.setText(linha.getText().toString().substring(0, 2));
                linha.setSelection(linha.getText().length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            VeiculoDTO v = (VeiculoDTO) parent.getItemAtPosition(position);
            ((CheckinBusiness) delegate).veiculo = v;
        }
    };

}
