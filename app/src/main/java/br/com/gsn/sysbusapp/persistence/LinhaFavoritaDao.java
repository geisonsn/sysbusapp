package br.com.gsn.sysbusapp.persistence;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.gsn.sysbusapp.cache.Linha;
import br.com.gsn.sysbusapp.cache.LinhaFavorita;
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

    public void insert(LocalizacaoLinhaDTO localizacaoLinhaDTO) {
        Long idUsuario = PreferencesUtil.getInstance(context).getIdUsuario();
        helper.getWritableDatabase().insert(Linha.TABLE_NAME, null,
                LinhaFavorita.getContentValues(idUsuario, localizacaoLinhaDTO));
    }

    public List<Linha> listLinha() {
        final String[] colunas = Linha.COLUNAS;
        final String orderBy = Linha.NUMERO;
        Cursor cursor = helper.getReadableDatabase().query(Linha.TABLE_NAME, colunas,
                null, null, null, null, orderBy);

        List<Linha> linhas = new ArrayList<>();

        while (cursor.moveToNext()) {
            linhas.add(new Linha(cursor));
        }

        return linhas;
    }

    public Integer getIdUltimaLinha() {
        Cursor cursor;
        cursor = helper.getReadableDatabase()
                .query(Linha.TABLE_NAME, new String[]{"max(" + Linha.ID + ")"}, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}
