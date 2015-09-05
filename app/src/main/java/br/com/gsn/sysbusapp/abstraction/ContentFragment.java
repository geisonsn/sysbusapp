package br.com.gsn.sysbusapp.abstraction;

import android.support.v4.app.Fragment;

/**
 * Created by Geison on 05/09/2015.
 */
public abstract class ContentFragment extends Fragment implements TaskCancelable {

    @Override
    public void onStart() {
        super.onStart();
        Host activity = (Host) getActivity();
        activity.seCurrentFragment(this);
    }
}
