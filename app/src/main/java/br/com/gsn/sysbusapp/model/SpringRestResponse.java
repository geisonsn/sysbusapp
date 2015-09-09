package br.com.gsn.sysbusapp.model;

import android.content.Context;

/**
 * Created by Geison on 23/08/2015.
 */
public class SpringRestResponse extends AbstractSpringRestResponse {


    public SpringRestResponse(Context context, Object object, int statusCode, boolean showMessage) {
        super(context, object, statusCode, showMessage);
    }

    public SpringRestResponse(Context context, int statusCode, boolean showMessage) {
        super(context, statusCode, showMessage);
    }

}
