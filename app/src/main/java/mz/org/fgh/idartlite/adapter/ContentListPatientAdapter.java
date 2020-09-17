package mz.org.fgh.idartlite.adapter;

import android.view.ViewGroup;
import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.databinding.ContentPatientBinding;
import mz.org.fgh.idartlite.model.Patient;

public class ContentListPatientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Patient> patientList = new ArrayList<Patient>();

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Activity activity;
   // private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public ContentListPatientAdapter(RecyclerView recyclerView, List<Patient> patientList, Activity activity) {
        this.patientList = patientList;
        this.activity = activity;

        /*final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
               *//* if (!isLoading && linearLayoutManager.findLastCompletelyVisibleItemPosition() == patientList.size() - 1) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }*//*
            }
        });*/
    }

    @Override
    public int getItemViewType(int position) {
        return patientList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContentPatientBinding contentPatientBinding;
        //ItemLoadingBinding itemLoadingBinding;
        contentPatientBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.content_patient, parent, false);
        return new PatientViewHolder(contentPatientBinding);
        /*if (viewType == VIEW_TYPE_ITEM) {
            contentPatientBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.activity_search_patient, parent, false);
            return new PatientViewHolder(contentPatientBinding);
        }else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemLoadingBinding);
        }
        return null;*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PatientViewHolder) holder).contentPatientBinding.setPatient(patientList.get(position));
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setFarmaciaList(List<Patient> patientList) {
        this.patientList = patientList;
        notifyDataSetChanged();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder{

        private ContentPatientBinding contentPatientBinding;


        public PatientViewHolder(@NonNull ContentPatientBinding contentPatientBinding) {
            super(contentPatientBinding.getRoot());
            this.contentPatientBinding = contentPatientBinding;
        }
    }

   /* private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        ItemLoadingBinding itemLoadingBinding;

        public LoadingViewHolder(@NonNull ItemLoadingBinding itemLoadingBinding) {
            super(itemLoadingBinding.getRoot());
            this.itemLoadingBinding = itemLoadingBinding;
        }
    }*/

    public boolean isLoading() {
        return isLoading;
    }

    /*private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }*/

}
