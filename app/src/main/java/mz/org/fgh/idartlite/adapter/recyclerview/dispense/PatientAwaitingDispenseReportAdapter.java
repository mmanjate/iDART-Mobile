package mz.org.fgh.idartlite.adapter.recyclerview.dispense;

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
import mz.org.fgh.idartlite.databinding.ContentAwaitingPatientsReportBinding;
import mz.org.fgh.idartlite.databinding.ContentDispensesReportBinding;
import mz.org.fgh.idartlite.databinding.ItemLoadingBinding;
import mz.org.fgh.idartlite.databinding.PatientDispenseRowBinding;
import mz.org.fgh.idartlite.model.Dispense;

public class PatientAwaitingDispenseReportAdapter  extends AbstractRecycleViewAdapter<Dispense> {

    private Activity activity;
    private List<Dispense> dispenseList;

    public PatientAwaitingDispenseReportAdapter(RecyclerView recyclerView, List<Dispense> dispenseList, Activity activity) {
        super(recyclerView, dispenseList, activity);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ContentDispensesReportBinding contentDispensesReportBinding;
        ItemLoadingBinding itemLoadingBinding;


        if (viewType == VIEW_TYPE_ITEM) {
            ContentAwaitingPatientsReportBinding contentAwaitingPatientsReportBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.content_awaiting_patients_report, parent, false);
            return new DispenseViewHolder(contentAwaitingPatientsReportBinding);
        }  else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new PatientAwaitingDispenseReportAdapter.LoadingViewHolder(itemLoadingBinding);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof PatientAwaitingDispenseReportAdapter.DispenseViewHolder){
            Dispense dispense = (Dispense) records.get(position);
            ((PatientAwaitingDispenseReportAdapter.DispenseViewHolder) viewHolder).contentAwaitingPatientsReportBinding.setDispense(dispense);
        }else
        if (viewHolder instanceof PatientAwaitingDispenseReportAdapter.LoadingViewHolder){
            showLoadingView((PatientAwaitingDispenseReportAdapter.LoadingViewHolder) viewHolder, position);
        }
    }


    public class DispenseViewHolder extends RecyclerView.ViewHolder{

        private ContentAwaitingPatientsReportBinding contentAwaitingPatientsReportBinding;


        public DispenseViewHolder(@NonNull ContentAwaitingPatientsReportBinding contentAwaitingPatientsReportBinding) {
            super(contentAwaitingPatientsReportBinding.getRoot());
            this.contentAwaitingPatientsReportBinding = contentAwaitingPatientsReportBinding;
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

    protected void showLoadingView(PatientAwaitingDispenseReportAdapter.LoadingViewHolder viewHolder, int position) {}
}
