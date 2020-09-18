package mz.org.fgh.idartlite.view.patient;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.common.RecyclerTouchListener;
import mz.org.fgh.idartlite.databinding.FragmentDispenseBinding;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.adapter.DispenseAdapter;
import mz.org.fgh.idartlite.viewmodel.DispenseVM;

public class DispenseFragment extends GenericFragment {

    public DispenseFragment() {
        // Required empty public constructor
    }



    private RecyclerView rcvDispences;
    private List<Dispense> dispenseList;

    private FragmentDispenseBinding fragmentDispenseBinding;
    private DispenseAdapter dispenseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentDispenseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dispense, container,false);
        return fragmentDispenseBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.rcvDispences = fragmentDispenseBinding.rcvDispenses;

        try {
            this.dispenseList = getRelatedViewModel().gatAllOfPatient(getSelectedPatient());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (Utilities.listHasElements(dispenseList)) {
            dispenseAdapter = new DispenseAdapter(rcvDispences, this.dispenseList, getMyActivity());
            displayDataOnRecyclerView(rcvDispences, dispenseAdapter, getContext());
        }

    }

    /*private void displayPrescriptions() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcvDispences.setLayoutManager(mLayoutManager);
        rcvDispences.setItemAnimator(new DefaultItemAnimator());
        rcvDispences.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        dispenseAdapter = new DispenseAdapter(rcvDispences, this.dispenseList, getMyActivity());
        rcvDispences.setAdapter(dispenseAdapter);

        rcvDispences.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rcvDispences, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(getContext(), "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getContext(), "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
    }*/

    public PatientActivity getMyActivity(){
        return (PatientActivity) getActivity();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(DispenseVM.class);
    }

    @Override
    public DispenseVM getRelatedViewModel() {
        return (DispenseVM) super.getRelatedViewModel();
    }

    private Patient getSelectedPatient(){
        return getMyActivity().getPatient();
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }
}