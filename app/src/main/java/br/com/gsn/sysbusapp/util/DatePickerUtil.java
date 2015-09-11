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

public class DatePickerUtil {

    private DatePickerFragment datePicker;
    private FragmentActivity context;
    private String name = "dialog";
    private int sourceId = 0;

    public DatePickerUtil(FragmentActivity context, int sourceId) {
        this.context = context;
        this.datePicker = new DatePickerFragment();
        this.sourceId = sourceId;
    }

    public DatePickerUtil setName(String name) {
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

    private interface DatePickerListener {
        void handleCreateDialog(Activity context, DialogInterface dialog, int idSource);
        void handleDataSet(Date date);
    }

    public static class DatePickerWrapper implements DatePickerListener {

        private DialogInterface currentDatePicker;
        private Activity context;
        private View source;

        @Override
        public void handleCreateDialog(Activity context, DialogInterface dialog, int idSource) {
            this.context = context;
            this.currentDatePicker = dialog;
            this.source = context.findViewById(idSource);
        }

        @Override
        public void handleDataSet(Date date) {
            if (source == null) {
                Toast.makeText(context, "Data " + Dates.format(date, Dates.FORMAT_PT_BR_DATE), Toast.LENGTH_SHORT)
                        .show();
            } else if (source instanceof TextView) {
                ((TextView)source).setText(Dates.format(date, Dates.FORMAT_PT_BR_DATE));
            } else if (source instanceof EditText){
                ((EditText)source).setText(Dates.format(date, Dates.FORMAT_PT_BR_DATE));
            } else {
                Toast.makeText(context, "Data " + Dates.format(date, Dates.FORMAT_PT_BR_DATE), Toast.LENGTH_SHORT)
                        .show();
            }
            currentDatePicker.cancel();
        }
    }
}