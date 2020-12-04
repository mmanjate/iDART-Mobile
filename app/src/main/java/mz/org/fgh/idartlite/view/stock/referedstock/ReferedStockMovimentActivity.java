package mz.org.fgh.idartlite.view.stock.referedstock;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.ListbleRecycleViewAdapter;
import mz.org.fgh.idartlite.adapter.spinner.listable.ListableSpinnerAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityReferedStockMovimentBinding;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.stock.ReferedStockMovimentVM;

public class ReferedStockMovimentActivity extends BaseActivity {

    private ActivityReferedStockMovimentBinding referedStockMovimentBinding;
    private ListableSpinnerAdapter drugArrayAdapter;
    private ListableSpinnerAdapter operationTypeArrayAdapter;

    private ListbleRecycleViewAdapter listbleRecycleViewAdapter;
    private RecyclerView rcvReferedStockMoviment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        referedStockMovimentBinding = DataBindingUtil.setContentView(this, R.layout.activity_refered_stock_moviment);

        referedStockMovimentBinding.setViewModel(getRelatedViewModel());

        populateSpinners();

        rcvReferedStockMoviment = referedStockMovimentBinding.rcvSelectedDrugs;

        referedStockMovimentBinding.registrationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(ReferedStockMovimentActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        getRelatedViewModel().setRegistrationDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        referedStockMovimentBinding.expireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(ReferedStockMovimentActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        getRelatedViewModel().setExpireDate(DateUtilities.createDate(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, DateUtilities.DATE_FORMAT));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        referedStockMovimentBinding.autCmpDrugs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                getRelatedViewModel().setSelectedDrug((Listble) adapterView.getItemAtPosition(pos));
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utilities.listHasElements(getRelatedViewModel().getReferedStockMovimentList())) displayReferedStockMoviments();
    }

    public void populateSpinners(){
        drugArrayAdapter = new ListableSpinnerAdapter(this, R.layout.simple_auto_complete_item, getRelatedViewModel().getDrugList());
        referedStockMovimentBinding.autCmpDrugs.setAdapter(drugArrayAdapter);
        referedStockMovimentBinding.autCmpDrugs.setThreshold(1);

        operationTypeArrayAdapter = new ListableSpinnerAdapter(this, R.layout.simple_auto_complete_item, getRelatedViewModel().getOperationTypeList());
        referedStockMovimentBinding.setOperationTypeAdapter(operationTypeArrayAdapter);
        referedStockMovimentBinding.spnOperationType.setAdapter(operationTypeArrayAdapter);
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(ReferedStockMovimentVM.class);
    }

    @Override
    public ReferedStockMovimentVM getRelatedViewModel() {
        return (ReferedStockMovimentVM) super.getRelatedViewModel();
    }

    public void changeFormSectionVisibility(View view) {
        if (view.equals(referedStockMovimentBinding.initialData)){
            if (referedStockMovimentBinding.initialDataLyt.getVisibility() == View.VISIBLE){
                referedStockMovimentBinding.ibtnInitialData.animate().setDuration(200).rotation(180);
                Utilities.collapse(referedStockMovimentBinding.initialDataLyt);
            }else {
                referedStockMovimentBinding.ibtnInitialData.animate().setDuration(200).rotation(0);
                Utilities.expand(referedStockMovimentBinding.initialDataLyt);
            }
        }else if (view.equals(referedStockMovimentBinding.txvDrugs)){
            if (referedStockMovimentBinding.drugsDataLyt.getVisibility() == View.VISIBLE){
                referedStockMovimentBinding.ibtnDrugs.animate().setDuration(200).rotation(180);
                Utilities.collapse(referedStockMovimentBinding.drugsDataLyt);
            }else {
                referedStockMovimentBinding.ibtnDrugs.animate().setDuration(200).rotation(0);
                Utilities.expand(referedStockMovimentBinding.drugsDataLyt);
            }
        }
    }

    public void displayReferedStockMoviments(){
        if (listbleRecycleViewAdapter != null) {
            listbleRecycleViewAdapter.notifyDataSetChanged();
        }else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rcvReferedStockMoviment.setLayoutManager(mLayoutManager);
            rcvReferedStockMoviment.setItemAnimator(new DefaultItemAnimator());
            rcvReferedStockMoviment.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));


            listbleRecycleViewAdapter = new ListbleRecycleViewAdapter(rcvReferedStockMoviment, getRelatedViewModel().getReferedStockMovimentList(), this);
            rcvReferedStockMoviment.setAdapter(listbleRecycleViewAdapter);
        }
    }
}