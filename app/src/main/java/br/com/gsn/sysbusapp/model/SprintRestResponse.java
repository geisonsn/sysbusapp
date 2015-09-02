package br.com.gsn.sysbusapp.model;

import android.content.Context;

/**
 * Created by Geison on 23/08/2015.
 */
public class SprintRestResponse extends AbstractSpringRestResponse {


    public SprintRestResponse(Context context, Object object, int statusCode) {
        super(context, object, statusCode);
    }

    public SprintRestResponse(Context context, int statusCode) {
        super(context, statusCode);
    }

}
