package br.com.gsn.sysbusapp.abstraction;

import android.app.Activity;

import br.com.gsn.sysbusapp.task.TaskOperation;
import br.com.gsn.sysbusapp.util.DatePickerUtil;
import br.com.gsn.sysbusapp.util.TimePickerUtil;

/**
 * Marca uma classe que realiza operaçães de rede por meio de AsyncTask
 */
public abstract class BusinessTaskOperation<Params,Progress, Result> implements TaskOperation<Params, Progress, Result> {

    protected Activity context;
    protected DatePickerUtil.DatePickerWrapper datePickerWrapper;
    protected TimePickerUtil.TimePickerWrapper timePickerWrapper;

    protected BusinessTaskOperation() {
        this.datePickerWrapper =  new DatePickerUtil.DatePickerWrapper();
        this.timePickerWrapper =  new TimePickerUtil.TimePickerWrapper();
    }

    public DatePickerUtil.DatePickerWrapper getDatePickerWrapper() {
        return datePickerWrapper;
    }

    public TimePickerUtil.TimePickerWrapper getTimePickerWrapper() {
        return timePickerWrapper;
    }
}
