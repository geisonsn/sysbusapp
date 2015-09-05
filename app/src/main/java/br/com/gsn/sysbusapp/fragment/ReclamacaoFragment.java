package br.com.gsn.sysbusapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.abstraction.ListContentFragment;
import br.com.gsn.sysbusapp.task.ReclamacaoRankingTask;

public class ReclamacaoFragment extends ListContentFragment {

    private ReclamacaoRankingTask task;

    public ReclamacaoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_reclamacao_ranking, container, false);

        //Ocultando cabeçalho
        LinearLayout header = (LinearLayout) rootView.findViewById(R.id.header);
        header.setVisibility(View.GONE);

        task = new ReclamacaoRankingTask(this);
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
