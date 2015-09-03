package br.com.gsn.sysbusapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.model.ReclamacaoRankingDTO;

/**
 * Created by Geison on 01/09/2015.
 */
public class ReclamacaoRankingAdapter extends ArrayAdapter<ReclamacaoRankingDTO> {

    private Context context;
    private List<ReclamacaoRankingDTO> linhas;

    public ReclamacaoRankingAdapter(Context context, List<ReclamacaoRankingDTO> linhas) {
        super(context, R.layout.item_lista_reclamacao_ranking, linhas);
        this.context = context;
        this.linhas = linhas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            view = layoutInflater.inflate(R.layout.item_lista_reclamacao_ranking, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.numeroLinha = (TextView) view.findViewById(R.id.numeroLinha);
            viewHolder.nomeEmpresa = (TextView) view.findViewById(R.id.nomeEmpresa);
            viewHolder.totalReclamacoes = (TextView) view.findViewById(R.id.totalReclamacoes);
            view.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        ReclamacaoRankingDTO linha = linhas.get(position);

        holder.numeroLinha.setText(linha.getNumeroLinha());
        holder.nomeEmpresa.setText(linha.getNomeEmpresa());
        holder.totalReclamacoes.setText(linha.getTotalReclamacoes().toString());

        return view;
    }

    static  class ViewHolder {
        TextView numeroLinha;
        TextView nomeEmpresa;
        TextView totalReclamacoes;
    }
}
