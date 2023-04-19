package mz.org.fgh.idartlite.adapter.recyclerview.listable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.databinding.HeaderitemFilaReportBinding;
import mz.org.fgh.idartlite.databinding.HeaderitemReportBinding;
import mz.org.fgh.idartlite.databinding.ListableItemFilaReportBinding;
import mz.org.fgh.idartlite.databinding.ListableItemReportBinding;
import mz.org.fgh.idartlite.model.Dispense;


public class ListbleReportFilaRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private BaseActivity activity;
    private List<Dispense> dispenses;
    private RecyclerView recyclerView;

    public ListbleReportFilaRecycleViewAdapter(RecyclerView recyclerView, List<Dispense> dispenses, BaseActivity activity) {
        this.activity = activity;
        this.dispenses = dispenses;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            // Here Inflating your recyclerview item layout
            ListableItemFilaReportBinding listableItemFilaReportBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.listable_item_fila_report, parent, false);
            return new ListbleViewHolder(listableItemFilaReportBinding);
        } else if (viewType == TYPE_HEADER) {
            // Here Inflating your header view
            HeaderitemFilaReportBinding headerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.headeritem_fila_report, parent, false);
            return new HeaderViewReportHolder(headerItemBinding);
        }
        else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

      if (viewHolder instanceof ListbleViewHolder) {
            ((ListbleViewHolder) viewHolder).listableItemFilaReportBinding.setListble(dispenses.get(position - 1));
        }
    }



    @Override
    public int getItemCount() {
        return dispenses.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }



    public class ListbleViewHolder extends RecyclerView.ViewHolder {

        private ListableItemFilaReportBinding listableItemFilaReportBinding;

        public ListbleViewHolder(@NonNull ListableItemFilaReportBinding listableItemFilaReportBinding) {
            super(listableItemFilaReportBinding.getRoot());
            this.listableItemFilaReportBinding = listableItemFilaReportBinding;
        }
    }

    public class HeaderViewReportHolder extends RecyclerView.ViewHolder {

        private HeaderitemFilaReportBinding headeritemBinding;

        public HeaderViewReportHolder(@NonNull HeaderitemFilaReportBinding headeritemBinding) {
            super(headeritemBinding.getRoot());
            this.headeritemBinding = headeritemBinding;
        }
    }
}