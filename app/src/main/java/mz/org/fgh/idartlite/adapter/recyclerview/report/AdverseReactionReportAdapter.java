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
import mz.org.fgh.idartlite.databinding.ContentAdverseReactionReportBinding;
import mz.org.fgh.idartlite.databinding.ItemLoadingBinding;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.viewmodel.report.AdverseReactionReportVM;

public class AdverseReactionReportAdapter extends AbstractRecycleViewAdapter<ClinicInformation> {

    public AdverseReactionReportAdapter(RecyclerView recyclerView, List<ClinicInformation> records, Activity activity) {
        super(recyclerView, records, activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContentAdverseReactionReportBinding contentAdverseReactionReportBinding;
        ItemLoadingBinding itemLoadingBinding;

        if (viewType == VIEW_TYPE_ITEM) {
            contentAdverseReactionReportBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.content_adverse_reaction_report, parent, false);
            return new ReportViewHolder(contentAdverseReactionReportBinding);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemLoadingBinding);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ReportViewHolder){

            ClinicInformation clinicInformation = (ClinicInformation) records.get(position);
            ((ReportViewHolder) viewHolder).contentAdverseReactionReportBinding.setClinicInformation(clinicInformation);
            String reportType = (activity.getRelatedViewModel() instanceof AdverseReactionReportVM) ? ClinicInformation.PARAM_RAM_STATUS : ClinicInformation.PARAM_FOLLOW_STATUS;

            ((ReportViewHolder) viewHolder).contentAdverseReactionReportBinding.setReportType(reportType);

        }else
        if (viewHolder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder{

        private ContentAdverseReactionReportBinding contentAdverseReactionReportBinding;


        public ReportViewHolder(@NonNull ContentAdverseReactionReportBinding contentAdverseReactionReportBinding) {
            super(contentAdverseReactionReportBinding.getRoot());
            this.contentAdverseReactionReportBinding = contentAdverseReactionReportBinding;
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

    protected void showLoadingView(LoadingViewHolder viewHolder, int position) {}
}
