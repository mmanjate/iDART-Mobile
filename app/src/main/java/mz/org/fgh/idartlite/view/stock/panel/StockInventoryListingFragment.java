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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.stock.IventoryAdapter;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.databinding.FragmentStockInventoryBinding;
import mz.org.fgh.idartlite.listener.recyclerView.ClickListener;
import mz.org.fgh.idartlite.listener.recyclerView.IOnLoadMoreListener;
import mz.org.fgh.idartlite.model.inventory.Iventory;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.stock.inventory.IventoryActivity;
import mz.org.fgh.idartlite.viewmodel.stock.IventoryListingVM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StockInventoryListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockInventoryListingFragment extends GenericFragment {

    private FragmentStockInventoryBinding iventoryBinding;

    private IventoryAdapter adapter;

    private RecyclerView rcvIventory;

    public StockInventoryListingFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StockInventoryListingFragment.
     */
    public static StockInventoryListingFragment newInstance() {
        StockInventoryListingFragment fragment = new StockInventoryListingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        iventoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_inventory, container,false);
        return iventoryBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iventoryBinding.setViewModel(getRelatedViewModel());

        this.rcvIventory = iventoryBinding.rcvIventories;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcvIventory.setLayoutManager(mLayoutManager);
        rcvIventory.setItemAnimator(new DefaultItemAnimator());
        rcvIventory.addItemDecoration(new DividerItemDecoration(getContext(), 0));

        rcvIventory.addOnItemTouchListener(
                new ClickListener(getContext(), rcvIventory, new ClickListener.OnItemClickListener() {
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
                }
                ));
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            getRelatedViewModel().initSearch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IventoryListingVM getRelatedViewModel() {
        return (IventoryListingVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(IventoryListingVM.class);
    }

    public void displaySearchResult() {

            this.adapter = new IventoryAdapter(rcvIventory, getRelatedViewModel().getAllDisplyedRecords(), getMyActivity());
            rcvIventory.setAdapter(this.adapter);


        if (this.adapter.getOnLoadMoreListener() == null) {
            this.adapter.setOnLoadMoreListener(new IOnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    getRelatedViewModel().loadMoreRecords(rcvIventory, adapter);
                }
            });
        }
    }

    private void displayPopupMenu(View view, int position) {
        getRelatedViewModel().setSelectedRecord(getRelatedViewModel().getAllDisplyedRecords().get(position));
        getRelatedViewModel().getSelectedRecord().setListPosition(position);

        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(StockInventoryListingFragment.this::onMenuItemClick);
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
                    startInventoryActivity(getRelatedViewModel().getSelectedRecord(), ApplicationStep.STEP_EDIT);
                }

                return true;
            case R.id.remove:

                String removeErrors = getRelatedViewModel().getSelectedRecord().canBeRemoved(getContext());

                if (Utilities.stringHasValue(removeErrors)) {
                    Utilities.displayAlertDialog(getContext(), removeErrors).show();
                } else {
                    Utilities.displayDeleteConfirmationDialogFromList(getContext(), getString(R.string.list_item_delete_msg), getRelatedViewModel().getSelectedRecord().getListPosition(), StockInventoryListingFragment.this).show();
                }
                return true;

            case R.id.viewDetails:
                startInventoryActivity(getRelatedViewModel().getSelectedRecord(), ApplicationStep.STEP_DISPLAY);

            default:
                return false;
        }

    }

    @Override
    public void remove(int position) {
        try {
            getRelatedViewModel().deleteRecord(getRelatedViewModel().getSelectedRecord());

            doAfterRemove(position);

            Utilities.displayAlertDialog(getContext(), getString(R.string.record_sucessfuly_removed)).show();

        } catch (SQLException e) {
            Utilities.displayAlertDialog(getContext(), getString(R.string.error_removing_record)+ " "+e.getLocalizedMessage()).show();
        }
    }


    private void doAfterRemove(int position) {
        getRelatedViewModel().getAllDisplyedRecords().remove(getRelatedViewModel().getSelectedRecord());

        rcvIventory.getAdapter().notifyItemRemoved(position);
        rcvIventory.removeViewAt(position);
        rcvIventory.getAdapter().notifyItemRangeChanged(position, rcvIventory.getAdapter().getItemCount());
    }

    public void startInventoryActivity(Iventory record, String step) {
        Map<String, Object> params = new HashMap<>();
        if (record != null) params.put("relatedRecord", record);
        params.put("user", getRelatedViewModel().getCurrentUser());
        params.put("clinic", getMyActivity().getCurrentClinic());
        if (Utilities.stringHasValue(step)) params.put("step", step);
        nextActivity(IventoryActivity.class, params);
    }
}