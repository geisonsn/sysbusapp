package br.com.gsn.sysbusapp.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.task.ReclamacaoRankingTask;

public class ReclamacaoFragment extends ListFragment {

    public ReclamacaoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        new ReclamacaoRankingTask(this).execute();

        return rootView;
    }
}
