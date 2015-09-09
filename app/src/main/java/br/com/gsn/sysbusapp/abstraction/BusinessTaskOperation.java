package br.com.gsn.sysbusapp.abstraction;

import android.app.Activity;

import br.com.gsn.sysbusapp.task.TaskOperation;

/**
 * Marca uma classe que realiza operaçães de rede por meio de AsyncTask
 */
public abstract class BusinessTaskOperation<Params,Progress, Result> implements TaskOperation<Params, Progress, Result> {

    protected Activity context;

}
