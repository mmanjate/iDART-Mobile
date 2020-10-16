package mz.org.fgh.idartlite.common;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.util.Utilities;

public class ListableSpinnerAdapter extends ArrayAdapter {

    private Context context;
    private List<Listble> dataList;
    LayoutInflater inflater;
    Activity activity;

    private ArrayList<Listble> suggestions;

    public ListableSpinnerAdapter(@NonNull Activity activity, int textViewResourceId, List dataList) {
        super(activity, textViewResourceId, dataList);
        this.context = activity.getApplicationContext();
        this.dataList = dataList;
        inflater = activity.getLayoutInflater();
        this.activity = activity;
        this.suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simple_auto_complete_item, parent, false);
        }
        TextView label = convertView.findViewById(R.id.label);
        ImageView icon = convertView.findViewById(R.id.item_icon);

        if (dataList.get(position) instanceof Clinic){
            if (Utilities.stringHasValue(((Listble)dataList.get(position)).getDescription())) {
                icon.setImageResource(R.drawable.ic_add_clinic);
            }
        }else if (dataList.get(position) instanceof Drug) {
            icon.setImageResource(R.mipmap.ic_drug);
        }
        label.setText(((Listble)dataList.get(position)).getDescription());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.simple_auto_complete_item,parent, false);
        }

        TextView label = convertView.findViewById(R.id.label);
        ImageView icon = convertView.findViewById(R.id.item_icon);

        if (dataList.get(position) instanceof Clinic){
            icon.setImageResource(R.drawable.ic_add_clinic);
        }else if (dataList.get(position) instanceof Drug) {
            icon.setImageResource(R.mipmap.ic_drug);
        }


        label.setText(((Listble)dataList.get(position)).getDescription());
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (Listble listble : dataList) {
                    if(listble.getDescription().toLowerCase().contains(constraint.toString().toLowerCase())){
                        suggestions.add(listble);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            ArrayList<Listble> filteredList = (ArrayList<Listble>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (Listble c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
