package br.com.gsn.sysbusapp.util;

import java.util.regex.Pattern;

/**
 * Created by Geison on 12/08/2015.
 */
public final class RegexValidatorUtil {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PLACA_PATTERN = "([a-zA-Z]{3})-(\\\\d{4})";

    private RegexValidatorUtil() {}

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }

    public static boolean isValidPlaca(String placa) {
        return Pattern.compile(PLACA_PATTERN).matcher(placa).matches();
    }
}
