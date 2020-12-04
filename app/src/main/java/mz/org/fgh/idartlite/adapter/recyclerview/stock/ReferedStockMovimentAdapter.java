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
import mz.org.fgh.idartlite.databinding.ItemLoadingBinding;
import mz.org.fgh.idartlite.databinding.ReferedstockmovimentListingRowBinding;
import mz.org.fgh.idartlite.model.ReferedStockMoviment;

public class ReferedStockMovimentAdapter extends AbstractRecycleViewAdapter<ReferedStockMoviment> {

    public ReferedStockMovimentAdapter(RecyclerView recyclerView, List<ReferedStockMoviment> records, Activity activity) {
        super(recyclerView, records, activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReferedstockmovimentListingRowBinding referedstockmovimentListingRowBinding;
        ItemLoadingBinding itemLoadingBinding;

        if (viewType == VIEW_TYPE_ITEM) {
            referedstockmovimentListingRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.referedstockmoviment_listing_row, parent, false);
            return new ReferedStockMovimentViewHolder(referedstockmovimentListingRowBinding);
        }else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemLoadingBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ReferedStockMovimentViewHolder){
            ((ReferedStockMovimentViewHolder) viewHolder).referedstockmovimentListingRowBinding.setReferedStock(records.get(position));
        }else
        if (viewHolder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

      public class ReferedStockMovimentViewHolder extends RecyclerView.ViewHolder{

        private ReferedstockmovimentListingRowBinding referedstockmovimentListingRowBinding;


        public ReferedStockMovimentViewHolder(@NonNull ReferedstockmovimentListingRowBinding referedstockmovimentListingRowBinding) {
            super(referedstockmovimentListingRowBinding.getRoot());
            this.referedstockmovimentListingRowBinding = referedstockmovimentListingRowBinding;
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
