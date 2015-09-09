package br.com.gsn.sysbusapp.abstraction;

import android.content.DialogInterface;

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
}
