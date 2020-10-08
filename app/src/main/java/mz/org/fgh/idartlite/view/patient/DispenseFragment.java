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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.ClickListener;
import mz.org.fgh.idartlite.adapter.StockEntranceAdapter;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.common.ApplicationStep;
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

    public static final String FRAGMENT_CODE_DISPENSE = "DispenseFragment";

    public DispenseFragment() {
        // Required empty public constructor
    }



    private RecyclerView rcvDispences;
    private List<Dispense> dispenseList;

    private FragmentDispenseBinding fragmentDispenseBinding;
    private DispenseAdapter dispenseAdapter;
    int dispensePosition;
    private List<Prescription> prescriptionList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentDispenseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dispense, container,false);
        return fragmentDispenseBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.rcvDispences = fragmentDispenseBinding.rcvDispenses;

        this.dispenseList = new ArrayList<>();

        try {
            this.dispenseList = getRelatedViewModel().gatAllOfPatient(getSelectedPatient());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (Utilities.listHasElements(this.dispenseList)) {
            this.dispenseAdapter = new DispenseAdapter(rcvDispences, this.dispenseList, getMyActivity());
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
                bundle.putSerializable("step", ApplicationStep.STEP_CREATE);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        rcvDispences.addOnItemTouchListener(
                new ClickListener(
                        getContext(), rcvDispences, new ClickListener.OnItemClickListener() {
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

    private void displayPopupMenu(View view, int position) {
        getRelatedViewModel().setDispense(dispenseList.get(position));
        getRelatedViewModel().getDispense().setListPosition(position);

        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(DispenseFragment.this::onMenuItemClick);
        inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
        popup.show();
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
                try {
                    String editErrors = getRelatedViewModel().dispenseCanBeEdited();

                    if (Utilities.stringHasValue(editErrors)){
                        Utilities.displayAlertDialog(DispenseFragment.this.getContext(),editErrors).show();
                    }else {
                Map<String, Object> params = new HashMap<>();
                params.put("patient", getMyActivity().getPatient());
                params.put("user", getCurrentUser());
                params.put("clinic", getMyActivity().getCurrentClinic());
                params.put("dispense", getRelatedViewModel().getDispense());
                params.put("step", ApplicationStep.STEP_EDIT);
                nextActivity(CreateDispenseActivity.class,params);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.remove:
                Utilities.displayDeleteConfirmationDialogFromList(DispenseFragment.this.getContext(),DispenseFragment.this.getString(R.string.list_item_delete_msg),dispensePosition,DispenseFragment.this).show();
                return true;

            case R.id.viewDetails:
                Map<String, Object>  params = new HashMap<>();
                params.put("patient", getMyActivity().getPatient());
                params.put("user", getCurrentUser());
                params.put("clinic", getMyActivity().getCurrentClinic());
                params.put("dispense", getRelatedViewModel().getDispense());
                params.put("step", ApplicationStep.STEP_DISPLAY);
                nextActivity(CreateDispenseActivity.class, params);
                return true;
            default:
                return false;
        }

    }

    @Override
    public void remove(int position) {

        String errorMsg = getRelatedViewModel().checkDispenseRemoveConditions();

        if(!Utilities.stringHasValue(errorMsg)){

            try {
                dispenseList.remove(getRelatedViewModel().getDispense());

                rcvDispences.getAdapter().notifyItemRemoved(position);
                rcvDispences.removeViewAt(position);
                rcvDispences.getAdapter().notifyItemRangeChanged(position, rcvDispences.getAdapter().getItemCount());

                getRelatedViewModel().deleteDispense(getRelatedViewModel().getDispense());
            } catch (SQLException e) {
                Utilities.displayAlertDialog(DispenseFragment.this.getContext(), getString(R.string.record_sucessfuly_removed)).show();
            }
        }
        else {
            Utilities.displayAlertDialog(DispenseFragment.this.getContext(),errorMsg).show();
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

    @Override
    public void onResume() {
        super.onResume();
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
    }

}