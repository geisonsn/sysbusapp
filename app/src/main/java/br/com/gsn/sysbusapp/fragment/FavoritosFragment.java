package br.com.gsn.sysbusapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.ListContentFragment;
import br.com.gsn.sysbusapp.task.LinhaFavoritaTask;

/**
 * Created by Geison on 31/08/2015.
 */
public class FavoritosFragment extends ListContentFragment {

    public FavoritosFragment() {}

    private LinhaFavoritaTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        task = new LinhaFavoritaTask(this);
        task.execute();

        return rootView;
    }

    @Override
    public void cancelTaskOperation() {
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
    }
}
