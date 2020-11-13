package mz.org.fgh.idartlite.adapter.recyclerview.patient;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.generic.AbstractRecycleViewAdapter;
import mz.org.fgh.idartlite.databinding.ContentPatientBinding;
import mz.org.fgh.idartlite.databinding.ItemLoadingBinding;
import mz.org.fgh.idartlite.model.Patient;

public class ContentListPatientAdapter extends AbstractRecycleViewAdapter<Patient> {


    public ContentListPatientAdapter(RecyclerView recyclerView, List<Patient> patientList, Activity activity) {
        super(recyclerView, patientList, activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContentPatientBinding contentPatientBinding;
        ItemLoadingBinding itemLoadingBinding;

        if (viewType == VIEW_TYPE_ITEM) {
            contentPatientBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.content_patient, parent, false);
            return new PatientViewHolder(contentPatientBinding);
        }else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemLoadingBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof PatientViewHolder){
            Patient patient = (Patient) records.get(position);
            ((PatientViewHolder) viewHolder).contentPatientBinding.setPatient(patient);
        }else
        if (viewHolder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }


    public class PatientViewHolder extends RecyclerView.ViewHolder{

        private ContentPatientBinding contentPatientBinding;


        public PatientViewHolder(@NonNull ContentPatientBinding contentPatientBinding) {
            super(contentPatientBinding.getRoot());
            this.contentPatientBinding = contentPatientBinding;
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        ItemLoadingBinding itemLoadingBinding;

        public LoadingViewHolder(@NonNull ItemLoadingBinding itemLoadingBinding) {
            super(itemLoadingBinding.getRoot());
            this.itemLoadingBinding = itemLoadingBinding;
        }
    }

    protected void showLoadingView(ContentListPatientAdapter.LoadingViewHolder viewHolder, int position) {}
}
