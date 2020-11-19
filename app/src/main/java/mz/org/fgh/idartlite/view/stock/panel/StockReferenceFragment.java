package mz.org.fgh.idartlite.view.stock.panel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.StockReferenceFragmentBinding;
import mz.org.fgh.idartlite.viewmodel.stock.StockReferenceVM;

public class StockReferenceFragment extends GenericFragment {

    private StockReferenceFragmentBinding stockReferenceFragmentBinding;

    public static StockReferenceFragment newInstance() {
        return new StockReferenceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        stockReferenceFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.stock_reference_fragment, container,false);
        // Inflate the layout for this fragment
        return stockReferenceFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        stockReferenceFragmentBinding.setViewModel(getRelatedViewModel());

    }

    @Override
    public StockReferenceVM getRelatedViewModel() {
        return (StockReferenceVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(StockReferenceVM.class);
    }
}