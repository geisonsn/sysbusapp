package br.com.gsn.sysbusapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import br.com.gsn.sysbusapp.abstraction.BusinessDelegate;
import br.com.gsn.sysbusapp.abstraction.BusinessTaskOperation;
import br.com.gsn.sysbusapp.abstraction.HostBusinessDelegate;

/**
 * Created by Geison on 05/09/2015.
 */
public abstract class ContentFragment extends Fragment implements BusinessDelegate<BusinessTaskOperation> {

    protected BusinessTaskOperation delegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBusinessDelegate();
    }

    @Override
    public void onStart() {
        super.onStart();
        HostBusinessDelegate activity = (HostBusinessDelegate) getActivity();
        activity.seCurrentBusinessDelegate(delegate);
    }

    @Override
    public BusinessTaskOperation getBusinessDelegate() {
        return delegate;
    }
}
