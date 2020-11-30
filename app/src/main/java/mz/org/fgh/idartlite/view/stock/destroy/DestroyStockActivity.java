package mz.org.fgh.idartlite.view.stock.destroy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.ListbleRecycleViewAdapter;
import mz.org.fgh.idartlite.adapter.spinner.listable.ListableSpinnerAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityDestroyStockBinding;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.stock.DestroyStockVM;

public class DestroyStockActivity extends BaseActivity {

    private ListableSpinnerAdapter drugArrayAdapter;

    private RecyclerView rcvSelectedDrugs;

    private ListbleRecycleViewAdapter listbleRecycleViewAdapter;

    private ActivityDestroyStockBinding destroyStockBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        destroyStockBinding = DataBindingUtil.setContentView(this, R.layout.activity_destroy_stock);

        destroyStockBinding.setViewModel(getRelatedViewModel());

        populateDrugs(getRelatedViewModel().getDrugs());

        destroyStockBinding.dispenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DestroyStockActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        getRelatedViewModel().setDestryedDrugDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        destroyStockBinding.dispenseDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(DestroyStockActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            getRelatedViewModel().setDestryedDrugDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        destroyStockBinding.autCmpDrugs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                getRelatedViewModel().setSelectedDrug((Drug) adapterView.getItemAtPosition(pos));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getApplicationStep().isApplicationstepCreate() && getRelatedViewModel().getRelatedRecord().getId() > 0) changeApplicationStepToEdit();

        if (getApplicationStep().isApplicationStepEdit() || getApplicationStep().isApplicationStepDisplay()){
            getRelatedViewModel().loadRelatedData();
        }
    }

    public void populateDrugs(List<Drug> drugs){
        drugArrayAdapter = new ListableSpinnerAdapter(this, R.layout.simple_auto_complete_item, drugs);
        destroyStockBinding.autCmpDrugs.setAdapter(drugArrayAdapter);
        destroyStockBinding.autCmpDrugs.setThreshold(1);
    }



    @Override
    public DestroyStockVM getRelatedViewModel() {
        return (DestroyStockVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(DestroyStockVM.class);
    }

    public void changeFormSectionVisibility(View view) {
        if (view.equals(destroyStockBinding.initialData)) {
            if (destroyStockBinding.initialDataLyt.getVisibility() == View.VISIBLE){
                destroyStockBinding.ibtnInitialData.animate().setDuration(200).rotation(180);
                Utilities.collapse(destroyStockBinding.initialDataLyt);
            }else {
                destroyStockBinding.ibtnInitialData.animate().setDuration(200).rotation(0);
                Utilities.expand(destroyStockBinding.initialDataLyt);
            }
        }
    }

    public void displaySelectedDrugs(){
        if (listbleRecycleViewAdapter != null) {
            listbleRecycleViewAdapter.notifyDataSetChanged();
        }else {
            rcvSelectedDrugs = destroyStockBinding.rcvSelectedDrugs;

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DestroyStockActivity.this);
            rcvSelectedDrugs.setLayoutManager(mLayoutManager);
            rcvSelectedDrugs.setItemAnimator(new DefaultItemAnimator());
            rcvSelectedDrugs.addItemDecoration(new DividerItemDecoration(DestroyStockActivity.this, 0));

            listbleRecycleViewAdapter = new ListbleRecycleViewAdapter(rcvSelectedDrugs, getRelatedViewModel().getStockList(), this);
            rcvSelectedDrugs.setAdapter(listbleRecycleViewAdapter);
        }
    }

    public List<Listble> getStockToDestroy(){
        return listbleRecycleViewAdapter.getListbles();
    }
}