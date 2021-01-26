package mz.org.fgh.idartlite.view.patientPanel;

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
import mz.org.fgh.idartlite.adapter.recyclerview.clinicInfo.ClinicInfoAdapter;
import mz.org.fgh.idartlite.adapter.recyclerview.episode.EpisodeAdapter;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.databinding.FragmentClinicInfoBinding;
import mz.org.fgh.idartlite.databinding.FragmentEpisodeBinding;
import mz.org.fgh.idartlite.listener.dialog.IListbleDialogListener;
import mz.org.fgh.idartlite.listener.recyclerView.ClickListener;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.clinicInfo.ClinicInfoActivity;
import mz.org.fgh.idartlite.view.episode.EpisodeActivity;
import mz.org.fgh.idartlite.viewmodel.clinicInfo.ClinicInfoVM;
import mz.org.fgh.idartlite.viewmodel.episode.EpisodeVM;

public class ClinicInfoFragment extends GenericFragment implements IListbleDialogListener {

    public static final String FRAGMENT_CODE_CLINIC_INFORMATION = "ClinicInfoFragment";

    private RecyclerView rcvClinicInfos;
    private List<ClinicInformation> clinicInformationsList;
    private Episode firstEpisode;
    private FragmentClinicInfoBinding fragmentClinicInfoBinding;
    private ClinicInfoAdapter clinicInfoAdapter;

    int position1;
    private ClinicInformation clinicInformation;

    private boolean viewDetails;


    public ClinicInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentClinicInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_clinic_info, container,false);
        return fragmentClinicInfoBinding.getRoot();
        
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.rcvClinicInfos = fragmentClinicInfoBinding.rcvClinicInfos;


        try {
            this.clinicInformationsList = getRelatedViewModel().gatAllOfPatient(getSelectedPatient());


        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (Utilities.listHasElements(clinicInformationsList)) {
            clinicInfoAdapter = new ClinicInfoAdapter(this.rcvClinicInfos, this.clinicInformationsList,getMyActivity());
             displayDataOnRecyclerView(rcvClinicInfos, clinicInfoAdapter, getContext());

        }
            fragmentClinicInfoBinding.newClinicInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


              //      if(getRelatedViewModel().patientHasEndingEpisode(getSelectedPatient())){
                //        Utilities.displayAlertDialog(ClinicInfoFragment.this.getContext(),"Nao pode Criar um novo episodio para o paciente uma vez que ele ja tem um episodio de fim").show();
                 //       return;
                  //  }

                    Intent intent = new Intent(getContext(), ClinicInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", getCurrentUser());
                    bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                    bundle.putSerializable("patient", getMyActivity().getPatient());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


        rcvClinicInfos.addOnItemTouchListener(
                new ClickListener(
                        getContext(), rcvClinicInfos, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        clinicInformation = clinicInformationsList.get(position);
                        position1=position;
                        PopupMenu popup = new PopupMenu(view.getContext(),view);
                        MenuInflater inflater = popup.getMenuInflater();
                        popup.setOnMenuItemClickListener(ClinicInfoFragment.this::onMenuItemClick);
                        inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
                        popup.show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        clinicInformation = clinicInformationsList.get(position);
                        position1=position;
                        PopupMenu popup = new PopupMenu(view.getContext(),view);
                        MenuInflater inflater = popup.getMenuInflater();
                        popup.setOnMenuItemClickListener(ClinicInfoFragment.this::onMenuItemClick);
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
           Intent intent = new Intent(getContext(), ClinicInfoActivity.class);
             Bundle bundle = new Bundle();
         switch (item.getItemId()){

             //Por retirar chamando tela de criar, por ser editado
             case R.id.edit:

                     //Call activity to Edit viewDetails

                     bundle.putSerializable("user", getCurrentUser());
                     bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                     bundle.putSerializable("clinicInformation", clinicInformation);
                     bundle.putSerializable("patient", getMyActivity().getPatient());
                      bundle.putSerializable("step",  ApplicationStep.STEP_EDIT);

                     intent.putExtras(bundle);
                     startActivity(intent);
                     return true;



             case R.id.remove:

              Utilities.displayDeleteConfirmationDialogFromList(ClinicInfoFragment.this.getContext(), ClinicInfoFragment.this.getString(R.string.list_item_delete_msg),position1, ClinicInfoFragment.this).show();
                 return true;
             case R.id.viewDetails:

                 //Call activity to Edit
                 //viewDetails=true;
               //  Intent intent = new Intent(getContext(), ClinicInfoActivity.class);
               //  Bundle bundle = new Bundle();
                 bundle.putSerializable("user", getCurrentUser());
                 bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                 bundle.putSerializable("clinicInformation", clinicInformation);
                 bundle.putSerializable("patient", getMyActivity().getPatient());
                 bundle.putSerializable("step",  ApplicationStep.STEP_DISPLAY);
                 intent.putExtras(bundle);
                 startActivity(intent);
             default:
                return false;
         }

    }

    @Override
    public void onResume() {
        super.onResume();
        this.rcvClinicInfos = fragmentClinicInfoBinding.rcvClinicInfos;

        try {
            this.clinicInformationsList = getRelatedViewModel().gatAllOfPatient(getSelectedPatient());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (Utilities.listHasElements(clinicInformationsList)) {
            clinicInfoAdapter = new ClinicInfoAdapter(this.rcvClinicInfos, this.clinicInformationsList,getMyActivity());
            displayDataOnRecyclerView(rcvClinicInfos, clinicInfoAdapter, getContext());
        }
    }



    public PatientPanelActivity getMyActivity(){
        return (PatientPanelActivity) getActivity();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(ClinicInfoVM.class);
    }

    @Override
    public ClinicInfoVM getRelatedViewModel() {
        return (ClinicInfoVM) super.getRelatedViewModel();
    }

    private Patient getSelectedPatient(){
        return getMyActivity().getPatient();
    }


    @Override
    public void remove(int position) {


        clinicInformationsList.remove(clinicInformationsList.get(position));

        for (int i = 0; i < clinicInformationsList.size(); i++){
            clinicInformationsList.get(i).setListPosition(i+1);
        }
        rcvClinicInfos.getAdapter().notifyItemRemoved(position);
        rcvClinicInfos.removeViewAt(position);
        rcvClinicInfos.getAdapter().notifyItemRangeChanged(position, rcvClinicInfos.getAdapter().getItemCount());

        //getSelectedPatient().set(clinicInformationsList);

            try {
                getRelatedViewModel().deleteClinicInfo(clinicInformation);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    @Override
    public void remove(BaseModel baseModel) {

    }
}