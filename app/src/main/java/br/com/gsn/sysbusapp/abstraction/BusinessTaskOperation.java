package br.com.gsn.sysbusapp.abstraction;

import android.app.Activity;

import br.com.gsn.sysbusapp.task.TaskOperation;
import br.com.gsn.sysbusapp.util.DatePickerUtil;

/**
 * Marca uma classe que realiza operaçães de rede por meio de AsyncTask
 */
public abstract class BusinessTaskOperation<Params,Progress, Result> implements TaskOperation<Params, Progress, Result> {

    protected Activity context;
    protected DatePickerUtil.DatePickerWrapper datePickerWrapper;

    protected BusinessTaskOperation() {
        this.datePickerWrapper =  new DatePickerUtil.DatePickerWrapper();
    }

    public DatePickerUtil.DatePickerWrapper getDatePickerWrapper() {
        return datePickerWrapper;
    }
}
