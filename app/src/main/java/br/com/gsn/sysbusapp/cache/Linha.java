package br.com.gsn.sysbusapp.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.gsn.sysbusapp.model.LinhaDTO;

/**
 * Created by Geison on 30/08/2015.
 */
public class Linha {

    public static String TABLE_NAME = "linha";
    public static String ID = "_id";
    public static String DESCRICAO = "descricao";
    public static String NUMERO = "numero";
    public static String EMPRESA = "empresa";

    public static final String[] COLUNAS = new String[] {
        ID, DESCRICAO, NUMERO, EMPRESA
    };

    private Integer id;
    private String descricao;
    private String numero;
    private String empresa;

    public Linha() {}

    public Linha(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.descricao = cursor.getString(1);
        this.numero = cursor.getString(2);
        this.empresa = cursor.getString(3);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public static ContentValues getContentValues(LinhaDTO linha) {
        ContentValues values = new ContentValues();
        values.put(Linha.ID, linha.getIdLinha());
        values.put(Linha.DESCRICAO, linha.getDescricaoLinha());
        values.put(Linha.NUMERO, linha.getNumeroLinha());
        values.put(Linha.EMPRESA, linha.getNomeEmpresa());
        return values;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Linha.ID, this.getId());
        values.put(Linha.DESCRICAO, this.getDescricao());
        values.put(Linha.NUMERO, this.getNumero());
        values.put(Linha.EMPRESA, this.getEmpresa());
        return values;
    }

    /**
     * Usado na criação da tabela
     * @param db
     */
    public static void onCreate(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer()
                .append(" CREATE TABLE " + Linha.TABLE_NAME + "( ")
                .append(Linha.ID + " INTEGER,  ")
                .append(Linha.DESCRICAO + " TEXT, ")
                .append(Linha.NUMERO + " TEXT, ")
                .append(Linha.EMPRESA + " TEXT) ");
        db.execSQL(sql.toString());
    }
}
