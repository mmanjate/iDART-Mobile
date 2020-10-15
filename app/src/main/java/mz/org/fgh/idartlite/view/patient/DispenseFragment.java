package mz.org.fgh.idartlite.view.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.ClickListener;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.common.ListbleDialogListener;
import mz.org.fgh.idartlite.databinding.FragmentDispenseBinding;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.adapter.DispenseAdapter;
import mz.org.fgh.idartlite.viewmodel.DispenseVM;

public class DispenseFragment extends GenericFragment implements ListbleDialogListener {

    public static final String FRAGMENT_CODE_DISPENSE = "DispenseFragment";
    int dispensePosition;
    private RecyclerView rcvDispences;
    private List<Dispense> dispenseList;

    private FragmentDispenseBinding fragmentDispenseBinding;
    private DispenseAdapter dispenseAdapter;
    private List<Prescription> prescriptionList;

    public DispenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentDispenseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dispense, container, false);
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

                Patient patient = getMyActivity().getPatient();

                if (!getRelatedViewModel().patientHasEpisodioFim(patient)) {
                    Intent intent = new Intent(getContext(), CreateDispenseActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", getCurrentUser());
                    bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                    bundle.putSerializable("patient", patient);
                    bundle.putSerializable("step", ApplicationStep.STEP_CREATE);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else
                    Utilities.displayAlertDialog(DispenseFragment.this.getContext(), getActivity().getString(R.string.patient_has_final_episode_cant_create_dispense)).show();
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

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                try {
                    String editErrors = getRelatedViewModel().patientHasEndingEpisode();

                    if (Utilities.stringHasValue(editErrors)) {
                        Utilities.displayAlertDialog(DispenseFragment.this.getContext(), editErrors).show();
                    } else {

                        editErrors = getRelatedViewModel().dispenseCanBeEdited();
                        if (Utilities.stringHasValue(editErrors)) {
                            Utilities.displayAlertDialog(DispenseFragment.this.getContext(), editErrors).show();
                        } else {
                            Map<String, Object> params = new HashMap<>();
                            params.put("patient", getMyActivity().getPatient());
                            params.put("user", getCurrentUser());
                            params.put("clinic", getMyActivity().getCurrentClinic());
                            params.put("dispense", getRelatedViewModel().getDispense());
                            params.put("step", ApplicationStep.STEP_EDIT);
                            nextActivity(CreateDispenseActivity.class, params);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.remove:
                //Utilities.displayDeleteConfirmationDialogFromList(DispenseFragment.this.getContext(), DispenseFragment.this.getString(R.string.list_item_delete_msg), dispensePosition, DispenseFragment.this).show();
                this.removeDispenseConfirmation();
                return true;

            case R.id.viewDetails:
                Map<String, Object> params = new HashMap<>();
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

        String errorMsg = getRelatedViewModel().patientHasEndingEpisode();

        if (!Utilities.stringHasValue(errorMsg)) {

            errorMsg = getRelatedViewModel().checkDispenseRemoveConditions();

            if (!Utilities.stringHasValue(errorMsg)) {

                try {
                    dispenseList.remove(getRelatedViewModel().getDispense());

                    rcvDispences.getAdapter().notifyItemRemoved(position);
                    rcvDispences.removeViewAt(position);
                    rcvDispences.getAdapter().notifyItemRangeChanged(position, rcvDispences.getAdapter().getItemCount());

                    getRelatedViewModel().deleteDispense(getRelatedViewModel().getDispense());
                } catch (SQLException e) {
                    Utilities.displayAlertDialog(DispenseFragment.this.getContext(), getString(R.string.record_sucessfuly_removed)).show();
                }

            } else {
                Utilities.displayAlertDialog(DispenseFragment.this.getContext(), errorMsg).show();
            }
        } else {
            Utilities.displayAlertDialog(DispenseFragment.this.getContext(), errorMsg).show();
        }
    }

    @Override
    public void remove(BaseModel baseModel) {

    }


    public PatientActivity getMyActivity() {
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

    private Patient getSelectedPatient() {
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

    public void removeDispenseConfirmation() {

        StringBuilder dispensedDrugsList = new StringBuilder();

        String dataLevantamento = "Data Levantamento: " + DateUtilitis.formatToDDMMYYYY(getRelatedViewModel().getDispense().getPickupDate(), "/");
        String duracao = "Duração: " + Utilities.parseSupplyToLabel(getRelatedViewModel().getDispense().getSupply());
        String dataProximoLevantamento = "Data Próximo Levantamento: " + DateUtilitis.formatToDDMMYYYY(getRelatedViewModel().getDispense().getNextPickupDate(), "/");

        List<DispensedDrug> dispensedDrugs = new ArrayList<>();
        try {
            dispensedDrugs = getRelatedViewModel().findDispensedDrugsByDispenseId(getRelatedViewModel().getDispense().getId());
        } catch (SQLException ex) {
        }

        for (DispensedDrug dd : dispensedDrugs
        ) {
            Drug drug = dd.getStock().getDrug();
            dispensedDrugsList.append(drug.getDescription() + "\n");
        }

        String detalhesAviamento = DispenseFragment.this.getString(R.string.remove_dispense_drug_detail_list);
        String listaDeMedicamentosDispensados = DispenseFragment.this.getString(R.string.dispensed_drug_list);
        String gostariaDeRemoverAdispensaAnterior = DispenseFragment.this.getString(R.string.would_you_like_remove_dispense);

        String removeDispenseConfirmationMessage = detalhesAviamento + "\n\n" + dataLevantamento +
                "\n" + duracao + "\n" + dataProximoLevantamento + "\n\n" + listaDeMedicamentosDispensados + "\n"
                + dispensedDrugsList + "\n" + gostariaDeRemoverAdispensaAnterior;

        Utilities.displayDeleteConfirmationDialogFromList(DispenseFragment.this.getContext(), removeDispenseConfirmationMessage, dispensePosition, DispenseFragment.this).show();
    }

}