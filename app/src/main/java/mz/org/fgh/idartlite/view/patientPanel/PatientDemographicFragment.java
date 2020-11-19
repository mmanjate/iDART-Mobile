package mz.org.fgh.idartlite.view.patientPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.databinding.PacinteDetailsFragmentBinding;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.prescription.PrescriptionActivity;
import mz.org.fgh.idartlite.viewmodel.patient.PatientDemographicsVM;

public class PatientDemographicFragment extends GenericFragment {
    PacinteDetailsFragmentBinding pacinteDetailsFragmentBinding;

    public PatientDemographicFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pacinteDetailsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.pacinte_details_fragment, container,false);

        return pacinteDetailsFragmentBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getRelatedViewModel().setPatient(getSelectedPatient());
        pacinteDetailsFragmentBinding.setViewModel(getRelatedViewModel());
        getRelatedViewModel().setPatientDemographicFragment(this);
    }


    public void changeVisibilityToInitialData(View view) {
        if (view.equals(pacinteDetailsFragmentBinding.initialData)){
            if (pacinteDetailsFragmentBinding.personDataLyt.getVisibility() == View.VISIBLE){
                switchLayout();
                pacinteDetailsFragmentBinding.ibtnInitialData.animate().setDuration(200).rotation(180);
                Utilities.collapse(pacinteDetailsFragmentBinding.personDataLyt);
            }else {
                switchLayout();
                pacinteDetailsFragmentBinding.ibtnInitialData.animate().setDuration(200).rotation(0);
                Utilities.expand(pacinteDetailsFragmentBinding.personDataLyt);
            }

        }else if (view.equals(pacinteDetailsFragmentBinding.contacto)){
            if (pacinteDetailsFragmentBinding.contactDataLyt.getVisibility() == View.VISIBLE){
                switchLayout();
                pacinteDetailsFragmentBinding.ibtnContacto.animate().setDuration(200).rotation(180);
                Utilities.collapse(pacinteDetailsFragmentBinding.contactDataLyt);
            }else {
                switchLayout();
                pacinteDetailsFragmentBinding.ibtnContacto.animate().setDuration(200).rotation(0);
                Utilities.expand(pacinteDetailsFragmentBinding.contactDataLyt);
            }

        }else if (view.equals(pacinteDetailsFragmentBinding.others)){
            if (pacinteDetailsFragmentBinding.othersDataLyt.getVisibility() == View.VISIBLE){
                switchLayout();
                pacinteDetailsFragmentBinding.ibtnOthers.animate().setDuration(200).rotation(180);
                Utilities.collapse(pacinteDetailsFragmentBinding.othersDataLyt);
            }else {
                switchLayout();
                pacinteDetailsFragmentBinding.ibtnOthers.animate().setDuration(200).rotation(0);
                Utilities.expand(pacinteDetailsFragmentBinding.othersDataLyt);
            }
        }
    }

    public void startPatientActivity(){
        if (getMyActivity().getPatient().hasEndEpisode()) {
            Utilities.displayAlertDialog(PatientDemographicFragment.this.getContext(), getString(R.string.cant_edit_patient_data)).show();
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("patient", getMyActivity().getPatient());
            params.put("user", getCurrentUser());
            params.put("clinic", getMyActivity().getCurrentClinic());
            params.put("step", ApplicationStep.STEP_EDIT);
            nextActivityFinishingCurrent(AddNewPatientActivity.class, params);
        }
    }

    private void switchLayout(){
        getRelatedViewModel().setInitialDataVisible(!getRelatedViewModel().isInitialDataVisible());
        getRelatedViewModel().setContactDataVisible(!getRelatedViewModel().isContactDataVisible());
        getRelatedViewModel().setOtherDataVisible(!getRelatedViewModel().isOtherDataVisible());
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(getMyActivity()).get(PatientDemographicsVM.class);
    }

    public PatientDemographicsVM getRelatedViewModel() {
        return (PatientDemographicsVM) super.getRelatedViewModel();
    }

    public PatientPanelActivity getMyActivity(){
        return (PatientPanelActivity) getActivity();
    }

    private Patient getSelectedPatient(){
        return getMyActivity().getPatient();
    }
}