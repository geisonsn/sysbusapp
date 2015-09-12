package br.com.gsn.sysbusapp.util;

import android.app.Activity;
import android.content.DialogInterface;

import java.util.Date;

public interface PickerListener {
        void handleCreateDialog(Activity context, DialogInterface dialog, int idSource);
        void handleDataSet(Date date);
    }