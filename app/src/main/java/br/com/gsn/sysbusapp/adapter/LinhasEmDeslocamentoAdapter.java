package br.com.gsn.sysbusapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusapp.persistence.LinhaFavoritaDao;
import br.com.gsn.sysbusapp.util.Dates;
import br.com.gsn.sysbusapp.util.PreferencesUtil;

/**
 * Created by Geison on 01/09/2015.
 */
public class LinhasEmDeslocamentoAdapter extends ArrayAdapter<LocalizacaoLinhaDTO> {

    private Context context;
    private List<LocalizacaoLinhaDTO> linhas;
    private LinhaFavoritaDao linhaFavoritaDao;

    public LinhasEmDeslocamentoAdapter(Context context, List<LocalizacaoLinhaDTO> linhasFavorita) {
        super(context, R.layout.item_lista_home, linhasFavorita);
        this.context = context;
        this.linhas = linhasFavorita;
        linhaFavoritaDao = new LinhaFavoritaDao(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            view = layoutInflater.inflate(R.layout.item_lista_home, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.favorito = (TextView) view.findViewById(R.id.favorito);
            viewHolder.numeroLinha = (TextView) view.findViewById(R.id.numeroLinha);
            viewHolder.nomeEmpresa = (TextView) view.findViewById(R.id.nomeEmpresa);
            viewHolder.descricaoLinha = (TextView) view.findViewById(R.id.descricaoLinha);
            viewHolder.veiculo = (TextView) view.findViewById(R.id.veiculo);
            viewHolder.ultimoRegistro = (TextView) view.findViewById(R.id.ultimoRegistro);
            view.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) view.getTag();

        final LocalizacaoLinhaDTO linha = linhas.get(position);

        controlarExibicaoIconeFavorito(holder, linha);

        holder.favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable img = null;

                boolean isLinhaFavorita = linhaFavoritaDao
                    .isLinhaFavorita(linha.getIdLinha(), PreferencesUtil.getInstance(context).getIdUsuario());

                if (isLinhaFavorita) {
                    img = ContextCompat.getDrawable(context, R.drawable.ic_star_border_black);
                    holder.favorito.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                    linhaFavoritaDao.delete(linha.getIdLinha());
                } else {
                    img = ContextCompat.getDrawable(context, R.drawable.ic_star_black);
                    holder.favorito.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                    linhaFavoritaDao.insert(linha);
                }
            }
        });

        holder.numeroLinha.setText(linha.getNumeroLinha());
        holder.nomeEmpresa.setText(linha.getNomeEmpresa());
        holder.descricaoLinha.setText(linha.getDescricaoLinha());
        holder.veiculo.setText(linha.getNumeroRegistro());

        Date ultimoRegistro = Dates.parse(linha.getDataHoraRegistro(), Dates.FORMAT_PT_BR_DATE_HOUR);
        holder.ultimoRegistro.setText(Dates.format(ultimoRegistro, Dates.FORMAT_PT_BR_HOUR));

        return view;
    }

    /**
     * Controla a exibição do ícone de favorito
     * @param holder
     * @param linha
     */
    private void controlarExibicaoIconeFavorito(ViewHolder holder, LocalizacaoLinhaDTO linha) {
        Drawable img = ContextCompat.getDrawable(context, R.drawable.ic_star_black);
        boolean isLinhaFavorita = linhaFavoritaDao
            .isLinhaFavorita(linha.getIdLinha(), PreferencesUtil.getInstance(context).getIdUsuario());
        if (isLinhaFavorita) {
            holder.favorito.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
        } else {
            img = ContextCompat.getDrawable(context, R.drawable.ic_star_border_black);
            holder.favorito.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
        }
    }

    public static class ViewHolder {
        private TextView favorito;
        private TextView numeroLinha;
        private TextView nomeEmpresa;
        private TextView descricaoLinha;
        private TextView veiculo;
        private TextView ultimoRegistro;
    }
}
