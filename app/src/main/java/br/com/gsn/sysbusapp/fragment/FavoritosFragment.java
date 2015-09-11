package br.com.gsn.sysbusapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.gsn.sysbusapp.R;
import br.com.gsn.sysbusapp.business.FavoritosBusiness;

/**
 * Created by Geison on 31/08/2015.
 */
public class FavoritosFragment extends ListContentFragment /*implements BusinessDelegate<BusinessTaskOperation>*/ {

//    private FavoritosBusiness delegate;

    public FavoritosFragment() {}

    @Override
    public void setBusinessDelegate() {
        this.delegate = new FavoritosBusiness(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ((FavoritosBusiness)delegate).listarLinhas();

        return rootView;
    }

    /*@Override
    public void cancelTaskOperation() {
        delegate.cancelTaskOperation();
    }*/

}
