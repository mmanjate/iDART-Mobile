package mz.org.fgh.idartlite.common;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import mz.org.fgh.idartlite.R;

public class ListableSpinnerAdapter extends ArrayAdapter {

    private Context context;
    private List dataList;
    LayoutInflater inflater;

    public ListableSpinnerAdapter(@NonNull Activity activity, int textViewResourceId, List dataList) {
        super(activity, textViewResourceId, dataList);
        this.context = activity.getApplicationContext();
        this.dataList = dataList;
        inflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simple_spinner_item, parent, false);
        }
        TextView label = convertView.findViewById(R.id.label);
        label.setText(((Listble)dataList.get(position)).getDescription());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.simple_spinner_item,parent, false);
        }

        TextView label = convertView.findViewById(R.id.label);
        label.setText(((Listble)dataList.get(position)).getDescription());
        return convertView;
    }
}
