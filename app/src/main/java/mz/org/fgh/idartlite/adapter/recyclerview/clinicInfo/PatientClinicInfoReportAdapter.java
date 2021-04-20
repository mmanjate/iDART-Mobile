package mz.org.fgh.idartlite.adapter.recyclerview.clinicInfo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.dispense.PatientAwaitingAbsentDispenseReportAdapter;
import mz.org.fgh.idartlite.adapter.recyclerview.generic.AbstractRecycleViewAdapter;
import mz.org.fgh.idartlite.adapter.recyclerview.report.PatientAwaitingStatisticDispenseReportAdapter;
import mz.org.fgh.idartlite.databinding.ContentAwaitingAbsentPatientsReportBinding;
import mz.org.fgh.idartlite.databinding.ContentClinicInfoReportBinding;
import mz.org.fgh.idartlite.databinding.ContentPatientsAwaitingStatisticsBinding;
import mz.org.fgh.idartlite.databinding.ItemLoadingBinding;
import mz.org.fgh.idartlite.databinding.PatientClinicInfoRowBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;

public class PatientClinicInfoReportAdapter extends AbstractRecycleViewAdapter<ClinicInformation>  {

    private Activity activity;
    private List<ClinicInformation> clinicInformations;


    public PatientClinicInfoReportAdapter(RecyclerView recyclerView, List clinicInformations, Activity activity) {
        super(recyclerView, clinicInformations, activity);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemLoadingBinding itemLoadingBinding;


        if (viewType == VIEW_TYPE_ITEM) {
            ContentClinicInfoReportBinding contentClinicInfoReportBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.content_clinic_info_report, parent, false);
            return new PatientClinicInfoReportAdapter.PatientClinicInfoReportViewHolder(contentClinicInfoReportBinding);
        }  else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new PatientClinicInfoReportAdapter.LoadingViewHolder(itemLoadingBinding);

        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof PatientClinicInfoReportAdapter.PatientClinicInfoReportViewHolder){
            ClinicInformation clinicInformation = (ClinicInformation) records.get(position);
            ((PatientClinicInfoReportAdapter.PatientClinicInfoReportViewHolder) viewHolder).contentClinicInfoReportBinding.setClinicInformation(clinicInformation);
        }else
        if (viewHolder instanceof PatientAwaitingAbsentDispenseReportAdapter.LoadingViewHolder){
            showLoadingView((PatientClinicInfoReportAdapter.LoadingViewHolder) viewHolder, position);
        }
    }


    public class PatientClinicInfoReportViewHolder extends RecyclerView.ViewHolder{

        ContentClinicInfoReportBinding contentClinicInfoReportBinding;


        public PatientClinicInfoReportViewHolder(@NonNull ContentClinicInfoReportBinding contentClinicInfoReportBinding) {
            super(contentClinicInfoReportBinding.getRoot());
            this.contentClinicInfoReportBinding = contentClinicInfoReportBinding;
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

    protected void showLoadingView(PatientClinicInfoReportAdapter.LoadingViewHolder viewHolder, int position) {
    }
}
