package br.com.gsn.sysbusapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import br.com.gsn.sysbusapp.abstraction.BusinessDelegate;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.util.Dates;
import br.com.gsn.sysbusapp.util.TimePickerUtil;

public class TimePickerFragment extends DialogFragment
                            implements TimePickerDialog.OnTimeSetListener {


    private BusinessTaskOperation businessTaskOperation;
    private Activity context;
    private TimePickerUtil.TimePickerWrapper timePickerWrapper;

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

        this.context = getActivity();
        businessTaskOperation = (BusinessTaskOperation) ((BusinessDelegate) context)
                .getBusinessDelegate();

        timePickerWrapper = businessTaskOperation.getTimePickerWrapper();


        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        DialogInterface dialog = new TimePickerDialog(context, this, hour, minute,
                DateFormat.is24HourFormat(context));

        //Método deve executado após a criação do dialogo
        timePickerWrapper.handleCreateDialog(context, dialog, idComponent);

        return (Dialog) dialog;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Date date = Dates.parse(hourOfDay, minute);
        timePickerWrapper.handleDataSet(date);
    }
}