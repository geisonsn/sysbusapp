package br.com.gsn.sysbusapp.model.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.gsn.sysbusapp.model.LinhaFavoritaDTO;

/**
 * Created by Geison on 30/08/2015.
 */
public class LinhaFavorita {

    public static String TABLE_NAME = "linha_favorita";
    public static String ID = "_id";
    public static String ID_LINHA = "id_linha";
    public static String NUMERO_LINHA = "numero_linha";
    public static String DESCRICAO_LINHA = "descricao_linha";
    public static String EMPRESA = "empresa";

    public static final String[] COLUNAS = new String[] {
        ID, ID_LINHA, NUMERO_LINHA, DESCRICAO_LINHA, EMPRESA
    };

    private Integer id;
    private Integer idLinha;
    private String numeroLinha;
    private String descricaoLinha;
    private String empresa;

    public LinhaFavorita() {}

    public LinhaFavorita(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.idLinha = cursor.getInt(1);
        this.numeroLinha = cursor.getString(2);
        this.descricaoLinha = cursor.getString(3);
        this.empresa = cursor.getString(4);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(Integer idLinha) {
        this.idLinha = idLinha;
    }

    public String getNumeroLinha() {
        return numeroLinha;
    }

    public void setNumeroLinha(String numeroLinha) {
        this.numeroLinha = numeroLinha;
    }

    public String getDescricaoLinha() {
        return descricaoLinha;
    }

    public void setDescricaoLinha(String descricaoLinha) {
        this.descricaoLinha = descricaoLinha;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public static ContentValues getContentValues(LinhaFavoritaDTO linhaFavorita) {
        ContentValues values = new ContentValues();
        values.put(LinhaFavorita.ID, linhaFavorita.getIdLinhaFavorita());
        values.put(LinhaFavorita.ID_LINHA, linhaFavorita.getIdLinha());
        values.put(LinhaFavorita.NUMERO_LINHA, linhaFavorita.getNumeroLinha());
        values.put(LinhaFavorita.DESCRICAO_LINHA, linhaFavorita.getDescricaoLinha());
        values.put(LinhaFavorita.EMPRESA, linhaFavorita.getEmpresaLinha());
        return values;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(LinhaFavorita.ID, this.getId());
        values.put(LinhaFavorita.ID_LINHA, this.getIdLinha());
        values.put(LinhaFavorita.NUMERO_LINHA, this.getNumeroLinha());
        values.put(LinhaFavorita.DESCRICAO_LINHA, this.getDescricaoLinha());
        values.put(LinhaFavorita.EMPRESA, this.getEmpresa());
        return values;
    }

    /**
     * Usado na criação da tabela
     * @param db
     */
    public static void onCreate(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer()
            .append(" CREATE TABLE " + LinhaFavorita.TABLE_NAME + "( ")
            .append(LinhaFavorita.ID + " INTEGER,  ")
            .append(LinhaFavorita.ID_LINHA + " INTEGER,  ")
            .append(LinhaFavorita.NUMERO_LINHA + " TEXT, ")
            .append(LinhaFavorita.DESCRICAO_LINHA + " TEXT, ")
            .append(LinhaFavorita.EMPRESA + " TEXT) ");
        db.execSQL(sql.toString());
    }
}
