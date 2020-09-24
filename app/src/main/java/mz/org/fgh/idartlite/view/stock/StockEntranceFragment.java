package mz.org.fgh.idartlite.view.stock;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.ClickListener;
import mz.org.fgh.idartlite.adapter.StockEntranceAdapter;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.common.ListbleDialogListener;
import mz.org.fgh.idartlite.databinding.FragmentStockEntranceBinding;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.StockEntranceVM;

public class StockEntranceFragment extends GenericFragment implements ListbleDialogListener {

    private RecyclerView rcvFragmentStock;
    private List<Stock> stockList;
    private Stock stock;
    private StockEntranceAdapter stockEntranceAdapter;
    private FragmentStockEntranceBinding fragmentStockEntranceBinding;
    int stockPosition;
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

        rcvFragmentStock.addOnItemTouchListener(
                new ClickListener(
                        getContext(), rcvFragmentStock, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        stock = stockList.get(position);
                        stockPosition = position;
                        PopupMenu popup = new PopupMenu(view.getContext(),view);
                        MenuInflater inflater = popup.getMenuInflater();
                        popup.setOnMenuItemClickListener(StockEntranceFragment.this::onMenuItemClick);
                        inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
                        popup.show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        stock = stockList.get(position);
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

    public boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()){


            case R.id.edit:
                //Call activity to Edit
                Intent intent = new Intent(getContext(), StockEntranceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", getCurrentUser());
                bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                bundle.putSerializable("stock", stock);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.remove:
                Utilities.displayDeleteConfirmationDialogFromList(StockEntranceFragment.this.getContext(),StockEntranceFragment.this.getString(R.string.list_item_delete_msg),stockPosition,StockEntranceFragment.this).show();
                return true;
            default:
                return false;
        }

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
    public void remove(int position) {

        if(stockList.get(position).getSyncStatus().equals("R")){
            stockList.remove(stockList.get(position));

            for (int i = 0; i < stockList.size(); i++){
                stockList.get(i).setListPosition(i+1);
            }
            rcvFragmentStock.getAdapter().notifyItemRemoved(position);
            rcvFragmentStock.getAdapter().notifyItemRangeChanged(position, rcvFragmentStock.getAdapter().getItemCount());
            try {
                getRelatedViewModel().deleteStock(stock);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Utilities.displayAlertDialog(StockEntranceFragment.this.getContext(),"O Stock nao pode ser removido uma vez que ja foi sicronizado com a central").show();
        }

    }

    @Override
    public void remove(BaseModel baseModel) {

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