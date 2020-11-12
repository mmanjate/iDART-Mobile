package mz.org.fgh.idartlite.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.databinding.HeaderitemReportBinding;
import mz.org.fgh.idartlite.databinding.ListableItemReportBinding;
import mz.org.fgh.idartlite.model.StockReportData;

public class ListbleReportRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private BaseActivity activity;
    private List<StockReportData> listbles;
    private RecyclerView recyclerView;

    public ListbleReportRecycleViewAdapter(RecyclerView recyclerView, List<StockReportData> listbles, BaseActivity activity) {
        this.activity = activity;
        this.listbles = listbles;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            // Here Inflating your recyclerview item layout
            ListableItemReportBinding listableItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.listable_item_report, parent, false);
            return new ListbleViewHolder(listableItemBinding);
        } else if (viewType == TYPE_HEADER) {
            // Here Inflating your header view
            HeaderitemReportBinding headerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.headeritem_report, parent, false);
            return new HeaderViewReportHolder(headerItemBinding);
        }
        else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

      if (viewHolder instanceof ListbleViewHolder) {
            ((ListbleViewHolder) viewHolder).listableItemBinding.setListble(listbles.get(position - 1));
        }
    }



    @Override
    public int getItemCount() {
        return listbles.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }



    public class ListbleViewHolder extends RecyclerView.ViewHolder {

        private ListableItemReportBinding listableItemBinding;

        public ListbleViewHolder(@NonNull ListableItemReportBinding listableItemBinding) {
            super(listableItemBinding.getRoot());
            this.listableItemBinding = listableItemBinding;
        }
    }

    public class HeaderViewReportHolder extends RecyclerView.ViewHolder {

        private HeaderitemReportBinding headeritemBinding;

        public HeaderViewReportHolder(@NonNull HeaderitemReportBinding headeritemBinding) {
            super(headeritemBinding.getRoot());
            this.headeritemBinding = headeritemBinding;
        }
    }
}