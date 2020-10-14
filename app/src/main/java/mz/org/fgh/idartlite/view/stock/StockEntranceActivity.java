package mz.org.fgh.idartlite.view.stock;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.common.DialogListener;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.common.ListbleRecycleViewAdapter;
import mz.org.fgh.idartlite.databinding.ActivityStockEntranceBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.service.DispenseDrugService;
import mz.org.fgh.idartlite.service.DrugService;
import mz.org.fgh.idartlite.service.StockService;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.AboutActivity;
import mz.org.fgh.idartlite.viewmodel.StockEntranceVM;

public class StockEntranceActivity extends BaseActivity implements DialogListener {

    private ActivityStockEntranceBinding stockEntranceBinding;
    private DrugService drugService;
    private StockService stockService;
    private DispenseDrugService dispenseDrugService;
    private List<Drug> drugList;
    private List<Listble> selectedStock;
    private List<Stock> stockListEdit;
    private RecyclerView rcvSelectedDrugs;
    private ListbleRecycleViewAdapter listbleRecycleViewAdapter;
    ArrayAdapter<Drug> adapterSpinner;
    private Drug drug;
    private boolean isEditForm = false;

    private Drug selectedDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockEntranceBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock_entrance);
        drugService = new DrugService(getApplication(), getCurrentUser());
        stockService = new StockService(getApplication(), getCurrentUser());
        dispenseDrugService = new DispenseDrugService(getApplication(), getCurrentUser());
        rcvSelectedDrugs = stockEntranceBinding.rcvSelectedDrugs;
        selectedStock = new ArrayList<>();
        drug = new Drug();
        stockEntranceBinding.spnDrugs.setThreshold(1);
        stockEntranceBinding.drugsDataLyt.setVisibility(View.GONE);
        getRelatedViewModel().setInitialDataVisible(true);
        getRelatedViewModel().setDrugDataVisible(false);

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

        try {
            populateDrugList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        enventInitialization();

        Intent intent = this.getIntent();
        try {
            if(intent != null) {
                Bundle bundle = intent.getExtras();
                if(bundle != null) {
                    getRelatedViewModel().setClinic((Clinic) bundle.getSerializable("clinic"));
                    stockEntranceBinding.setClinic(getRelatedViewModel().getClinic());

                    if(bundle.getSerializable("mode") != null) {
                        if(bundle.getSerializable("mode").equals("edit")) {
                            getRelatedViewModel().setViewListRemoveButton(true);
                            stockListEdit = ((List<Stock>) bundle.getSerializable("listStock"));
                            List<Stock> listStock = getRelatedViewModel().getStockByOrderNumber(stockListEdit.get(0).getOrderNumber(), (Clinic) bundle.getSerializable("clinic"));
                            boolean isSync  = false, isUsed = false;
                            for(Stock stEdit : listStock){
                                if (!stEdit.getSyncStatus().equals(Stock.SYNC_SATUS_READY)){
                                    isSync = true;
                                }
                                if (!dispenseDrugService.checkStockIsDispensedDrug(stEdit)){
                                    isUsed = true;
                                }
                            }
                            if(stockListEdit != null ) {
                                if (isSync || isUsed) {
                                    stockEntranceBinding.dataEntrada.setEnabled(false);
                                    stockEntranceBinding.numeroGuia.setEnabled(false);
                                } else {

                                }
                                Stock stb = new Stock();
                                stb.setDateReceived(stockListEdit.get(0).getDateReceived());
                                stb.setOrderNumber(stockListEdit.get(0).getOrderNumber());
                                getRelatedViewModel().setStock(stb);
                                selectedStock.addAll(stockListEdit);
                                Collections.sort(selectedStock);
                                displaySelectedDrugs();
                            //    stockEntranceBinding.spnDrugs.setText(getRelatedViewModel().getStock().getDrug().getDescription());
                                isEditForm = true;
                            }
                        } else if (bundle.getSerializable("mode").equals("view")) {
                            disableForm();
                            selectedStock = (List<Listble>) bundle.getSerializable("listStock");
                            Collections.sort(selectedStock);
                            displaySelectedDrugs();
                        } else {
                            isEditForm = false;
                        }
                    } else {
                        getRelatedViewModel().setViewListRemoveButton(true);
                    }
                    if (getRelatedViewModel().getClinic() == null) {
                        throw new RuntimeException("Não foi seleccionado uma clinic para detalhar.");
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        stockEntranceBinding.spnDrugs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                //this is the way to find selected object/item
                selectedDrug = (Drug) adapterView.getItemAtPosition(pos);
            }
        });
        stockEntranceBinding.setStock(getRelatedViewModel().getStock());
    }

    //Handling Action Bar button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //Back button
            case R.id.about:
                //If this activity started from other activity
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    public void populateDrugList() throws SQLException {
        drugList = new ArrayList<Drug>();
        //drugList.add(0, new Drug());
        drugList.addAll(drugService.getAll());
        adapterSpinner = new ArrayAdapter<Drug>(getApplicationContext(), android.R.layout.select_dialog_item, drugList);
     //   adapterSpinner.setDropDownViewResource(android.R.layout.select_dialog_item);
        stockEntranceBinding.spnDrugs.setAdapter(adapterSpinner);
        stockEntranceBinding.spnDrugs.setThreshold(1);
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

        stockEntranceBinding.dataEntrada.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
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
            }
        });

        stockEntranceBinding.dataValidade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
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
            }
        });

        stockEntranceBinding.imvAddSelectedDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedStock == null) selectedStock = new ArrayList<>();

                if (stockEntranceBinding.spnDrugs.getText().toString().length() != 0) {
                    if(stockEntranceBinding.dataValidade.getText().length() != 0 &&
                            stockEntranceBinding.dataEntrada.getText().length() != 0 &&
                            stockEntranceBinding.numeroLote.getText().length() != 0 &&
                            stockEntranceBinding.numeroGuia.getText().length() != 0 &&
                            stockEntranceBinding.numeroPreco.getText().length() != 0 &&
                            stockEntranceBinding.numeroQuantidadeRecebida.getText().length() != 0) {

                        if(DateUtilitis.dateDiff(DateUtilitis.createDate(DateUtilitis.parseDateToDDMMYYYYString(Calendar.getInstance().getTime()),
                                DateUtilitis.DATE_FORMAT), DateUtilitis.createDate(stockEntranceBinding.dataEntrada.getText().toString(), DateUtilitis.DATE_FORMAT), DateUtilitis.DAY_FORMAT) >= 0) {
                            if(DateUtilitis.dateDiff(DateUtilitis.createDate(stockEntranceBinding.dataValidade.getText().toString(), DateUtilitis.DATE_FORMAT),
                                    DateUtilitis.createDate(DateUtilitis.parseDateToDDMMYYYYString(Calendar.getInstance().getTime()), DateUtilitis.DATE_FORMAT), DateUtilitis.DAY_FORMAT) >= 60) {
                                if(Integer.valueOf(stockEntranceBinding.numeroQuantidadeRecebida.getText().toString()) != 0) {
                                    loadDataForm();
                                    getRelatedViewModel().getStock().setUuid(Utilities.getNewUUID().toString());

                                    Listble listble = (Listble) getRelatedViewModel().getStock();

                                    if (!selectedStock.contains(listble)) {
                                        listble.setListPosition(selectedStock.size() + 1);
                                        selectedStock.add(listble);
                                        getRelatedViewModel().initNewStock();
                                        Collections.sort(selectedStock);
                                        displaySelectedDrugs();
                                        cleanForm();
                                    } else {
                                        Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.drug_data_duplication_msg)).show();
                                    }
                                }else {
                                    Utilities.displayAlertDialog(StockEntranceActivity.this,  getString(R.string.quantity_cannot_be_zero)).show();
                                }
                            }else {
                                Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.drug_validate_date)).show();
                            }
                        }else {
                            Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.drug_entrada_date)).show();
                        }
                    }else {
                        Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.drug_data_empty_filds)).show();
                    }
                }else {
                    Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.must_select_at_least_one_drug)).show();
                }
            }
        });
    }

    private void loadDataForm() {
        drug = selectedDrug;
        getRelatedViewModel().getStock().setDrug(selectedDrug);
        getRelatedViewModel().getStock().setExpiryDate(DateUtilitis.createDate(stockEntranceBinding.dataValidade.getText().toString(), DateUtilitis.DATE_FORMAT));
        getRelatedViewModel().getStock().setDateReceived(DateUtilitis.createDate(stockEntranceBinding.dataEntrada.getText().toString(), DateUtilitis.DATE_FORMAT));
        getRelatedViewModel().getStock().setBatchNumber(stockEntranceBinding.numeroLote.getText().toString());
        getRelatedViewModel().getStock().setOrderNumber(stockEntranceBinding.numeroGuia.getText().toString());
        getRelatedViewModel().getStock().setPrice(Double.valueOf(stockEntranceBinding.numeroPreco.getText().toString()));
        getRelatedViewModel().getStock().setUnitsReceived(Integer.valueOf(stockEntranceBinding.numeroQuantidadeRecebida.getText().toString()));
        getRelatedViewModel().getStock().setClinic(getCurrentClinic());
        getRelatedViewModel().getStock().setStockMoviment(Integer.valueOf(stockEntranceBinding.numeroQuantidadeRecebida.getText().toString()));
        getRelatedViewModel().getStock().setSyncStatus(BaseModel.SYNC_SATUS_READY);
    }

    private void cleanForm() {
        stockEntranceBinding.dataValidade.setText("");
        stockEntranceBinding.numeroLote.setText("");
        stockEntranceBinding.numeroPreco.setText("0");
        stockEntranceBinding.numeroQuantidadeRecebida.setText("");
        stockEntranceBinding.spnDrugs.setText("");
    }

    public void disableForm() {
        stockEntranceBinding.drugsDataLyt.setVisibility(View.VISIBLE);
        stockEntranceBinding.initialDataLyt.setVisibility(View.GONE);
        stockEntranceBinding.initialData.setVisibility(View.GONE);
        stockEntranceBinding.imvAddSelectedDrug.setVisibility(View.GONE);
        stockEntranceBinding.dataValidade.setVisibility(View.GONE);
        stockEntranceBinding.dataEntrada.setVisibility(View.GONE);
        stockEntranceBinding.numeroLote.setVisibility(View.GONE);
        stockEntranceBinding.numeroGuia.setVisibility(View.GONE);
        stockEntranceBinding.numeroPreco.setVisibility(View.GONE);
        stockEntranceBinding.numeroQuantidadeRecebida.setVisibility(View.GONE);
        stockEntranceBinding.spnDrugs.setVisibility(View.GONE);
        stockEntranceBinding.saveAndContinue.setVisibility(View.GONE);
        stockEntranceBinding.txtdrugs.setVisibility(View.GONE);
        stockEntranceBinding.txtimage.setVisibility(View.GONE);
        stockEntranceBinding.txtlote.setVisibility(View.GONE);
        stockEntranceBinding.txtpreco.setVisibility(View.GONE);
        stockEntranceBinding.txtquantidade.setVisibility(View.GONE);
        stockEntranceBinding.txtvalidade.setVisibility(View.GONE);
        stockEntranceBinding.txtlistadrugs.setVisibility(View.GONE);
    }

    private void saveStock() throws SQLException {
        if(!this.selectedStock.isEmpty()) {
            if(!isEditForm) {
                if (stockService.checkStockExist(stockEntranceBinding.numeroGuia.getText().toString(), getRelatedViewModel().getClinic())) {
                    for (Listble listble : this.selectedStock) {
                        stockService.saveOrUpdateStock((Stock) listble);
                    }
                    Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.stock_saved_sucessfully),StockEntranceActivity.this).show();
                } else {
                    selectedStock.clear();
                    //selectedStock.addAll(stockListEdit);
                    //Collections.sort(selectedStock);
                    displaySelectedDrugs();
                    Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.guide_number_already_exists)+ " ("+stockEntranceBinding.numeroGuia.getText().toString()+")").show();
                }
            }else {
                    if (stockEntranceBinding.numeroGuia.getText().toString().equals(stockListEdit.get(0).getOrderNumber())){
                        for (Stock stDelete : stockListEdit){
                            stockService.deleteStock(stDelete);
                        }
                        for (Listble listble : this.selectedStock) {
                            Stock stSave = (Stock) listble;
                            stSave.setOrderNumber(stockEntranceBinding.numeroGuia.getText().toString());
                            stSave.setDateReceived(DateUtilitis.createDate(stockEntranceBinding.dataEntrada.getText().toString(), DateUtilitis.DATE_FORMAT));
                            stockService.saveOrUpdateStock(stSave);
                        }
                        Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.stock_edited_sucessfully), StockEntranceActivity.this).show();
                    } else {
                        if (stockService.checkStockExist(stockEntranceBinding.numeroGuia.getText().toString(), getRelatedViewModel().getClinic())) {
                            for (Stock stDelete : stockListEdit){
                                stockService.deleteStock(stDelete);
                            }
                            for (Listble listble : this.selectedStock) {
                                Stock stSave = (Stock) listble;
                                stSave.setOrderNumber(stockEntranceBinding.numeroGuia.getText().toString());
                                stSave.setDateReceived(DateUtilitis.createDate(stockEntranceBinding.dataEntrada.getText().toString(), DateUtilitis.DATE_FORMAT));
                                stockService.saveOrUpdateStock(stSave);
                            }
                            Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.stock_edited_sucessfully), StockEntranceActivity.this).show();
                        } else {
                            selectedStock.clear();
                            selectedStock.addAll(stockListEdit);
                            Collections.sort(selectedStock);
                            displaySelectedDrugs();
                            Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.guide_number_already_exists)).show();
                        }
                    }
                }
        }else {
            Utilities.displayAlertDialog(StockEntranceActivity.this, getString(R.string.drug_data_empty_filds)).show();
        }
    }

    private void displaySelectedDrugs() {
        if (listbleRecycleViewAdapter != null) {
            listbleRecycleViewAdapter.notifyDataSetChanged();
        }else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rcvSelectedDrugs.setLayoutManager(mLayoutManager);
            rcvSelectedDrugs.setItemAnimator(new DefaultItemAnimator());
            rcvSelectedDrugs.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 0));

            listbleRecycleViewAdapter = new ListbleRecycleViewAdapter(rcvSelectedDrugs, this.selectedStock, this);
            rcvSelectedDrugs.setAdapter(listbleRecycleViewAdapter);
        }
    }

    private void changeVisibilityToInitialData(View view) {
        if (view.equals(stockEntranceBinding.initialData)) {
            if (stockEntranceBinding.initialDataLyt.getVisibility() == View.VISIBLE) {
                stockEntranceBinding.initialDataLyt.setVisibility(View.GONE);
                switchLayout();
            }else {
                stockEntranceBinding.initialDataLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }else if (view.equals(stockEntranceBinding.txvDrugs)){
            if (stockEntranceBinding.drugsDataLyt.getVisibility() == View.VISIBLE) {
                stockEntranceBinding.drugsDataLyt.setVisibility(View.GONE);
                switchLayout();
            }else {
                stockEntranceBinding.drugsDataLyt.setVisibility(View.VISIBLE);
                switchLayout();
            }
        }
    }

    private void switchLayout() {
        getRelatedViewModel().setInitialDataVisible(!getRelatedViewModel().isInitialDataVisible());
        getRelatedViewModel().setDrugDataVisible(!getRelatedViewModel().isDrugDataVisible());
    }

    public Clinic getClinic() {
        return getRelatedViewModel().getClinic();
    }

    @Override
    public StockEntranceVM getRelatedViewModel() {
        return (StockEntranceVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(StockEntranceVM.class);
    }

    @Override
    public void doOnConfirmed() {
        Map<String, Object> params = new HashMap<>();
        params.put("clinic", getRelatedViewModel().getClinic());
        params.put("user", getCurrentUser());
        nextActivity(StockActivity.class, params);
        finish();
    }

    @Override
    public void doOnDeny() {

    }
}