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
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.stock.destroy.DestroyStockActivity;
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
        if (adapter == null) {
            adapter = new IventoryAdapter(rcvIventory, getRelatedViewModel().getAllDisplyedRecords(), getMyActivity());
            rcvIventory.setAdapter(adapter);
        }

        if (adapter.getOnLoadMoreListener() == null) {
            adapter.setOnLoadMoreListener(new IOnLoadMoreListener() {
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

                String editErrors = getRelatedViewModel().getSelectedRecord().canBeEdited(StockInventoryListingFragment.this.getContext());

                if (Utilities.stringHasValue(editErrors)) {
                    Utilities.displayAlertDialog(StockInventoryListingFragment.this.getContext(), editErrors).show();
                } else {
                    Map<String, Object> params = new HashMap<>();
                    params.put("relatedRecord", getRelatedViewModel().getSelectedRecord());
                    params.put("user", getRelatedViewModel().getCurrentUser());
                    params.put("clinic", getMyActivity().getCurrentClinic());
                    params.put("step", ApplicationStep.STEP_EDIT);
                    nextActivity(DestroyStockActivity.class, params);
                }

                return true;
            case R.id.remove:
                Utilities.displayDeleteConfirmationDialogFromList(StockInventoryListingFragment.this.getContext(), getString(R.string.list_item_delete_msg), getRelatedViewModel().getSelectedRecord().getListPosition(), StockInventoryListingFragment.this).show();
                return true;

            case R.id.viewDetails:
                Map<String, Object> params = new HashMap<>();
                params.put("relatedRecord", getRelatedViewModel().getSelectedRecord());
                params.put("user", getRelatedViewModel().getCurrentUser());
                params.put("clinic", getMyActivity().getCurrentClinic());
                params.put("step", ApplicationStep.STEP_DISPLAY);
                nextActivity(DestroyStockActivity.class, params);
            default:
                return false;
        }

    }

    @Override
    public void remove(int position) {
        try {
            getRelatedViewModel().deleteRecord(getRelatedViewModel().getSelectedRecord());

            doAfterRemove(position);

            Utilities.displayAlertDialog(StockInventoryListingFragment.this.getContext(), getString(R.string.record_sucessfuly_removed)).show();

        } catch (SQLException e) {
            Utilities.displayAlertDialog(StockInventoryListingFragment.this.getContext(), getString(R.string.error_removing_record)+ " "+e.getLocalizedMessage()).show();
        }
    }


    private void doAfterRemove(int position) {
        getRelatedViewModel().getAllDisplyedRecords().remove(getRelatedViewModel().getSelectedRecord());

        rcvIventory.getAdapter().notifyItemRemoved(position);
        rcvIventory.removeViewAt(position);
        rcvIventory.getAdapter().notifyItemRangeChanged(position, rcvIventory.getAdapter().getItemCount());
    }
}