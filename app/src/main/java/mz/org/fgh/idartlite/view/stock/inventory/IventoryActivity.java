package mz.org.fgh.idartlite.view.stock.inventory;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.ListbleRecycleViewAdapter;
import mz.org.fgh.idartlite.adapter.spinner.listable.ListableSpinnerAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityIventoryBinding;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.stock.InventoryVM;

public class IventoryActivity extends BaseActivity {

    private ListableSpinnerAdapter drugArrayAdapter;

    private RecyclerView rcvSelectedDrugs;

    private ListbleRecycleViewAdapter listbleRecycleViewAdapter;

    private ActivityIventoryBinding iventoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iventoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_iventory);

        List<Drug> drugs = new ArrayList<>();
        drugs.addAll(getRelatedViewModel().getDrugs());

        populateDrugs(drugs);



        iventoryBinding.setViewModel(getRelatedViewModel());

        iventoryBinding.autCmpDrugs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                getRelatedViewModel().setSelectedDrug((Drug) adapterView.getItemAtPosition(pos));
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
        if (Utilities.listHasElements(getRelatedViewModel().getAdjustmentList())) displaySelectedDrugStockAjustmentInfo();
    }


    public void populateDrugs(List<Drug> drugs){
        drugArrayAdapter = new ListableSpinnerAdapter(this, R.layout.simple_auto_complete_item, drugs);
        iventoryBinding.autCmpDrugs.setAdapter(drugArrayAdapter);
        iventoryBinding.autCmpDrugs.setThreshold(1);
    }

    public void changeFormSectionVisibility(View view) {
        if (view.equals(iventoryBinding.initialData)) {
            if (iventoryBinding.initialDataLyt.getVisibility() == View.VISIBLE){
                iventoryBinding.ibtnInitialData.animate().setDuration(200).rotation(180);
                Utilities.collapse(iventoryBinding.initialDataLyt);
            }else {
                iventoryBinding.ibtnInitialData.animate().setDuration(200).rotation(0);
                Utilities.expand(iventoryBinding.initialDataLyt);
            }
        }
    }

    public void displaySelectedDrugStockAjustmentInfo(){
        if (listbleRecycleViewAdapter != null) {
            listbleRecycleViewAdapter.notifyDataSetChanged();

        }else {
            rcvSelectedDrugs = iventoryBinding.rcvSelectedDrugs;

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(IventoryActivity.this);
            rcvSelectedDrugs.setLayoutManager(mLayoutManager);
            rcvSelectedDrugs.setItemAnimator(new DefaultItemAnimator());
            rcvSelectedDrugs.addItemDecoration(new DividerItemDecoration(IventoryActivity.this, 0));

            listbleRecycleViewAdapter = new ListbleRecycleViewAdapter(rcvSelectedDrugs, getRelatedViewModel().getAdjustmentList(), this);
            rcvSelectedDrugs.setAdapter(listbleRecycleViewAdapter);
        }

        Utilities.hideSoftKeyboard(this);
        iventoryBinding.autCmpDrugs.dismissDropDown();
    }

    public void displayResumeStockAjustmentInfo(){


            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(IventoryActivity.this);
            rcvSelectedDrugs.setLayoutManager(mLayoutManager);
            rcvSelectedDrugs.setItemAnimator(new DefaultItemAnimator());
            rcvSelectedDrugs.addItemDecoration(new DividerItemDecoration(IventoryActivity.this, 0));

            listbleRecycleViewAdapter = new ListbleRecycleViewAdapter(rcvSelectedDrugs, getRelatedViewModel().getAdjustmentList(), this);
            rcvSelectedDrugs.setAdapter(listbleRecycleViewAdapter);

    }

    @Override
    public InventoryVM getRelatedViewModel() {
        return (InventoryVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(InventoryVM.class);
    }

    public void summarizeView(int visibility) {
        iventoryBinding.lblDrugs.setVisibility(visibility);
        iventoryBinding.autCmpDrugs.setVisibility(visibility);
        iventoryBinding.lblCount.setVisibility(visibility);
        iventoryBinding.navigation.setVisibility(visibility);
        iventoryBinding.btnCloseInventory.setVisibility(visibility);
        iventoryBinding.save.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
        iventoryBinding.confirmation.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}