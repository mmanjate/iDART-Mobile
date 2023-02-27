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
import mz.org.fgh.idartlite.databinding.ContentStockListBinding;
import mz.org.fgh.idartlite.databinding.ItemLoadingBinding;
import mz.org.fgh.idartlite.model.DrugReportModel;

public class StockListAdapter extends AbstractRecycleViewAdapter<DrugReportModel> {

    public StockListAdapter(RecyclerView recyclerView, List<DrugReportModel> stockList, Activity activity) {
        super(recyclerView, stockList, activity);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContentStockListBinding contentStockListBinding;
        ItemLoadingBinding itemLoadingBinding;

        if (viewType == VIEW_TYPE_ITEM) {
            contentStockListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.content_stock_list, parent, false);
            return new StockListViewHolder(contentStockListBinding);
        }else if (viewType == VIEW_TYPE_LOADING) {
            itemLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_loading, parent, false);
            return new LoadingViewHolder(itemLoadingBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof StockListViewHolder){
            ((StockListViewHolder) viewHolder).contentStockListBinding.setStock(records.get(position));
        }else
        if (viewHolder instanceof LoadingViewHolder){
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    public class StockListViewHolder extends RecyclerView.ViewHolder{

        private ContentStockListBinding contentStockListBinding;

        public StockListViewHolder(@NonNull ContentStockListBinding contentStockListBinding) {
            super(contentStockListBinding.getRoot());
            this.contentStockListBinding = contentStockListBinding;
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
