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
import br.com.gsn.sysbusapp.model.LinhaFavoritaDTO;

/**
 * Created by Geison on 01/09/2015.
 */
public class LinhaFavoritaAdapter extends ArrayAdapter<LinhaFavoritaDTO> {

    private Context context;
    private List<LinhaFavoritaDTO> linhasFavoritas;

    public LinhaFavoritaAdapter(Context context, List<LinhaFavoritaDTO> linhasFavoritas) {
        super(context, R.layout.item_lista_favoritos, linhasFavoritas);
        this.context = context;
        this.linhasFavoritas = linhasFavoritas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            view = layoutInflater.inflate(R.layout.item_lista_favoritos, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.numeroLinha = (TextView) view.findViewById(R.id.numeroLinha);
            viewHolder.nomeEmpresa = (TextView) view.findViewById(R.id.nomeEmpresa);
            viewHolder.descricaoLinha = (TextView) view.findViewById(R.id.descricaoLinha);
            view.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        LinhaFavoritaDTO linha = linhasFavoritas.get(position);

        holder.numeroLinha.setText(linha.getNumeroLinha());
        holder.nomeEmpresa.setText(linha.getEmpresaLinha());
        holder.descricaoLinha.setText(linha.getDescricaoLinha());

        return view;
    }

    static  class ViewHolder {
        TextView numeroLinha;
        TextView nomeEmpresa;
        TextView descricaoLinha;
    }
}
