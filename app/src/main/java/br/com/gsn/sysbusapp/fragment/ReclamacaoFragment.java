package br.com.gsn.sysbusapp.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.task.ReclamacaoRankingTask;
import br.com.gsn.sysbusapp.util.ConnectionUtil;

public class ReclamacaoFragment extends ListFragment {

    private TextView emptyView;
    private ProgressBar progressBar;

    public ReclamacaoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        emptyView = (TextView) rootView.findViewById(android.R.id.empty);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        if (ConnectionUtil.isOnline(getActivity())) {
            emptyView.setText("");
            progressBar.setVisibility(View.VISIBLE);
            new ReclamacaoRankingTask(this).execute();
        } else {
            emptyView.setText("Sem conexão com a internet");
            progressBar.setVisibility(View.GONE);
        }

        return rootView;
    }
}
