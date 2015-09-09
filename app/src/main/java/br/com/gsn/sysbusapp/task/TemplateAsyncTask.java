package br.com.gsn.sysbusapp.task;

import android.os.AsyncTask;

import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;

/**
 * Created by Geison on 06/09/2015.
 */
public class TemplateAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private BusinessTaskOperation businessOperation;

    public TemplateAsyncTask(BusinessTaskOperation businessOperation) {
        this.businessOperation = businessOperation;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Progress... values) {
        super.onProgressUpdate(values);
        businessOperation.onProgressUpdate((Progress) values);
    }

    @Override
    protected Result doInBackground(Params... params) {
        return (Result) businessOperation.doInBackground(params);
    }

    @Override
    protected void onPostExecute(Result result) {
        businessOperation.onPostExecute(result);
    }
}
