package br.com.gsn.sysbusapp.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import br.com.gsn.sysbusapp.abstraction.BusinessDelegate;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.util.DatePickerUtil;
import br.com.gsn.sysbusapp.util.Dates;

public class DatePickerFragment extends DialogFragment
                            implements DatePickerDialog.OnDateSetListener {

    private BusinessTaskOperation businessTaskOperation;
    private Activity context;
    private DatePickerUtil.DatePickerWrapper datePickerWrapper;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int idComponent = 0;

        Bundle arguments = getArguments();

        if (savedInstanceState != null) {
            idComponent = savedInstanceState.getInt("sourceId");
        } else {
            if (arguments != null) {
                idComponent = arguments.getInt("sourceId");
                savedInstanceState = arguments;
            }
        }

        businessTaskOperation = (BusinessTaskOperation) ((BusinessDelegate) getActivity())
                .getBusinessDelegate();

        datePickerWrapper = businessTaskOperation.getDatePickerWrapper();

        this.context = getActivity();

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DialogInterface dialog = new DatePickerDialog(context, this, year, month, day);

        //Método deve executado após a criação do dialogo
        datePickerWrapper.handleCreateDialog(getActivity(), dialog, idComponent);

        return (Dialog) dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Date date = Dates.parse(day, month, year);
        datePickerWrapper.handleDataSet(date);
    }

}