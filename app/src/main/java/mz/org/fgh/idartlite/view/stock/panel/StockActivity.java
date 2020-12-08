package mz.org.fgh.idartlite.view.stock.panel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.tab.stock.StockTabAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityStockBinding;
import mz.org.fgh.idartlite.view.about.AboutActivity;
import mz.org.fgh.idartlite.viewmodel.stock.StockVM;

public class StockActivity extends BaseActivity {

    private ActivityStockBinding stockBinding;
    private StockTabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockBinding = DataBindingUtil.setContentView(this, R.layout.activity_stock);
        stockBinding.setClinic(getRelatedViewModel().getCurrentClinic());

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

        viewPager = stockBinding.viewPager;
        tabLayout = stockBinding.tabLayout;

        adapter = new StockTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new StockEntranceFragment(), getString(R.string.stock_entrance));
        adapter.addFragment(StockReferenceFragment.newInstance(), "Entrada/Saida por referencia");
        adapter.addFragment(DestroiedStockListFragment.newInstance(), "Destruir");
        adapter.addFragment(StockInventoryListingFragment.newInstance(), "Inventario");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_stock_entrance);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_refered);
        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_destroy);
        tabLayout.getTabAt(3).setIcon(R.mipmap.ic_inventory);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
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

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(StockVM.class);
    }

    @Override
    public StockVM getRelatedViewModel() {
        return (StockVM) super.getRelatedViewModel();
    }
}