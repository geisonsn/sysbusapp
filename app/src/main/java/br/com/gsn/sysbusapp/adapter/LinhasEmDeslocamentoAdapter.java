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
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;

/**
 * Created by Geison on 01/09/2015.
 */
public class LinhasEmDeslocamentoAdapter extends ArrayAdapter<LocalizacaoLinhaDTO> {

    private Context context;
    private List<LocalizacaoLinhaDTO> linhas;

    public LinhasEmDeslocamentoAdapter(Context context, List<LocalizacaoLinhaDTO> linhasFavorita) {
        super(context, R.layout.item_lista_home, linhasFavorita);
        this.context = context;
        this.linhas = linhasFavorita;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            view = layoutInflater.inflate(R.layout.item_lista_home, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.numeroLinha = (TextView) view.findViewById(R.id.numeroLinha);
            viewHolder.nomeEmpresa = (TextView) view.findViewById(R.id.nomeEmpresa);
            viewHolder.descricaoLinha = (TextView) view.findViewById(R.id.descricaoLinha);
            view.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        LocalizacaoLinhaDTO linha = linhas.get(position);

        holder.numeroLinha.setText(linha.getNumeroLinha());
        holder.nomeEmpresa.setText(" Nome da empresa" + linha.getNumeroLinha());
        holder.descricaoLinha.setText("Descricao da linha " + linha.getNumeroLinha());

        return view;
    }

    static  class ViewHolder {
        TextView numeroLinha;
        TextView nomeEmpresa;
        TextView descricaoLinha;
    }
}
