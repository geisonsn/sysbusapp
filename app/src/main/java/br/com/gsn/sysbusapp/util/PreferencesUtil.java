package br.com.gsn.sysbusapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Geison on 22/09/2015.
 */
public final class PreferencesUtil {

    private static PreferencesUtil preferenceUtil = null;
    private SharedPreferences preferences;

    public static final String ID_USUARIO = "idUsuario";
    public static final String NOME_USUARIO = "nomeUsuario";
    public static final String EMAIL_USUARIO = "emailUsuario";
    public static final String DISTANCIA = "distancia";
    public static final String INTERVALO = "intervalo";
    private static final String MENU_CORRENTE = "menuCorrente";
    public static final String PESQUISA_LOCALIZACAO = "pesquisaLocalizacao";
    public static final String MOSTRAR_LOCALIZACAO_TODOS = "T";
    public static final String MOSTRAR_LOCALIZACAO_PROXIMOS = "P";

    private PreferencesUtil(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.init();
    }

    public synchronized static PreferencesUtil getInstance(Context context) {
        if (preferenceUtil == null) {
            preferenceUtil = new PreferencesUtil(context);
        }
        return preferenceUtil;
    }

    private void init() {
        String distancia = this.preferences.getString(DISTANCIA, null);
        if (distancia == null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(INTERVALO, "3");
            editor.putString(DISTANCIA, "3");
            editor.commit();
        }
    }

    public void restaurarPreferencias() {
        this.limparDadosUsuario();
        this.restaurarConfiguracoes();
    }

    private void restaurarConfiguracoes() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(INTERVALO, "3");
        editor.putString(DISTANCIA, "3");
        editor.commit();
    }

    public String getString(String name) {
        return this.preferences.getString(name, "0");
    }

    public Integer getInt(String name) {
        return this.preferences.getInt(name, -1);
    }

    public Long getLong(String name) {
        return this.preferences.getLong(name, -1);
    }

    public Long getIdUsuario() {
        return this.preferences.getLong(ID_USUARIO, -1);
    }

    public void set(String name, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public void set(String name, Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public void set(String name, Long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(name, value);
        editor.commit();
    }

    public void set(String name, Integer value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public boolean isUsuarioLogado() {
        Long idUsuario = this.preferences.getLong(ID_USUARIO, 0);
        return idUsuario > 0;
    }

    public void setMenuCorrente(int indice) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(MENU_CORRENTE, indice);
        editor.commit();
    }

    public int getMenuCorrente() {
        return this.preferences.getInt(MENU_CORRENTE, -1);
    }

    public void setPesquisaLinha(final String pesquisa) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PESQUISA_LOCALIZACAO, pesquisa);
        editor.commit();
    }

    public String getPesquisaLinha() {
        return this.preferences.getString(PESQUISA_LOCALIZACAO, MOSTRAR_LOCALIZACAO_TODOS);
    }

    public void limparDadosUsuario() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(ID_USUARIO, 0);
        editor.putString(NOME_USUARIO, "");
        editor.putString(EMAIL_USUARIO, "");
        editor.commit();
    }
}
