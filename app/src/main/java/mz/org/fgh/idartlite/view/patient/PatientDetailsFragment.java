package mz.org.fgh.idartlite.view.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.databinding.PacinteDetailsFragmentBinding;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.viewmodel.PatientDetailsVM;

public class PatientDetailsFragment extends GenericFragment {
    Patient patient;

    PacinteDetailsFragmentBinding pacinteDetailsFragmentBinding;

    public PatientDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pacinteDetailsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.pacinte_details_fragment, container,false);
        patient = getSelectedPatient();
        pacinteDetailsFragmentBinding.setPatient(patient);
        enventInitialization();
        return pacinteDetailsFragmentBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void enventInitialization() {
        pacinteDetailsFragmentBinding.initialData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityToInitialData(view);
            }
        });

        pacinteDetailsFragmentBinding.contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityToInitialData(view);
            }
        });

        pacinteDetailsFragmentBinding.others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityToInitialData(view);
            }
        });
    }

    private void changeVisibilityToInitialData(View view) {
        if (view.equals(pacinteDetailsFragmentBinding.initialData)){
            if (pacinteDetailsFragmentBinding.personDataLyt.getVisibility() == View.VISIBLE){
                pacinteDetailsFragmentBinding.personDataLyt.setVisibility(View.GONE);
                switchLayout();
            }else {
                pacinteDetailsFragmentBinding.personDataLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }else if (view.equals(pacinteDetailsFragmentBinding.contacto)){
            if (pacinteDetailsFragmentBinding.contactDataLyt.getVisibility() == View.VISIBLE){
                pacinteDetailsFragmentBinding.contactDataLyt.setVisibility(View.GONE);
                switchLayout();
            }else {
                pacinteDetailsFragmentBinding.contactDataLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }else if (view.equals(pacinteDetailsFragmentBinding.others)){
            if (pacinteDetailsFragmentBinding.othersDataLyt.getVisibility() == View.VISIBLE){
                pacinteDetailsFragmentBinding.othersDataLyt.setVisibility(View.GONE);
                switchLayout();
            }else {
                pacinteDetailsFragmentBinding.othersDataLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }
    }

    private void switchLayout(){
        getRelatedViewModel().setInitialDataVisible(!getRelatedViewModel().isInitialDataVisible());
        getRelatedViewModel().setContactDataVisible(!getRelatedViewModel().isContactDataVisible());
        getRelatedViewModel().setOtherDataVisible(!getRelatedViewModel().isOtherDataVisible());
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(getMyActivity()).get(PatientDetailsVM.class);
    }

    public PatientDetailsVM getRelatedViewModel() {
        return (PatientDetailsVM) super.getRelatedViewModel();
    }

    public PatientActivity getMyActivity(){
        return (PatientActivity) getActivity();
    }

    private Patient getSelectedPatient(){
        return getMyActivity().getPatient();
    }
}