package br.com.gsn.sysbusapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusapp.util.Dates;

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
            viewHolder.veiculo = (TextView) view.findViewById(R.id.veiculo);
            viewHolder.ultimoRegistro = (TextView) view.findViewById(R.id.ultimoRegistro);
            view.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        LocalizacaoLinhaDTO linha = linhas.get(position);

        holder.localizacaLinha = linha;

        holder.numeroLinha.setText(linha.getNumeroLinha());
        holder.nomeEmpresa.setText(linha.getNomeEmpresa());
        holder.descricaoLinha.setText(linha.getDescricaoLinha());
        holder.veiculo.setText(linha.getNumeroRegistro());

        Date ultimoRegistro = Dates.parse(linha.getDataHoraRegistro(), Dates.FORMAT_PT_BR_DATE_HOUR);
        holder.ultimoRegistro.setText(Dates.format(ultimoRegistro, Dates.FORMAT_PT_BR_HOUR));

        return view;
    }

    public static class ViewHolder {
        private TextView numeroLinha;
        private TextView nomeEmpresa;
        private TextView descricaoLinha;
        private TextView veiculo;
        private TextView ultimoRegistro;
        private LocalizacaoLinhaDTO localizacaLinha;

        public LocalizacaoLinhaDTO getLocalizacalLinha() {
            return localizacaLinha;
        }
    }
}
