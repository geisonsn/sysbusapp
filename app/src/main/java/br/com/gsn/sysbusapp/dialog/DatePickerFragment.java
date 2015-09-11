package br.com.gsn.sysbusapp.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import br.com.gsn.sysbusapp.abstraction.BusinessDelegate;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.util.Dates;

public class DatePickerFragment extends DialogFragment
                            implements DatePickerDialog.OnDateSetListener {

    private BusinessDelegate<BusinessTaskOperation> delegate;
    private BusinessTaskOperation businessTaskOperation;
    private Activity context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        delegate = (BusinessDelegate) getActivity();
        businessTaskOperation = delegate.getBusinessDelegate();
        this.context = getActivity();

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DialogInterface dialog = new DatePickerDialog(context, this, year, month, day);
        businessTaskOperation.setCurrentDatePicker(dialog);

        return (Dialog) dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Date date = Dates.parse(day, month, year);
        Toast.makeText(context, day + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
        businessTaskOperation.closeDataPicker();
    }


}