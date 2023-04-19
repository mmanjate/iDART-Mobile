package mz.org.fgh.idartlite.adapter.recyclerview.dispense;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.databinding.PatientDispenseRowBinding;
import mz.org.fgh.idartlite.model.Dispense;

public class DispenseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Dispense> dispenseList;

    public DispenseAdapter(RecyclerView recyclerView, List<Dispense> dispenseList, Activity activity) {
        this.activity = activity;
        this.dispenseList = dispenseList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PatientDispenseRowBinding patientDispenseRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.patient_dispense_row, parent, false);
        return new DispenseViewHolder(patientDispenseRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((DispenseViewHolder) viewHolder).patientDispenseRowBinding.setDispense(dispenseList.get(position));
    }

    @Override
    public int getItemCount() {
        return dispenseList.size();
    }

    public class DispenseViewHolder extends RecyclerView.ViewHolder{

        private PatientDispenseRowBinding patientDispenseRowBinding;


        public DispenseViewHolder(@NonNull PatientDispenseRowBinding patientDispenseRowBinding) {
            super(patientDispenseRowBinding.getRoot());
            this.patientDispenseRowBinding = patientDispenseRowBinding;
        }
    }
}
