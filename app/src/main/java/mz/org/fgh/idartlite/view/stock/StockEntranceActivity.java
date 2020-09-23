package mz.org.fgh.idartlite.view.stock;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.common.ListbleAdapter;
import mz.org.fgh.idartlite.databinding.ActivityStockEntranceBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.service.DrugService;
import mz.org.fgh.idartlite.service.StockService;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.StockEntranceVM;

public class StockEntranceActivity extends BaseActivity {

    private ActivityStockEntranceBinding stockEntranceBinding;
    private DrugService drugService;
    private StockService stockService;
    private List<Drug> drugList;
    private List<Listble> selectedStock;
    private RecyclerView rcvSelectedDrugs;
    private ListbleAdapter listbleAdapter;
    private Drug drug;
    private Stock stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockEntranceBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock_entrance);
        drugService = new DrugService(getApplication(), getCurrentUser());
        stockService = new StockService(getApplication(), getCurrentUser());
        rcvSelectedDrugs = stockEntranceBinding.rcvSelectedDrugs;
        selectedStock = new ArrayList<>();
        stock = new Stock();
        drug = new Drug();

        stockEntranceBinding.drugsDataLyt.setVisibility(View.GONE);
        getRelatedViewModel().setInitialDataVisible(true);
        getRelatedViewModel().setDrugDataVisible(false);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                getRelatedViewModel().setClinic((Clinic) bundle.getSerializable("clinic"));
                stockEntranceBinding.setClinic(getRelatedViewModel().getClinic());
                if (getRelatedViewModel().getClinic() == null){
                    throw new RuntimeException("Não foi seleccionado uma clinic para detalhar.");
                }
            }
        }

        enventInitialization();

        try {
            populateDrugList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateDrugList() throws SQLException {
        drugList = drugService.getDrugListAll();
        ArrayAdapter<Drug> adapter = new ArrayAdapter<Drug>(getApplicationContext(), android.R.layout.simple_spinner_item, drugList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stockEntranceBinding.spnDrugs.setAdapter(adapter);
    }

    public void enventInitialization(){
        stockEntranceBinding.initialData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityToInitialData(view);
            }
        });

        stockEntranceBinding.txvDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeVisibilityToInitialData(view);
            }
        });

        stockEntranceBinding.saveAndContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveStock();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        stockEntranceBinding.dataEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(StockEntranceActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        stockEntranceBinding.dataEntrada.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        stockEntranceBinding.dataValidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(StockEntranceActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        stockEntranceBinding.dataValidade.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        stockEntranceBinding.imvAddSelectedDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedStock == null) selectedStock = new ArrayList<>();

                if ( stockEntranceBinding.spnDrugs.getSelectedItem() != null){
                    if(stockEntranceBinding.dataValidade.getText().length() != 0 &&
                            stockEntranceBinding.dataEntrada.getText().length() != 0 &&
                            stockEntranceBinding.numeroLote.getText().length() != 0 &&
                            stockEntranceBinding.numeroGuia.getText().length() != 0 &&
                            stockEntranceBinding.numeroPreco.getText().length() != 0 &&
                            stockEntranceBinding.numeroQuantidadeRecebida.getText().length() != 0){

                        if(DateUtilitis.dateDiff(DateUtilitis.createDate(stockEntranceBinding.dataValidade.getText().toString(), DateUtilitis.DATE_FORMAT),
                                DateUtilitis.createDate(stockEntranceBinding.dataEntrada.getText().toString(), DateUtilitis.DATE_FORMAT), DateUtilitis.DAY_FORMAT) > 0){
                            drug = (Drug) stockEntranceBinding.spnDrugs.getSelectedItem();
                            stock.setDrug(drug);
                            stock.setExpiryDate(DateUtilitis.createDate(stockEntranceBinding.dataValidade.getText().toString(), DateUtilitis.DATE_FORMAT));
                            stock.setDateReceived(DateUtilitis.createDate(stockEntranceBinding.dataEntrada.getText().toString(), DateUtilitis.DATE_FORMAT));
                            stock.setBatchNumber(stockEntranceBinding.numeroLote.getText().toString());
                            stock.setOrderNumber(stockEntranceBinding.numeroGuia.getText().toString());
                            stock.setPrice(Double.valueOf(stockEntranceBinding.numeroPreco.getText().toString()));
                            stock.setUnitsReceived(Integer.valueOf(stockEntranceBinding.numeroQuantidadeRecebida.getText().toString()));
                            stock.setClinic(getCurrentClinic());

                            Listble listble = (Listble) stock;

                            if (!selectedStock.contains(listble)) {
                                listble.setListPosition(selectedStock.size()+1);
                                selectedStock.add(listble);
                                Collections.sort(selectedStock);

                                displaySelectedDrugs();
                            }else {

                                Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.drug_data_duplication_msg)).show();
                            }
                        }else {
                            Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.drug_validate_date)).show();
                        }
                    }else {
                        Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.drug_data_empty_filds)).show();
                    }
                }
            }
        });
    }

    private void saveStock() throws SQLException {
        if(!this.selectedStock.isEmpty()){
            for (Listble listble : this.selectedStock){
                stockService.saveStock((Stock) listble);
            }
            finish();
        }else{
            Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.drug_data_empty_filds)).show();
        }
    }

    private void displaySelectedDrugs(){
        if (listbleAdapter != null) {
            listbleAdapter.notifyDataSetChanged();
        }else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rcvSelectedDrugs.setLayoutManager(mLayoutManager);
            rcvSelectedDrugs.setItemAnimator(new DefaultItemAnimator());
            rcvSelectedDrugs.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

            listbleAdapter = new ListbleAdapter(rcvSelectedDrugs, this.selectedStock, this);
            rcvSelectedDrugs.setAdapter(listbleAdapter);
        }

    }

    private void changeVisibilityToInitialData(View view) {
        if (view.equals(stockEntranceBinding.initialData)){
            if (stockEntranceBinding.initialDataLyt.getVisibility() == View.VISIBLE){
                stockEntranceBinding.initialDataLyt.setVisibility(View.GONE);
                switchLayout();
            }else {
                stockEntranceBinding.initialDataLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }else if (view.equals(stockEntranceBinding.txvDrugs)){
            if (stockEntranceBinding.drugsDataLyt.getVisibility() == View.VISIBLE){
                stockEntranceBinding.drugsDataLyt.setVisibility(View.GONE);
                switchLayout();
            }else {
                stockEntranceBinding.drugsDataLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }
    }

    private void switchLayout(){
        getRelatedViewModel().setInitialDataVisible(!getRelatedViewModel().isInitialDataVisible());
        getRelatedViewModel().setDrugDataVisible(!getRelatedViewModel().isDrugDataVisible());
    }

    public Clinic getClinic(){
        return getRelatedViewModel().getClinic();
    }

    @Override
    public StockEntranceVM getRelatedViewModel(){
        return (StockEntranceVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(StockEntranceVM.class);
    }
}