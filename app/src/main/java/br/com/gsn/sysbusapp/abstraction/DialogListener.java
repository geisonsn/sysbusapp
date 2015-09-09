package br.com.gsn.sysbusapp.abstraction;

import android.content.DialogInterface;

/**
 * Created by Geison on 05/09/2015.
 */
public interface DialogListener {
    void onConfirmButton(DialogInterface dialog);
    void onCancelButton(DialogInterface dialog);
    void onCloseDialog();
    void setCurrentDialog(DialogInterface dialog);
}
