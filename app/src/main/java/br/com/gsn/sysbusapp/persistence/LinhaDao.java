package br.com.gsn.sysbusapp.persistence;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.gsn.sysbusapp.model.LinhaDTO;
import br.com.gsn.sysbusapp.model.cache.Linha;

/**
 * Created by Geison on 29/08/2015.
 */
public class LinhaDao {

    private DatabaseHelper helper;

    public LinhaDao(Context context) {
        this.helper = DatabaseHelper.getInstance(context);
    }

    public void insert(List<LinhaDTO> linhas) {
        for (LinhaDTO linha : linhas) {
            helper.getWritableDatabase().insert(Linha.TABLE_NAME, null, Linha.getContentValues(linha));
        }
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
