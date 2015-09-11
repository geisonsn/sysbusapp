package br.com.gsn.sysbusapp.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import br.com.gsn.sysbusapp.dialog.DatePickerFragment;

public class TimerPickerUtil {

    private DatePickerFragment datePicker;
    private FragmentActivity context;
    private String name = "timerPickerDialog";
    private int sourceId = 0;

    public TimerPickerUtil(FragmentActivity context, int sourceId) {
        this.context = context;
        this.datePicker = new DatePickerFragment();
        this.sourceId = sourceId;
    }

    public TimerPickerUtil setName(String name) {
        this.name = name;
        return this;
    }

    public void show() {
        Bundle bundle = new Bundle();
        bundle.putString("dialogName", name);
        bundle.putInt("sourceId", sourceId);
        datePicker.setArguments(bundle);
        datePicker.show(context.getSupportFragmentManager(), this.name);
    }

    public static class TimerPickerWrapper implements DatePickerListener {

        private DialogInterface currentDialog;
        private Activity context;
        private View source;

        @Override
        public void handleCreateDialog(Activity context, DialogInterface dialog, int idSource) {
            this.context = context;
            this.currentDialog = dialog;
            this.source = context.findViewById(idSource);
        }

        @Override
        public void handleDataSet(Date date) {
            if (source == null) {
                Toast.makeText(context, "Hora " + Dates.format(date, Dates.FORMAT_PT_BR_HOUR), Toast.LENGTH_SHORT)
                        .show();
            } else if (source instanceof TextView) {
                ((TextView)source).setText(Dates.format(date, Dates.FORMAT_PT_BR_HOUR));
            } else if (source instanceof EditText){
                ((EditText)source).setText(Dates.format(date, Dates.FORMAT_PT_BR_HOUR));
            } else {
                Toast.makeText(context, "Data " + Dates.format(date, Dates.FORMAT_PT_BR_HOUR), Toast.LENGTH_SHORT)
                        .show();
            }
            currentDialog.cancel();
        }
    }
}