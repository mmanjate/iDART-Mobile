package mz.org.fgh.idartlite.adapter.recyclerview.report;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.generic.AbstractRecycleViewAdapter;
import mz.org.fgh.idartlite.databinding.ContentAwaitingPatientsReportBinding;
import mz.org.fgh.idartlite.databinding.ContentDispensesReportBinding;
import mz.org.fgh.idartlite.databinding.ContentPatientsAwaitingStatisticsBinding;
import mz.org.fgh.idartlite.databinding.ItemLoadingBinding;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.TherapeuticLine;

public class PatientAwaitingStatisticDispenseReportAdapter extends AbstractRecycleViewAdapter<Dispense> {

    public PatientAwaitingStatisticDispenseReportAdapter(RecyclerView recyclerView, List dispenseList, Activity activity) {
        super(recyclerView, dispenseList, activity);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ContentDispensesReportBinding contentDispensesReportBinding;
        ItemLoadingBinding itemLoadingBinding;


        if (viewType == VIEW_TYPE_ITEM) {
            ContentPatientsAwaitingStatisticsBinding contentPatientsAwaitingStatisticsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.content_patients_awaiting_statistics, parent, false);
            return new DispenseViewHolder(contentPatientsAwaitingStatisticsBinding);
        }  else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new PatientAwaitingStatisticDispenseReportAdapter.LoadingViewHolder(itemLoadingBinding);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof PatientAwaitingStatisticDispenseReportAdapter.DispenseViewHolder){

            HashMap map = (HashMap) records.toArray()[position];

            ((PatientAwaitingStatisticDispenseReportAdapter.DispenseViewHolder) viewHolder).contentPatientsAwaitingStatisticsBinding.setDispenseStatistic(map);
        }else
        if (viewHolder instanceof PatientAwaitingStatisticDispenseReportAdapter.LoadingViewHolder){
            showLoadingView((PatientAwaitingStatisticDispenseReportAdapter.LoadingViewHolder) viewHolder, position);
        }
    }


    public class DispenseViewHolder extends RecyclerView.ViewHolder{

        private ContentPatientsAwaitingStatisticsBinding contentPatientsAwaitingStatisticsBinding;


        public DispenseViewHolder(@NonNull ContentPatientsAwaitingStatisticsBinding contentPatientsAwaitingStatisticsBinding) {
            super(contentPatientsAwaitingStatisticsBinding.getRoot());
            this.contentPatientsAwaitingStatisticsBinding = contentPatientsAwaitingStatisticsBinding;
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

    protected void showLoadingView(PatientAwaitingStatisticDispenseReportAdapter.LoadingViewHolder viewHolder, int position) {}
}
