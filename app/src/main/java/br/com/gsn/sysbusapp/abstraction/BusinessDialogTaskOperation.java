package br.com.gsn.sysbusapp.abstraction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;

import br.com.gsn.sysbusapp.R;

/**
 * Classe usada para realizar requisições de rede por meio de AsyncTask e realizar operações por meio Dialog
 */
public abstract class BusinessDialogTaskOperation<Params,Progress, Result> extends BusinessTaskOperation<Params, Progress, Result> implements DialogListener {

    protected DialogInterface currentDialog;

    @Override
    public void onPreExecute() {
    }

    @Override
    public void onCloseDialog() {
        this.currentDialog.cancel();
    }

    @Override
    public void onCancelButton(DialogInterface dialog) {
        this.cancelTaskOperation();
        this.onCloseDialog();
    }

    @Override
    public void setCurrentDialog(DialogInterface dialog) {
        this.currentDialog = dialog;
    }

    public void showRequestProgress() {
        Dialog dialogFragment = ((Dialog) this.currentDialog);

        ViewGroup containerLoading = (ViewGroup) dialogFragment.findViewById(R.id.container_loading);

        if (containerLoading.getVisibility() == View.VISIBLE) {
            containerLoading.setVisibility(View.GONE);
        } else {
            containerLoading.setVisibility(View.VISIBLE);
        }

        disableButtons();
    }

    private void disableButtons() {
        Dialog dialogFragment = ((Dialog) this.currentDialog);

        ViewGroup containerLoading = (ViewGroup) dialogFragment.findViewById(R.id.container_loading);
        ViewGroup containerBotoes = (ViewGroup) dialogFragment.findViewById(R.id.container_botoes);

        if (containerLoading.getVisibility() == View.VISIBLE) {
            for (int i = 0; i < containerBotoes.getChildCount(); i++) {
                View viewButton = containerBotoes.getChildAt(i);
                viewButton.setEnabled(false);
            }
        } else {
            for (int i = 0; i < containerBotoes.getChildCount(); i++) {
                View viewButton = containerBotoes.getChildAt(i);
                viewButton.setEnabled(true);
            }
        }

    }
}
