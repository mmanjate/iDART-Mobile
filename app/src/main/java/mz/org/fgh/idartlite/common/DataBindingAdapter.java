package mz.org.fgh.idartlite.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

public class DataBindingAdapter {

    @BindingAdapter(value = {"selectedOpt", "selectedOptAttrChanged"}, requireAll = false)
    public static void setSelectedOpt(final AppCompatSpinner spinner,
                                      final Listble selectedOpt,
                                      final InverseBindingListener changeListener) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changeListener.onChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                changeListener.onChange();
            }
        });

        spinner.setSelection(getIndexOfItem(spinner, selectedOpt));
    }

    @InverseBindingAdapter(attribute = "selectedOpt", event = "selectedOptAttrChanged")
    public static Listble getSelectedOpt(final AppCompatSpinner spinner) {
        return (Listble)spinner.getSelectedItem();
    }

    private static int getIndexOfItem(AppCompatSpinner spinner, Listble item){
        Adapter a = spinner.getAdapter();
        if (a == null) return 0;

        for(int i=0; i<a.getCount(); i++){
            if (a.getItem(i) == null) return 0;

            if((a.getItem(i)).equals(item)){
                return i;
            }
        }
        return 0;
    }

    @BindingAdapter("android:layout_marginBottom")
    public static void setBottomMargin(View view, float bottomMargin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin,
                layoutParams.rightMargin, Math.round(bottomMargin));
        view.setLayoutParams(layoutParams);
    }
}
