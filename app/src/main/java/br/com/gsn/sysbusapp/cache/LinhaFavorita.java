package br.com.gsn.sysbusapp.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.gsn.sysbusapp.model.LinhaFavoritaDTO;
import br.com.gsn.sysbusapp.model.LocalizacaoLinhaDTO;

/**
 * Created by Geison on 30/08/2015.
 */
public class LinhaFavorita {

    public static String TABLE_NAME = "linha_favorita";
    public static String ID = "_id";
    public static String ID_USUARIO = "id_usuario";
    public static String ID_LINHA = "id_linha";
    public static String NUMERO_LINHA = "numero_linha";
    public static String DESCRICAO_LINHA = "descricao_linha";
    public static String EMPRESA = "empresa";

    public static final String[] COLUNAS = new String[] {
        ID, ID_USUARIO, ID_LINHA, NUMERO_LINHA, DESCRICAO_LINHA, EMPRESA
    };

    private Integer id;
    private Integer idUsuario;
    private Integer idLinha;
    private String numeroLinha;
    private String descricaoLinha;
    private String empresa;

    public LinhaFavorita() {}

    public LinhaFavorita(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.idUsuario = cursor.getInt(1);
        this.idLinha = cursor.getInt(2);
        this.numeroLinha = cursor.getString(3);
        this.descricaoLinha = cursor.getString(4);
        this.empresa = cursor.getString(5);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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

    public static ContentValues getContentValues(Long idUsuario, LocalizacaoLinhaDTO localizacaoLinhaDTO) {
        ContentValues values = new ContentValues();
        values.put(LinhaFavorita.ID_USUARIO, idUsuario);
        values.put(LinhaFavorita.ID_LINHA, localizacaoLinhaDTO.getIdLinha());
        values.put(LinhaFavorita.NUMERO_LINHA, localizacaoLinhaDTO.getNumeroLinha());
        values.put(LinhaFavorita.DESCRICAO_LINHA, localizacaoLinhaDTO.getDescricaoLinha());
        values.put(LinhaFavorita.EMPRESA, localizacaoLinhaDTO.getNomeEmpresa());
        return values;
    }

    public static ContentValues getContentValues(Long idUsuario, LinhaFavoritaDTO linhaFavoritaDTO) {
        ContentValues values = new ContentValues();
        values.put(LinhaFavorita.ID_USUARIO, idUsuario);
        values.put(LinhaFavorita.ID_LINHA, linhaFavoritaDTO.getIdLinha());
        values.put(LinhaFavorita.NUMERO_LINHA, linhaFavoritaDTO.getNumeroLinha());
        values.put(LinhaFavorita.DESCRICAO_LINHA, linhaFavoritaDTO.getDescricaoLinha());
        values.put(LinhaFavorita.EMPRESA, linhaFavoritaDTO.getEmpresaLinha());
        return values;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(LinhaFavorita.ID, this.getId());
        values.put(LinhaFavorita.ID_USUARIO, this.getIdUsuario());
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
            .append(LinhaFavorita.ID_USUARIO + " INTEGER,  ")
            .append(LinhaFavorita.ID_LINHA + " INTEGER,  ")
            .append(LinhaFavorita.NUMERO_LINHA + " TEXT, ")
            .append(LinhaFavorita.DESCRICAO_LINHA + " TEXT, ")
            .append(LinhaFavorita.EMPRESA + " TEXT) ");
        db.execSQL(sql.toString());
    }
}
