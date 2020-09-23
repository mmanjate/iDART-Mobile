package mz.org.fgh.idartlite.view.stock;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.StockEntranceAdapter;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.databinding.FragmentStockEntranceBinding;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.StockEntranceVM;

public class StockEntranceFragment extends GenericFragment {

    private RecyclerView rcvFragmentStock;
    private List<Stock> stockList;
    private StockEntranceAdapter stockEntranceAdapter;
    private FragmentStockEntranceBinding fragmentStockEntranceBinding;

    public StockEntranceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentStockEntranceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_entrance, container,false);
        return fragmentStockEntranceBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.rcvFragmentStock = fragmentStockEntranceBinding.rcvFragmentStock;

        fragmentStockEntranceBinding.newStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), StockEntranceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", getCurrentUser());
                bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            this.stockList = getRelatedViewModel().getStockByClinic(getMyActivity().getCurrentClinic());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (Utilities.listHasElements(stockList)) {
            stockEntranceAdapter = new StockEntranceAdapter(this.rcvFragmentStock, this.stockList, getMyActivity());
            displayDataOnRecyclerView(rcvFragmentStock, stockEntranceAdapter, getContext());
        }
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(StockEntranceVM.class);
    }

    public StockActivity getMyActivity() {
        return (StockActivity) getActivity();
    }

    @Override
    public StockEntranceVM getRelatedViewModel(){
        return (StockEntranceVM) super.getRelatedViewModel();
    }
}