package mz.org.fgh.idartlite.view.stock.panel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.stock.ReferedStockMovimentAdapter;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.databinding.StockReferenceFragmentBinding;
import mz.org.fgh.idartlite.listener.recyclerView.ClickListener;
import mz.org.fgh.idartlite.listener.recyclerView.IOnLoadMoreListener;
import mz.org.fgh.idartlite.model.ReferedStockMoviment;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.stock.referedstock.ReferedStockMovimentActivity;
import mz.org.fgh.idartlite.viewmodel.stock.ReferedStockMovimentListingVM;

public class StockReferenceFragment extends GenericFragment {

    private StockReferenceFragmentBinding stockReferenceFragmentBinding;

    private ReferedStockMovimentAdapter adapter;

    private RecyclerView rcvReferedStock;


    public static StockReferenceFragment newInstance() {
        return new StockReferenceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        stockReferenceFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.stock_reference_fragment, container,false);

        return stockReferenceFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        stockReferenceFragmentBinding.setViewModel(getRelatedViewModel());

        this.rcvReferedStock = stockReferenceFragmentBinding.rcvIventories;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcvReferedStock.setLayoutManager(mLayoutManager);
        rcvReferedStock.setItemAnimator(new DefaultItemAnimator());
        rcvReferedStock.addItemDecoration(new DividerItemDecoration(getContext(), 0));

        rcvReferedStock.addOnItemTouchListener(
                new ClickListener(getContext(), rcvReferedStock, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        displayPopupMenu(view, position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        displayPopupMenu(view, position);
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));
    }

    @Override
    public void onResume() {
        super.onResume();
        getRelatedViewModel().initSearch();
    }

    @Override
    public ReferedStockMovimentListingVM getRelatedViewModel() {
        return (ReferedStockMovimentListingVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(ReferedStockMovimentListingVM.class);
    }

    public void displaySearchResult() {

            this.adapter = new ReferedStockMovimentAdapter(rcvReferedStock, getRelatedViewModel().getAllDisplyedRecords(), getMyActivity());
            rcvReferedStock.setAdapter(this.adapter);


        if (this.adapter.getOnLoadMoreListener() == null) {
            this.adapter.setOnLoadMoreListener(new IOnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    getRelatedViewModel().loadMoreRecords(rcvReferedStock, adapter);
                }
            });
        }
    }

    private void displayPopupMenu(View view, int position) {
        getRelatedViewModel().setSelectedRecord(getRelatedViewModel().getAllDisplyedRecords().get(position));
        getRelatedViewModel().getSelectedRecord().setListPosition(position);

        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(StockReferenceFragment.this::onMenuItemClick);
        inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
        popup.show();
    }

    public boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()){
            case R.id.edit:

                String editErrors = getRelatedViewModel().getSelectedRecord().canBeEdited(getContext());

                if (Utilities.stringHasValue(editErrors)) {
                    Utilities.displayAlertDialog(getContext(), editErrors).show();
                } else {
                    startReferedStockMovimentActivity(getRelatedViewModel().getRelatedRecord(), ApplicationStep.STEP_LIST);
                }

                return true;
            case R.id.remove:
                String removeErrors = getRelatedViewModel().getSelectedRecord().canBeRemoved(getContext());

                if (Utilities.stringHasValue(removeErrors)) {
                    Utilities.displayAlertDialog(getContext(), removeErrors).show();
                } else {
                    Utilities.displayDeleteConfirmationDialogFromList(getContext(), getString(R.string.list_item_delete_msg), getRelatedViewModel().getSelectedRecord().getListPosition(), StockReferenceFragment.this).show();
                }
                return true;

            case R.id.viewDetails:
                startReferedStockMovimentActivity(getRelatedViewModel().getRelatedRecord(), ApplicationStep.STEP_DISPLAY);
            default:
                return false;
        }

    }

    @Override
    public void remove(int position) {
        try {
            getRelatedViewModel().deleteRecord(getRelatedViewModel().getRelatedRecord());

            doAfterRemove(position);

            Utilities.displayAlertDialog(getContext(), getString(R.string.record_sucessfuly_removed)).show();

        } catch (SQLException e) {
            Utilities.displayAlertDialog(getContext(), getString(R.string.error_removing_record)+ " "+e.getLocalizedMessage()).show();
        }
    }


    private void doAfterRemove(int position) {
        getRelatedViewModel().getAllDisplyedRecords().remove(getRelatedViewModel().getSelectedRecord());

        rcvReferedStock.getAdapter().notifyItemRemoved(position);
        rcvReferedStock.removeViewAt(position);
        rcvReferedStock.getAdapter().notifyItemRangeChanged(position, rcvReferedStock.getAdapter().getItemCount());
    }

    public void startReferedStockMovimentActivity(ReferedStockMoviment relatedRecord, String step) {
        Map<String, Object> params = new HashMap<>();
        if (relatedRecord != null) params.put("relatedRecord", relatedRecord);
        params.put("user", getRelatedViewModel().getCurrentUser());
        params.put("clinic", getMyActivity().getCurrentClinic());
        if (Utilities.stringHasValue(step)) params.put("step", step);
        nextActivity(ReferedStockMovimentActivity.class, params);
    }
}