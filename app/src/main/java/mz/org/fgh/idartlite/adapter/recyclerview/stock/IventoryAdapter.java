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
import mz.org.fgh.idartlite.databinding.IventoryListingRowBinding;
import mz.org.fgh.idartlite.model.inventory.Iventory;

public class IventoryAdapter extends AbstractRecycleViewAdapter<Iventory> {

    public IventoryAdapter(RecyclerView recyclerView, List<Iventory> records, Activity activity) {
        super(recyclerView, records, activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IventoryListingRowBinding iventoryListingRowBinding;
        ItemLoadingBinding itemLoadingBinding;

        if (viewType == VIEW_TYPE_ITEM) {
            iventoryListingRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.iventory_listing_row, parent, false);
            return new IventoryViewHolder(iventoryListingRowBinding);
        }else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemLoadingBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof IventoryViewHolder){
            ((IventoryViewHolder) viewHolder).iventoryListingRowBinding.setIventory(records.get(position));
        }else
        if (viewHolder instanceof LoadingViewHolder){
            showLoadingView((DestroiedStockAdapter.LoadingViewHolder) viewHolder, position);
        }
    }

    public class IventoryViewHolder extends RecyclerView.ViewHolder{

        private IventoryListingRowBinding iventoryListingRowBinding;


        public IventoryViewHolder(@NonNull IventoryListingRowBinding iventoryListingRowBinding) {
            super(iventoryListingRowBinding.getRoot());
            this.iventoryListingRowBinding = iventoryListingRowBinding;
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

    protected void showLoadingView(DestroiedStockAdapter.LoadingViewHolder viewHolder, int position) {}
}
