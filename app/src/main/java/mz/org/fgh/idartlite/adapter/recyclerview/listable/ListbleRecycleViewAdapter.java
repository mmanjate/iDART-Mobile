package mz.org.fgh.idartlite.adapter.recyclerview.listable;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.databinding.HeaderitemBinding;
import mz.org.fgh.idartlite.databinding.ListableItemBinding;
import mz.org.fgh.idartlite.listener.dialog.IListbleDialogListener;
import mz.org.fgh.idartlite.util.Utilities;

public class ListbleRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IListbleDialogListener {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private BaseActivity activity;
    private List<Listble> listbles;
    private RecyclerView recyclerView;

    public ListbleRecycleViewAdapter(RecyclerView recyclerView, List<Listble> listbles, BaseActivity activity) {
        this.activity = activity;
        this.listbles = listbles;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            // Here Inflating your recyclerview item layout
            ListableItemBinding listableItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.listable_item, parent, false);
            return new ListbleViewHolder(listableItemBinding);
        } else if (viewType == TYPE_HEADER) {
            // Here Inflating your header view
            HeaderitemBinding headerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.headeritem, parent, false);
            return new HeaderViewHolder(headerItemBinding);
        }
        else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            if (listbles.size() != 0) {
                headerViewHolder.headeritemBinding.setListble(listbles.get(position));
            }
            headerViewHolder.headeritemBinding.setViewListEditButton(activity.isViewListEditButton());
            headerViewHolder.headeritemBinding.setViewListRemoveButton(activity.isViewListRemoveButton());
        } else if (viewHolder instanceof ListbleViewHolder) {
            ((ListbleViewHolder) viewHolder).listableItemBinding.setViewModel(activity.getRelatedViewModel());
            ((ListbleViewHolder) viewHolder).listableItemBinding.setListble(listbles.get(position - 1));
            ((ListbleViewHolder) viewHolder).listableItemBinding.setViewListEditButton(activity.isViewListEditButton());
            ((ListbleViewHolder) viewHolder).listableItemBinding.setViewListRemoveButton(activity.isViewListRemoveButton());


            ((ListbleViewHolder) viewHolder).listableItemBinding.edtQtyDestroy.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                   if (s != null && Utilities.stringHasValue(s.toString())){
                       getItemAtPosition(position).setQtyToModify(Integer.valueOf(s.toString()));
                   }
                }
            });

            ((ListbleViewHolder) viewHolder).listableItemBinding.imvRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.displayDeleteConfirmationDialogFromList(activity, activity.getString(R.string.list_item_delete_msg), position - 1, ListbleRecycleViewAdapter.this).show();
                }
            });

            ((ListbleViewHolder) viewHolder).listableItemBinding.imvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.getRelatedViewModel().setSelectedListble(listbles.get(position - 1));
                }
            });
        }
    }

    public List<Listble> getData(){
        return this.listbles;
    }

    public Listble getItemAtPosition(int position){
        if (!Utilities.listHasElements(listbles)) return null;

        return listbles.get(position-1);
    }

    public void remove(int position) {
        activity.getRelatedViewModel().setSelectedListble(listbles.get(position));
        listbles.remove(listbles.get(position));

        for (int i = 0; i < listbles.size(); i++){
            listbles.get(i).setListPosition(i+1);
        }
        notifyItemRemoved(position);
        recyclerView.removeViewAt(position);
        notifyItemRangeChanged(position, getItemCount());
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

    public List<Listble> getListbles() {
        return listbles;
    }

    @Override
    public void remove(BaseModel baseModel) { }

    public class ListbleViewHolder extends RecyclerView.ViewHolder {

        private ListableItemBinding listableItemBinding;

        public ListbleViewHolder(@NonNull ListableItemBinding listableItemBinding) {
            super(listableItemBinding.getRoot());
            this.listableItemBinding = listableItemBinding;
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private HeaderitemBinding headeritemBinding;

        public HeaderViewHolder(@NonNull HeaderitemBinding headeritemBinding) {
            super(headeritemBinding.getRoot());
            this.headeritemBinding = headeritemBinding;
        }
    }
}