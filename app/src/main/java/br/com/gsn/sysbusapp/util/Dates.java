package br.com.gsn.sysbusapp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Geison on 19/08/2015.
 */
public final class Dates {

    /**
     * Produz uma data em string formatada conforme o formato indicado
     * @param dia
     * @param mes
     * @param ano
     * @param format formato usado na construção da data
     * @return data formatada
     */
    public static String format(int dia, int mes, int ano, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dia);
        calendar.set(Calendar.MONTH, mes);
        calendar.set(Calendar.YEAR, ano);
        return new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
    }

    public static Date parse(int dia, int mes, int ano) {
        Calendar c = Calendar.getInstance();
        c.set(ano, mes, dia);
        return c.getTime();
    }
}
