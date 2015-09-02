package br.com.gsn.sysbusapp.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.task.LinhaFavoritaTask;

/**
 * Created by Geison on 31/08/2015.
 */
public class FavoritosFragment extends ListFragment {

    private ProgressBar progressBar;

    public FavoritosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LinhaFavoritaTask(this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favoritos, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Inflate the layout for this fragment
        return rootView;
    }
}
