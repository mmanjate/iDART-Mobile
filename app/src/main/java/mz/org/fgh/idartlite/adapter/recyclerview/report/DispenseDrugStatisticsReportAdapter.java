package mz.org.fgh.idartlite.adapter.recyclerview.report;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.generic.AbstractRecycleViewAdapter;
import mz.org.fgh.idartlite.databinding.ContentDispenseDrugStatisticsBinding;
import mz.org.fgh.idartlite.databinding.ContentPatientsAwaitingStatisticsBinding;
import mz.org.fgh.idartlite.databinding.ItemLoadingBinding;
import mz.org.fgh.idartlite.model.Dispense;

public class DispenseDrugStatisticsReportAdapter extends AbstractRecycleViewAdapter<Dispense> {

    public DispenseDrugStatisticsReportAdapter(RecyclerView recyclerView, List dispenseList, Activity activity) {
        super(recyclerView, dispenseList, activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemLoadingBinding itemLoadingBinding;

        if (viewType == VIEW_TYPE_ITEM) {
            ContentDispenseDrugStatisticsBinding contentDispenseDrugStatisticsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.content_dispense_drug_statistics, parent, false);
            return new DispenseViewHolder(contentDispenseDrugStatisticsBinding);
        } else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new DispenseDrugStatisticsReportAdapter.LoadingViewHolder(itemLoadingBinding);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof DispenseDrugStatisticsReportAdapter.DispenseViewHolder) {

            HashMap map = (HashMap) records.toArray()[position];

            ((DispenseDrugStatisticsReportAdapter.DispenseViewHolder) viewHolder).contentDispenseDrugStatisticsBinding.setDispenseStatistic(map);
        } else if (viewHolder instanceof DispenseDrugStatisticsReportAdapter.LoadingViewHolder) {
            showLoadingView((DispenseDrugStatisticsReportAdapter.LoadingViewHolder) viewHolder, position);
        }
    }


    public class DispenseViewHolder extends RecyclerView.ViewHolder {

        private ContentDispenseDrugStatisticsBinding contentDispenseDrugStatisticsBinding;


        public DispenseViewHolder(@NonNull ContentDispenseDrugStatisticsBinding contentDispenseDrugStatisticsBinding) {
            super(contentDispenseDrugStatisticsBinding.getRoot());
            this.contentDispenseDrugStatisticsBinding = contentDispenseDrugStatisticsBinding;
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

    protected void showLoadingView(DispenseDrugStatisticsReportAdapter.LoadingViewHolder viewHolder, int position) {
    }
}
