package mz.org.fgh.idartlite.view.stock;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.AbstractRecycleViewAdapter;
import mz.org.fgh.idartlite.adapter.ClickListener;
import mz.org.fgh.idartlite.adapter.StockEntranceAdapter;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.common.ListbleDialogListener;
import mz.org.fgh.idartlite.common.OnLoadMoreListener;
import mz.org.fgh.idartlite.databinding.FragmentStockEntranceBinding;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.service.DispenseDrugService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.StockEntranceVM;

public class StockEntranceFragment extends GenericFragment implements ListbleDialogListener {

    private RecyclerView rcvFragmentStock;
    private Stock stock;
    private DispenseDrugService dispenseDrugService;
    private StockEntranceAdapter stockEntranceAdapter;
    private FragmentStockEntranceBinding fragmentStockEntranceBinding;
    int stockPosition;

    public StockEntranceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStockEntranceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_entrance, container,false);
        return fragmentStockEntranceBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dispenseDrugService = new DispenseDrugService(getMyActivity().getApplication(), getCurrentUser());
        this.rcvFragmentStock = fragmentStockEntranceBinding.rcvFragmentStock;

        getRelatedViewModel().setEntranceFragment(this);

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

        rcvFragmentStock.addOnItemTouchListener(
                new ClickListener(
                        getContext(), rcvFragmentStock, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        stock = getRelatedViewModel().getAllDisplyedRecords().get(position);
                        stockPosition = position;
                        PopupMenu popup = new PopupMenu(view.getContext(),view);
                        MenuInflater inflater = popup.getMenuInflater();
                        popup.setOnMenuItemClickListener(StockEntranceFragment.this::onMenuItemClick);
                        inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
                        popup.show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        stock = getRelatedViewModel().getAllDisplyedRecords().get(position);
                        stockPosition = position;
                        PopupMenu popup = new PopupMenu(view.getContext(),view);
                        MenuInflater inflater = popup.getMenuInflater();
                        popup.setOnMenuItemClickListener(StockEntranceFragment.this::onMenuItemClick);
                        inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
                        popup.show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
                ));
    }

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                //Call activity to Edit
                try {
                    List<Stock> listStock = getRelatedViewModel().getStockByOrderNumber(stock.getOrderNumber(), getMyActivity().getCurrentClinic());
                    List<Stock> auxList = new ArrayList<Stock>();
                    for(Stock estoque : listStock){
                        if(estoque.getSyncStatus().equals(Stock.SYNC_SATUS_READY)){
                            auxList.add(estoque);
                        }
                    }
                    if (!auxList.isEmpty()) {
                        boolean isUsedStock = false;
                        for(Stock st : auxList){
                            if(dispenseDrugService.checkStockIsDispensedDrug(st)){
                                isUsedStock = true;
                            } else {
                                auxList.remove(st);
                            }
                        }
                            if (isUsedStock) {
                                Intent intent = new Intent(getContext(), StockEntranceActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user", getCurrentUser());
                                bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                                bundle.putSerializable("listStock", (Serializable) auxList);
                                bundle.putSerializable("mode", "edit");
                                intent.putExtras(bundle);
                                startActivity(intent);
                                return true;
                            } else {
                                Utilities.displayAlertDialog(StockEntranceFragment.this.getContext(), getString(R.string.stock_cannot_be_edited_already_used)).show();
                                return false;
                            }
                    } else {
                        Utilities.displayAlertDialog(StockEntranceFragment.this.getContext(), getString(R.string.stock_cannot_be_edited_already_synchronized)).show();
                        return false;
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            case R.id.remove:
                Utilities.displayDeleteConfirmationDialogFromList(StockEntranceFragment.this.getContext(), StockEntranceFragment.this.getString(R.string.list_item_delete_msg), stockPosition,StockEntranceFragment.this).show();
                return true;

            case R.id.viewDetails:
                try {
                    List<Stock> listStock = getRelatedViewModel().getStockByOrderNumber(stock.getOrderNumber(), getMyActivity().getCurrentClinic());
                    Intent intent = new Intent(getContext(), StockEntranceActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", getCurrentUser());
                    bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                    bundle.putSerializable("listStock", (Serializable) listStock);
                    bundle.putSerializable("mode", "view");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            default:
                return false;
        }

    }

    public void displaySearchResults() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvFragmentStock.setLayoutManager(layoutManager);
        rcvFragmentStock.setHasFixedSize(true);
        stockEntranceAdapter = new StockEntranceAdapter(this.rcvFragmentStock, getRelatedViewModel().getAllDisplyedRecords(), getMyActivity());
        rcvFragmentStock.setAdapter(stockEntranceAdapter);

        stockEntranceAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getRelatedViewModel().loadMoreRecords(rcvFragmentStock, stockEntranceAdapter);
            }
        });
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
    public void remove(int position) throws SQLException {
        try {
            List<Stock> removeStockList = getRelatedViewModel().getStockByOrderNumber(getRelatedViewModel().getSearchResults().get(position).getOrderNumber(), getMyActivity().getCurrentClinic());

            for (Stock stRemove : removeStockList){
                if(stRemove.getSyncStatus().equals(Stock.SYNC_SATUS_READY)){
                    if(dispenseDrugService.checkStockIsDispensedDrug(stRemove)){
                        getRelatedViewModel().deleteStock(stRemove);
                        getRelatedViewModel().getAllDisplyedRecords().remove(getRelatedViewModel().getAllDisplyedRecords().get(position));
                        for (int i = 0; i < getRelatedViewModel().getSearchResults().size(); i++){
                            getRelatedViewModel().getSearchResults().get(i).setListPosition(i+1);
                        }
                        rcvFragmentStock.getAdapter().notifyItemRemoved(position);
                        rcvFragmentStock.getAdapter().notifyItemRangeChanged(position, rcvFragmentStock.getAdapter().getItemCount());
                    }
                    else {
                        Utilities.displayAlertDialog(StockEntranceFragment.this.getContext(),getString(R.string.stock_cannot_be_removed_already_used)).show();
                    }
                }
                else {
                    Utilities.displayAlertDialog(StockEntranceFragment.this.getContext(),getString(R.string.stock_cannot_be_removed_already_synchronized)).show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(BaseModel baseModel) {}

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