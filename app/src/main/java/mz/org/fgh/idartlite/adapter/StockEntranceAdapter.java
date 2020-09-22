package mz.org.fgh.idartlite.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.databinding.ContentEntranceStockBinding;
import mz.org.fgh.idartlite.model.Stock;

public class StockEntranceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Stock> stockList;

    public StockEntranceAdapter(RecyclerView recyclerView, List<Stock> stockList, Activity activity) {
        this.activity = activity;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContentEntranceStockBinding contentEntranceStockBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.content_entrance_stock, parent, false);
        return new StockEntranceViewHolder(contentEntranceStockBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((StockEntranceViewHolder) holder).contentEntranceStockBinding.setStock(stockList.get(position));
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public class StockEntranceViewHolder extends RecyclerView.ViewHolder{

        private ContentEntranceStockBinding contentEntranceStockBinding;

        public StockEntranceViewHolder(@NonNull ContentEntranceStockBinding contentEntranceStockBinding) {
            super(contentEntranceStockBinding.getRoot());
            this.contentEntranceStockBinding = contentEntranceStockBinding;
        }
    }
}
