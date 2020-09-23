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
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.ClickListener;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.common.ListbleDialogListener;
import mz.org.fgh.idartlite.databinding.PrescriptionFragmentBinding;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.adapter.PrescriptionAdapter;
import mz.org.fgh.idartlite.viewmodel.PrescriptionVM;

public class PrescriptionFragment extends GenericFragment implements ListbleDialogListener {

    private RecyclerView rcvPrescriptions;
    private List<Prescription> prescriptionList;

    private PrescriptionFragmentBinding prescriptionFragmentBinding;
    private PrescriptionAdapter prescriptionAdapter;

    int prescriptionPosition;

    public PrescriptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        prescriptionFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.prescription_fragment, container,false);
        return prescriptionFragmentBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.rcvPrescriptions = prescriptionFragmentBinding.rcvPrescriptions;



        try {
            this.prescriptionList = getRelatedViewModel().gatAllOfPatient(getSelectedPatient());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (Utilities.listHasElements(prescriptionList)) {
            prescriptionAdapter = new PrescriptionAdapter(rcvPrescriptions, this.prescriptionList, getMyActivity());
            displayDataOnRecyclerView(rcvPrescriptions, prescriptionAdapter, getContext());
        }

        prescriptionFragmentBinding.newPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), PrescriptionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", getCurrentUser());
                bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                bundle.putSerializable("patient", getMyActivity().getPatient());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        rcvPrescriptions.addOnItemTouchListener(
                new ClickListener(
                        getContext(), rcvPrescriptions, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Prescription prescription = prescriptionList.get(position);
                        prescriptionPosition=position;
                        PopupMenu popup = new PopupMenu(view.getContext(),view);
                        MenuInflater inflater = popup.getMenuInflater();
                        popup.setOnMenuItemClickListener(PrescriptionFragment.this::onMenuItemClick);
                        inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
                        popup.show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Prescription prescription = prescriptionList.get(position);
                        prescriptionPosition=position;
                        PopupMenu popup = new PopupMenu(view.getContext(),view);
                        MenuInflater inflater = popup.getMenuInflater();
                        popup.setOnMenuItemClickListener(PrescriptionFragment.this::onMenuItemClick);
                        inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
                        popup.show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
                ));
    }

    /*private void displayPrescriptions(RecyclerView rcvEpisodes) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcvPrescriptions.setLayoutManager(mLayoutManager);
        rcvPrescriptions.setItemAnimator(new DefaultItemAnimator());
        rcvPrescriptions.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        prescriptionAdapter = new PrescriptionAdapter(rcvPrescriptions, this.prescriptionList, getMyActivity());
        rcvPrescriptions.setAdapter(prescriptionAdapter);

        rcvPrescriptions.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rcvPrescriptions, new RecyclerTouchListener.ClickListener() {
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
                Utilities.displayDeleteConfirmationDialogFromList(PrescriptionFragment.this.getContext(),PrescriptionFragment.this.getString(R.string.list_item_delete_msg),prescriptionPosition,PrescriptionFragment.this).show();
                return true;
            default:
                return false;
        }

    }

    @Override
    public void remove(int position) {

        if(prescriptionList.get(position).getSyncStatus().equals("R")){
            prescriptionList.remove(prescriptionList.get(position));

            for (int i = 0; i < prescriptionList.size(); i++){
                prescriptionList.get(i).setListPosition(i+1);
            }
            rcvPrescriptions.getAdapter().notifyItemRemoved(position);
            rcvPrescriptions.removeViewAt(position);
            rcvPrescriptions.getAdapter().notifyItemRangeChanged(position, rcvPrescriptions.getAdapter().getItemCount());


            try {
                getRelatedViewModel().deletePrescription(prescriptionList.get(position));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Utilities.displayAlertDialog(PrescriptionFragment.this.getContext(),"A Prescricao nao pode ser removido uma vez que ja foi sicronizado com a central").show();
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
        return new ViewModelProvider(this).get(PrescriptionVM.class);
    }

    @Override
    public PrescriptionVM getRelatedViewModel() {
        return (PrescriptionVM) super.getRelatedViewModel();
    }

    private Patient getSelectedPatient(){
        return getMyActivity().getPatient();
    }

    private void generateTestData(){

        Prescription p = new Prescription();
        p.setDispenseType(new DispenseType());
        p.getDispenseType().setDescription("Semanal");
        p.getDispenseType().setCode("SEMANAL");
        p.setPatient(getMyActivity().getPatient());
        p.setTherapeuticLine(new TherapeuticLine());
        p.getTherapeuticLine().setDescription("Linha terapeutica 1");
        p.getTherapeuticLine().setCode("Linha_Ter_1");
        p.setTherapeuticRegimen(new TherapeuticRegimen());
        p.getTherapeuticRegimen().setDescription("Regime 1");
        p.getTherapeuticRegimen().setRegimenCode("REGIME_1");
        p.setExpiryDate(DateUtilitis.getCurrentDate());
        p.setPrescriptionDate(DateUtilitis.getCurrentDate());
        p.setSyncStatus("N");

        try {
            getRelatedViewModel().create(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}