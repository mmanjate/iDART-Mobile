package mz.org.fgh.idartlite.adapter.recyclerview.report;

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
import mz.org.fgh.idartlite.databinding.ContentReportBinding;
import mz.org.fgh.idartlite.databinding.ItemLoadingBinding;
import mz.org.fgh.idartlite.model.Report;
import mz.org.fgh.idartlite.util.Utilities;

public class ReportListAdapter extends AbstractRecycleViewAdapter<Report> {

    public ReportListAdapter(RecyclerView recyclerView, List<Report> reportList, Activity activity) {
        super(recyclerView, reportList, activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContentReportBinding contentReportBinding;
        ItemLoadingBinding itemLoadingBinding;

        if (viewType == VIEW_TYPE_ITEM) {
            contentReportBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.content_report, parent, false);
            return new ReportListAdapter.ReportViewHolder(contentReportBinding);
        }else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new ReportListAdapter.LoadingViewHolder(itemLoadingBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ReportListAdapter.ReportViewHolder){
            ((ReportListAdapter.ReportViewHolder) viewHolder).contentReportBinding.setReport(records.get(position));
            ((ReportViewHolder) viewHolder).contentReportBinding.iconReport.setImageResource(records.get(position).getIcon());
        }else
        if (viewHolder instanceof ReportListAdapter.LoadingViewHolder){
            showLoadingView((ReportListAdapter.LoadingViewHolder) viewHolder, position);
        }

    }

    public Report getItemAtPosition(int position){
        if (!Utilities.listHasElements(records)) return  null;
        return this.records.get(position);
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder{

        private ContentReportBinding contentReportBinding;

        public ReportViewHolder(@NonNull ContentReportBinding contentReportBinding) {
            super(contentReportBinding.getRoot());
            this.contentReportBinding = contentReportBinding;
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        ItemLoadingBinding itemLoadingBinding;

        public LoadingViewHolder(@NonNull ItemLoadingBinding itemLoadingBinding) {
            super(itemLoadingBinding.getRoot());
            this.itemLoadingBinding = itemLoadingBinding;
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }
}
