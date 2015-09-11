package br.com.gsn.sysbusapp.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;
import android.view.Window;

import br.com.gsn.sysbusapp.abstraction.BusinessDelegate;
import br.com.gsn.sysbusapp.abstraction.BusinessDialogTaskOperation;

/**
 * Created by Geison on 05/09/2015.
 */
public abstract class DialogContentFragment extends DialogFragment implements BusinessDelegate<BusinessDialogTaskOperation> {

    protected BusinessDialogTaskOperation delegate;

    public DialogContentFragment() {
    }

    /**
     * Ao criar DialogContentFragment associar delegate
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBusinessDelegate();
    }

    /**
     * Após criar DialogContentFragment e associar delegate associar evento de close do diálogo
     */
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        delegate.setCurrentDialog(getDialog());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public BusinessDialogTaskOperation getBusinessDelegate() {
        return delegate;
    }

}
