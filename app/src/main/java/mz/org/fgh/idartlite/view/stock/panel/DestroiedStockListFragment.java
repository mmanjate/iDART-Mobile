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
import mz.org.fgh.idartlite.adapter.recyclerview.stock.DestroiedStockAdapter;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.databinding.FragmentDestroyStockBinding;
import mz.org.fgh.idartlite.listener.recyclerView.ClickListener;
import mz.org.fgh.idartlite.listener.recyclerView.IOnLoadMoreListener;
import mz.org.fgh.idartlite.model.DestroyedDrug;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.stock.destroy.DestroyStockActivity;
import mz.org.fgh.idartlite.viewmodel.stock.DestroiedStockListingVM;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DestroiedStockListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DestroiedStockListFragment extends GenericFragment {

    private DestroiedStockAdapter adapter;

    private FragmentDestroyStockBinding destroyStockBinding;

    private RecyclerView destroyedStockRV;

    public DestroiedStockListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        destroyStockBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_destroy_stock, container,false);
        // Inflate the layout for this fragment
        return destroyStockBinding.getRoot();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DestroiedStockListFragment.
     */
    public static DestroiedStockListFragment newInstance() {
        DestroiedStockListFragment fragment = new DestroiedStockListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        destroyStockBinding.setViewModel(getRelatedViewModel());

        this.destroyedStockRV = destroyStockBinding.rcvDestroiedStock;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        destroyedStockRV.setLayoutManager(mLayoutManager);
        destroyedStockRV.setItemAnimator(new DefaultItemAnimator());
        destroyedStockRV.addItemDecoration(new DividerItemDecoration(getContext(), 0));

        destroyedStockRV.addOnItemTouchListener(
                new ClickListener(getContext(), destroyedStockRV, new ClickListener.OnItemClickListener() {
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
    public DestroiedStockListingVM getRelatedViewModel() {
        return (DestroiedStockListingVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(DestroiedStockListingVM.class);
    }


    public void displaySearchResult() {

        if (this.adapter == null) {
            this.adapter = new DestroiedStockAdapter(destroyedStockRV, getRelatedViewModel().getAllDisplyedRecords(), getMyActivity());
            destroyedStockRV.setAdapter(this.adapter);
        }else {
            this.adapter.notifyDataSetChanged();
        }

        if (this.adapter.getOnLoadMoreListener() == null) {
            this.adapter.setOnLoadMoreListener(new IOnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    getRelatedViewModel().loadMoreRecords(destroyedStockRV, adapter);
                }
            });
        }
    }

    private void displayPopupMenu(View view, int position) {
        getRelatedViewModel().setSelectedRecord(getRelatedViewModel().getAllDisplyedRecords().get(position));
        getRelatedViewModel().getSelectedRecord().setListPosition(position);

        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(DestroiedStockListFragment.this::onMenuItemClick);
        inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
        popup.show();
    }

    public boolean onMenuItemClick(MenuItem item){
        Map<String, Object> params = new HashMap<>();

        switch (item.getItemId()){
            case R.id.edit:
                getRelatedViewModel().initEdition(getContext());

                startDestroyStockActivity(getRelatedViewModel().getSelectedRecord(), ApplicationStep.STEP_EDIT);

                return true;
            case R.id.remove:
                getRelatedViewModel().initRemotion(getContext());

                Utilities.displayDeleteConfirmationDialogFromList(DestroiedStockListFragment.this.getContext(), getString(R.string.list_item_delete_msg), getRelatedViewModel().getSelectedRecord().getListPosition(), DestroiedStockListFragment.this).show();
                return true;

            case R.id.viewDetails:
                startDestroyStockActivity(getRelatedViewModel().getSelectedRecord(), ApplicationStep.STEP_DISPLAY);
            default:
                return false;
        }

    }

    @Override
    public void remove(int position) {
        try {
            getRelatedViewModel().deleteRecord(getRelatedViewModel().getSelectedRecord());

            doAfterRemove(position);

            Utilities.displayAlertDialog(DestroiedStockListFragment.this.getContext(), getString(R.string.record_sucessfuly_removed)).show();

        } catch (SQLException e) {
            Utilities.displayAlertDialog(DestroiedStockListFragment.this.getContext(), getString(R.string.error_removing_record) + " " + e.getLocalizedMessage()).show();
        }
    }

    private void doAfterRemove(int position) {
        getRelatedViewModel().getAllDisplyedRecords().remove(getRelatedViewModel().getSelectedRecord());

        destroyedStockRV.getAdapter().notifyItemRemoved(position);
        destroyedStockRV.removeViewAt(position);
        destroyedStockRV.getAdapter().notifyItemRangeChanged(position, destroyedStockRV.getAdapter().getItemCount());
    }

    public void startDestroyStockActivity(DestroyedDrug record, String step) {
        Map<String, Object> params = new HashMap<>();
        if (record != null) params.put("relatedRecord", record);
        params.put("user", getRelatedViewModel().getCurrentUser());
        params.put("clinic", getMyActivity().getCurrentClinic());
        if (Utilities.stringHasValue(step)) params.put("step", step);
        nextActivity(DestroyStockActivity.class, params);
    }
}