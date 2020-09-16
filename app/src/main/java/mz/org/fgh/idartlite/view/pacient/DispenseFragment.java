package mz.org.fgh.idartlite.view.pacient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.GenericFragment;

public class DispenseFragment extends GenericFragment {

    public DispenseFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dispense, container, false);
    }
}