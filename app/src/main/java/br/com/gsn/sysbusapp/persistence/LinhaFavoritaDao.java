package br.com.gsn.sysbusapp.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import br.com.gsn.sysbusapp.cache.LinhaFavorita;
import br.com.gsn.sysbusapp.model.LinhaFavoritaDTO;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;

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

    public boolean isLinhaFavorita(Long idUsuario, Long idLinha) {
        String whereClause = LinhaFavorita.ID_USUARIO + " = ? and " + LinhaFavorita.ID_LINHA + " = ? ";
        String whereArgs[] = new String[]{idUsuario.toString(), idLinha.toString()};

        long count = DatabaseUtils.queryNumEntries(helper.getReadableDatabase(),
                LinhaFavorita.TABLE_NAME, whereClause, whereArgs);
        return count > 0;
    }

    public void insert(Long idUsuario, LocalizacaoLinhaDTO localizacaoLinhaDTO) {
        helper.getWritableDatabase().insert(LinhaFavorita.TABLE_NAME, null,
                LinhaFavorita.getContentValues(idUsuario, localizacaoLinhaDTO));
    }

    public void insert(Long idUsuario, LinhaFavoritaDTO linhaFavoritaDTO) {
        helper.getWritableDatabase().insert(LinhaFavorita.TABLE_NAME, null,
                LinhaFavorita.getContentValues(idUsuario, linhaFavoritaDTO));
    }

    public void delete(Long idUsuario, Long idLinha) {
        String whereClause = LinhaFavorita.ID_USUARIO + " = ? and " + LinhaFavorita.ID_LINHA + " = ? ";
        String whereArgs[] = new String[] {idUsuario.toString(), idLinha.toString()};
        helper.getWritableDatabase().delete(LinhaFavorita.TABLE_NAME, whereClause, whereArgs);
    }

    public List<LinhaFavorita> list(Long idUsuario) {
        final String[] colunas = LinhaFavorita.COLUNAS;
        final String whereClause = LinhaFavorita.ID_USUARIO + " = ? ";
        final String whereArgs[] = new String[]{idUsuario.toString()};
        final String orderBy = LinhaFavorita.NUMERO_LINHA;
        Cursor cursor = helper.getReadableDatabase().query(LinhaFavorita.TABLE_NAME, colunas, whereClause, whereArgs, null, null, orderBy);

        List<LinhaFavorita> linhaFavoritas = new ArrayList<>();

        while (cursor.moveToNext()) {
            linhaFavoritas.add(new LinhaFavorita(cursor));
        }

        return linhaFavoritas;
    }

}
