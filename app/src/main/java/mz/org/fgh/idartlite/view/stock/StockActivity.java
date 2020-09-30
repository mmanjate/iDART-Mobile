package mz.org.fgh.idartlite.view.stock;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.StockTabAdapter;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityStockBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.viewmodel.StockVM;

public class StockActivity extends BaseActivity {

    private ActivityStockBinding stockBinding;
    private StockTabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                getRelatedViewModel().setClinic((Clinic) bundle.getSerializable("clinic"));
                stockBinding.setClinic(getRelatedViewModel().getClinic());
                if (getRelatedViewModel().getClinic() == null){
                    throw new RuntimeException("Não foi seleccionado uma clinic para detalhar.");
                }
            }
        }

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

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new StockTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new StockEntranceFragment(), getString(R.string.stock_entrance));
        adapter.addFragment(new StockInventoryFragment(), getString(R.string.stock_inventory));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_stock_entrance);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_stock_inventory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    public Clinic getClinic(){
        return getRelatedViewModel().getClinic();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(StockVM.class);
    }

    @Override
    public StockVM getRelatedViewModel() {
        return (StockVM) super.getRelatedViewModel();
    }
}