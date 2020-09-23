package mz.org.fgh.idartlite.view.patient;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.ClickListener;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.common.ListbleDialogListener;
import mz.org.fgh.idartlite.common.RecyclerTouchListener;
import mz.org.fgh.idartlite.databinding.FragmentDispenseBinding;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.adapter.DispenseAdapter;
import mz.org.fgh.idartlite.viewmodel.DispenseVM;

public class DispenseFragment extends GenericFragment implements ListbleDialogListener {

    public DispenseFragment() {
        // Required empty public constructor
    }



    private RecyclerView rcvDispences;
    private List<Dispense> dispenseList;

    private FragmentDispenseBinding fragmentDispenseBinding;
    private DispenseAdapter dispenseAdapter;
    int dispensePosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentDispenseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dispense, container,false);
        return fragmentDispenseBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.rcvDispences = fragmentDispenseBinding.rcvDispenses;

        try {
            this.dispenseList = getRelatedViewModel().gatAllOfPatient(getSelectedPatient());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (Utilities.listHasElements(dispenseList)) {
            dispenseAdapter = new DispenseAdapter(rcvDispences, this.dispenseList, getMyActivity());
            displayDataOnRecyclerView(rcvDispences, dispenseAdapter, getContext());
        }

        fragmentDispenseBinding.newDispense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), CreateDispenseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", getCurrentUser());
                bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                bundle.putSerializable("patient", getMyActivity().getPatient());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        rcvDispences.addOnItemTouchListener(
                new ClickListener(
                        getContext(), rcvDispences, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Dispense dispense = dispenseList.get(position);
                        dispensePosition=position;
                        PopupMenu popup = new PopupMenu(view.getContext(),view);
                        MenuInflater inflater = popup.getMenuInflater();
                        popup.setOnMenuItemClickListener(DispenseFragment.this::onMenuItemClick);
                        inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
                        popup.show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Dispense dispense = dispenseList.get(position);
                        dispensePosition=position;
                        PopupMenu popup = new PopupMenu(view.getContext(),view);
                        MenuInflater inflater = popup.getMenuInflater();
                        popup.setOnMenuItemClickListener(DispenseFragment.this::onMenuItemClick);
                        inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
                        popup.show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
                ));

    }

    /*private void displayPrescriptions() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcvDispences.setLayoutManager(mLayoutManager);
        rcvDispences.setItemAnimator(new DefaultItemAnimator());
        rcvDispences.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        dispenseAdapter = new DispenseAdapter(rcvDispences, this.dispenseList, getMyActivity());
        rcvDispences.setAdapter(dispenseAdapter);

        rcvDispences.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rcvDispences, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(getContext(), "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getContext(), "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
    }*/

    public boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()){


            case R.id.edit:
                //Call activity to Edit
                return true;
            case R.id.remove:
                Utilities.displayDeleteConfirmationDialogFromList(DispenseFragment.this.getContext(),DispenseFragment.this.getString(R.string.list_item_delete_msg),dispensePosition,DispenseFragment.this).show();
                return true;
            default:
                return false;
        }

    }

    @Override
    public void remove(int position) {

        if(dispenseList.get(position).getSyncStatus().equals("R")){
            dispenseList.remove(dispenseList.get(position));

            for (int i = 0; i < dispenseList.size(); i++){
                dispenseList.get(i).setListPosition(i+1);
            }
            rcvDispences.getAdapter().notifyItemRemoved(position);
            rcvDispences.removeViewAt(position);
            rcvDispences.getAdapter().notifyItemRangeChanged(position, rcvDispences.getAdapter().getItemCount());


            try {
                getRelatedViewModel().deleteDispense(dispenseList.get(position));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Utilities.displayAlertDialog(DispenseFragment.this.getContext(),"A Dispensa nao pode ser removido uma vez que ja foi sicronizado com a central").show();
        }

    }

    @Override
    public void remove(BaseModel baseModel) {

    }


    public PatientActivity getMyActivity(){
        return (PatientActivity) getActivity();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(DispenseVM.class);
    }

    @Override
    public DispenseVM getRelatedViewModel() {
        return (DispenseVM) super.getRelatedViewModel();
    }

    private Patient getSelectedPatient(){
        return getMyActivity().getPatient();
    }

}