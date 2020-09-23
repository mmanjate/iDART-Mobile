package mz.org.fgh.idartlite.view.stock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityStockEntranceBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.viewmodel.StockEntranceVM;

public class StockEntranceActivity extends BaseActivity {

    private ActivityStockEntranceBinding stockEntranceBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockEntranceBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock_entrance);

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