package br.com.gsn.sysbusapp.task;

/**
 * Created by Geison on 06/09/2015.
 */
public interface TaskOperation<Params, Progress, Result> {

    void onPreExecute();
    void onProgressUpdate(Progress... values);
    Result doInBackground(Params... params);
    void onPostExecute(Result result);
    void cancelTaskOperation();
}
