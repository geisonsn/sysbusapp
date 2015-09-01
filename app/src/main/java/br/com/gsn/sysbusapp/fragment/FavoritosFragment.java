package br.com.gsn.sysbusapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.gsn.sysbusapp.R;

/**
 * Created by Geison on 31/08/2015.
 */
public class FavoritosFragment extends Fragment {

    public FavoritosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favoritos, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }
}
