package mz.org.fgh.idartlite.view.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.databinding.PacinteDetailsFragmentBinding;
import mz.org.fgh.idartlite.model.Patient;

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
    return pacinteDetailsFragmentBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PatientDetailsFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });*/
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    public PatientActivity getMyActivity(){
        return (PatientActivity) getActivity();
    }

    private Patient getSelectedPatient(){
        return getMyActivity().getPatient();
    }
}