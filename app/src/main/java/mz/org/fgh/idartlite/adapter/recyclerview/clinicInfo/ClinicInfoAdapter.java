package mz.org.fgh.idartlite.adapter.recyclerview.clinicInfo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.prescription.PrescriptionAdapter;
import mz.org.fgh.idartlite.databinding.PatientClinicInfoRowBinding;
import mz.org.fgh.idartlite.databinding.PatientEpisodeRowBinding;
import mz.org.fgh.idartlite.databinding.PatientPrescriptionRowBinding;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Prescription;

public class ClinicInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<ClinicInformation> clinicInformations;

    private Episode firstEpisode;
    private Dispense lastDispense;

    public ClinicInfoAdapter(RecyclerView recyclerView, List<ClinicInformation> clinicInformations, Activity activity) {
        this.activity = activity;
        this.clinicInformations = clinicInformations;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PatientClinicInfoRowBinding patientClinicInfoRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.patient_clinic_info_row, parent, false);
        return new ClinicInfoViewHolder(patientClinicInfoRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        ((ClinicInfoAdapter.ClinicInfoViewHolder) viewHolder).patientClinicInfoRowBinding.setClinicInformation(clinicInformations.get(position));
    }

    @Override
    public int getItemCount() {
        return clinicInformations.size();
    }

    public class ClinicInfoViewHolder extends RecyclerView.ViewHolder{

        private PatientClinicInfoRowBinding patientClinicInfoRowBinding;


        public ClinicInfoViewHolder(@NonNull PatientClinicInfoRowBinding patientClinicInfoRowBinding) {
            super(patientClinicInfoRowBinding.getRoot());
            this.patientClinicInfoRowBinding = patientClinicInfoRowBinding;
        }


    }


}
