package br.com.gsn.sysbusapp.model;

import android.content.Context;

/**
 * Created by Geison on 23/08/2015.
 */
public class SprintRestResponse extends AbstractSpringRestResponse {


    public SprintRestResponse(Context context, Object object, int statusCode, boolean showMessage) {
        super(context, object, statusCode, showMessage);
    }

    public SprintRestResponse(Context context, int statusCode, boolean showMessage) {
        super(context, statusCode, showMessage);
    }

}
