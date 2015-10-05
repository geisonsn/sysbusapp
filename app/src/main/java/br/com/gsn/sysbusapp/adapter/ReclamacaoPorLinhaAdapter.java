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
import br.com.gsn.sysbusapp.model.ReclamacaoPorLinhaDTO;

/**
 * Created by Geison on 01/09/2015.
 */
public class ReclamacaoPorLinhaAdapter extends ArrayAdapter<ReclamacaoPorLinhaDTO> {

    private Context context;
    private List<ReclamacaoPorLinhaDTO> linhas;

    public ReclamacaoPorLinhaAdapter(Context context, List<ReclamacaoPorLinhaDTO> linhas) {
        super(context, R.layout.item_lista_reclamacoes_por_linha, linhas);
        this.context = context;
        this.linhas = linhas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            view = layoutInflater.inflate(R.layout.item_lista_reclamacoes_por_linha, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.reclamado = (TextView) view.findViewById(R.id.reclamado);
            viewHolder.reclamacao = (TextView) view.findViewById(R.id.reclamacao);
            viewHolder.totalReclamacoes = (TextView) view.findViewById(R.id.totalReclamacoes);
            view.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        ReclamacaoPorLinhaDTO linha = linhas.get(position);

        holder.reclamado.setText(linha.getReclamado());
        holder.reclamacao.setText(linha.getTipoReclamacao());
        holder.totalReclamacoes.setText(linha.getTotalReclamacoes().toString());

        return view;
    }

    static  class ViewHolder {
        TextView reclamado;
        TextView reclamacao;
        TextView totalReclamacoes;
    }
}
