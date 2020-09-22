package mz.org.fgh.idartlite.common;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.databinding.ListableItemBinding;
import mz.org.fgh.idartlite.util.Utilities;

public class ListbleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Listble> listbles;
    private RecyclerView recyclerView;

    public ListbleAdapter(RecyclerView recyclerView, List<Listble> listbles, Activity activity) {
        this.activity = activity;
        this.listbles = listbles;
        this.recyclerView = recyclerView;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListableItemBinding listableItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.listable_item, parent, false);
        return new ListbleViewHolder(listableItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((ListbleViewHolder) viewHolder).listableItemBinding.setListble(listbles.get(position));
        ((ListbleViewHolder) viewHolder).listableItemBinding.imvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = Utilities.displayDeleteConfirmationDialog(activity.getApplicationContext(), activity.getString(R.string.list_item_delete_msg));
                
                if (result){
                    listbles.remove(listbles.get(position));
                    
                    /*for (int i = 0; i < listbles.size(); i++){
                        listbles.get(i).setListPosition(i+1);
                    }*/
                    notifyItemRemoved(position);
                    recyclerView.removeViewAt(position);
                    notifyItemRangeChanged(position, getItemCount());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listbles.size();
    }

    public class ListbleViewHolder extends RecyclerView.ViewHolder{

        private ListableItemBinding listableItemBinding;


        public ListbleViewHolder(@NonNull ListableItemBinding listableItemBinding) {
            super(listableItemBinding.getRoot());
            this.listableItemBinding = listableItemBinding;
        }
    }
}