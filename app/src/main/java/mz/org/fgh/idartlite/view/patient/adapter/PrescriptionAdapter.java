package mz.org.fgh.idartlite.view.patient.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.databinding.PatientPrescriptionRowBinding;
import mz.org.fgh.idartlite.model.Prescription;

public class PrescriptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Prescription> prescriptionList;

    public PrescriptionAdapter(RecyclerView recyclerView, List<Prescription> prescriptionList, Activity activity) {
        this.activity = activity;
        this.prescriptionList = prescriptionList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PatientPrescriptionRowBinding patientPrescriptionRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.patient_prescription_row, parent, false);
        return new PrescriptionViewHolder(patientPrescriptionRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((PrescriptionViewHolder) viewHolder).patientPrescriptionRowBinding.setPrescription(prescriptionList.get(position));
    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }

    public class PrescriptionViewHolder extends RecyclerView.ViewHolder{

        private PatientPrescriptionRowBinding patientPrescriptionRowBinding;


        public PrescriptionViewHolder(@NonNull PatientPrescriptionRowBinding patientPrescriptionRowBinding) {
            super(patientPrescriptionRowBinding.getRoot());
            this.patientPrescriptionRowBinding = patientPrescriptionRowBinding;
        }
    }
}
