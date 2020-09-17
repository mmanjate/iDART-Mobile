package mz.org.fgh.idartlite.view.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.databinding.PrescriptionFragmentBinding;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.viewmodel.PrescriptionVM;

public class PrescriptionFragment extends GenericFragment {

    private RecyclerView rcvPrescriptions;
    private List<Prescription> prescriptionList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.prescription_fragment, container, false);
        PrescriptionFragmentBinding prescriptionFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.prescription_fragment, container,false);
        return prescriptionFragmentBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    public PatientActivity getMyActivity(){
        return (PatientActivity) getActivity();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(PrescriptionVM.class);
    }

    @Override
    public PrescriptionVM getRelatedViewModel() {
        return (PrescriptionVM) super.getRelatedViewModel();
    }
}