package mz.org.fgh.idartlite.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.common.OnLoadMoreListener;
import mz.org.fgh.idartlite.databinding.ContentEntranceStockBinding;
import mz.org.fgh.idartlite.databinding.ContentPatientBinding;
import mz.org.fgh.idartlite.databinding.ItemLoadingBinding;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Stock;

public class StockEntranceAdapter extends AbstractRecycleViewAdapter<Stock> {

    public StockEntranceAdapter(RecyclerView recyclerView, List<Stock> stockList, Activity activity) {
        super(recyclerView, stockList, activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContentEntranceStockBinding contentEntranceStockBinding;
        ItemLoadingBinding itemLoadingBinding;

        if (viewType == VIEW_TYPE_ITEM) {
            contentEntranceStockBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.content_entrance_stock, parent, false);
            return new StockEntranceViewHolder(contentEntranceStockBinding);
        }else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemLoadingBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof StockEntranceViewHolder){
            ((StockEntranceViewHolder) viewHolder).contentEntranceStockBinding.setStock(records.get(position));
        }else
        if (viewHolder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    public class StockEntranceViewHolder extends RecyclerView.ViewHolder{

        private ContentEntranceStockBinding contentEntranceStockBinding;

        public StockEntranceViewHolder(@NonNull ContentEntranceStockBinding contentEntranceStockBinding) {
            super(contentEntranceStockBinding.getRoot());
            this.contentEntranceStockBinding = contentEntranceStockBinding;
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
