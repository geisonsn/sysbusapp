package br.com.gsn.sysbusapp.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.gsn.sysbusapp.cache.Linha;
import br.com.gsn.sysbusapp.cache.LinhaFavorita;


/**
 * Created by Geison on 29/08/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper dataBaseHelper;

    private static final String DATA_BASE = "sysbus";
    private static final int VERSAO = 1;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DatabaseHelper(context);
        }
        return dataBaseHelper;
    }

    private DatabaseHelper(Context context) {
        super(context, DATA_BASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Linha.onCreate(db);
        LinhaFavorita.onCreate(db);
        //TODO implementar table linha_favorita e origem_reclamacao
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
