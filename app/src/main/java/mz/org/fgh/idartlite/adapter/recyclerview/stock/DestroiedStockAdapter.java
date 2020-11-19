package mz.org.fgh.idartlite.adapter.recyclerview.stock;

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
import mz.org.fgh.idartlite.adapter.recyclerview.patient.ContentListPatientAdapter;
import mz.org.fgh.idartlite.databinding.DestroyedStockListingRowBinding;
import mz.org.fgh.idartlite.databinding.ItemLoadingBinding;
import mz.org.fgh.idartlite.model.DestroyedDrug;

public class DestroiedStockAdapter extends AbstractRecycleViewAdapter<DestroyedDrug> {

    public DestroiedStockAdapter(RecyclerView recyclerView, List<DestroyedDrug> records, Activity activity) {
        super(recyclerView, records, activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DestroyedStockListingRowBinding destroyedStockListingRowBinding;
        ItemLoadingBinding itemLoadingBinding;

        if (viewType == VIEW_TYPE_ITEM) {
            destroyedStockListingRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.destroyed_stock_listing_row, parent, false);
            return new DestroyedStockViewHolder(destroyedStockListingRowBinding);
        }else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemLoadingBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ContentListPatientAdapter.PatientViewHolder){
            ((DestroyedStockViewHolder) viewHolder).destroyedStockListingRowBinding.setDestroiedStock(records.get(position));
        }else
        if (viewHolder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    public class DestroyedStockViewHolder extends RecyclerView.ViewHolder{

        private DestroyedStockListingRowBinding destroyedStockListingRowBinding;


        public DestroyedStockViewHolder(@NonNull DestroyedStockListingRowBinding destroyedStockListingRowBinding) {
            super(destroyedStockListingRowBinding.getRoot());
            this.destroyedStockListingRowBinding = destroyedStockListingRowBinding;
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
