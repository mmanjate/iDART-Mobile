package mz.org.fgh.idartlite.view.stock;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityStockEntranceBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.service.DrugService;
import mz.org.fgh.idartlite.view.patient.PrescriptionActivity;
import mz.org.fgh.idartlite.viewmodel.StockEntranceVM;

public class StockEntranceActivity extends BaseActivity {

    private ActivityStockEntranceBinding stockEntranceBinding;
    private DrugService drugService;
    private List<Drug> drugList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockEntranceBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock_entrance);
        drugService = new DrugService(getApplication(), getCurrentUser());

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