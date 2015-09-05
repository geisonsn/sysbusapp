package br.com.gsn.sysbusapp.abstraction;

import android.support.v4.app.ListFragment;

/**
 * Created by Geison on 05/09/2015.
 */
public abstract class ListContentFragment extends ListFragment implements TaskCancelable {

    @Override
    public void onStart() {
        super.onStart();
        Host activity = (Host) getActivity();
        activity.seCurrentFragment(this);
    }
}
