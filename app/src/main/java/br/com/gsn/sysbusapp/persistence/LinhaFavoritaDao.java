package br.com.gsn.sysbusapp.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import br.com.gsn.sysbusapp.cache.LinhaFavorita;
import br.com.gsn.sysbusapp.model.LinhaFavoritaDTO;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusapp.util.PreferencesUtil;

/**
 * Created by Geison on 29/08/2015.
 */
public class LinhaFavoritaDao {

    private DatabaseHelper helper;
    private Context context;

    public LinhaFavoritaDao(Context context) {
        this.helper = DatabaseHelper.getInstance(context);
        this.context = context;
    }

    public boolean isLinhaFavorita(Long idLinha, Long idUsuario) {
        long count = DatabaseUtils.queryNumEntries(helper.getReadableDatabase(), LinhaFavorita.TABLE_NAME,
                LinhaFavorita.ID_USUARIO + "= ? and " + LinhaFavorita.ID_LINHA + " = ? ",
                new String[]{idUsuario.toString(), idLinha.toString()});
        return count > 0;
    }

    public void insert(LocalizacaoLinhaDTO localizacaoLinhaDTO) {
        Long idUsuario = PreferencesUtil.getInstance(context).getIdUsuario();
        helper.getWritableDatabase().insert(LinhaFavorita.TABLE_NAME, null,
                LinhaFavorita.getContentValues(idUsuario, localizacaoLinhaDTO));
    }

    public void insert(LinhaFavoritaDTO linhaFavoritaDTO) {
        Long idUsuario = PreferencesUtil.getInstance(context).getIdUsuario();
        helper.getWritableDatabase().insert(LinhaFavorita.TABLE_NAME, null,
                LinhaFavorita.getContentValues(idUsuario, linhaFavoritaDTO));
    }

    public void delete(Long idLinha) {
        helper.getWritableDatabase().delete(LinhaFavorita.TABLE_NAME, LinhaFavorita.ID_LINHA + " = ?",
            new String[]{idLinha.toString()});
    }

    public List<LinhaFavorita> list(Long idUsuario) {
        final String[] colunas = LinhaFavorita.COLUNAS;
        final String orderBy = LinhaFavorita.NUMERO_LINHA;
        Cursor cursor = helper.getReadableDatabase().query(LinhaFavorita.TABLE_NAME, colunas,
                LinhaFavorita.ID_USUARIO + "=?", new String[]{idUsuario.toString()}, null, null, orderBy);

        List<LinhaFavorita> linhaFavoritas = new ArrayList<>();

        while (cursor.moveToNext()) {
            linhaFavoritas.add(new LinhaFavorita(cursor));
        }

        return linhaFavoritas;
    }

}
