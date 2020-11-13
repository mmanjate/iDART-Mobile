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
import mz.org.fgh.idartlite.adapter.recyclerview.episode.EpisodeAdapter;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.FragmentEpisodeBinding;
import mz.org.fgh.idartlite.listener.dialog.IListbleDialogListener;
import mz.org.fgh.idartlite.listener.recyclerView.ClickListener;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.episode.EpisodeActivity;
import mz.org.fgh.idartlite.viewmodel.episode.EpisodeVM;

public class EpisodeFragment extends GenericFragment implements IListbleDialogListener {

    public static final String FRAGMENT_CODE_EPISODE = "EpisodeFragment";

    private RecyclerView rcvEpisodes;
    private List<Episode> episodeList;
    private Episode firstEpisode;
    private FragmentEpisodeBinding fragmentEpisodeBinding;
    private EpisodeAdapter episodeAdapter;
    private Dispense lastDispense;
    int position1;
    private Episode episode;

    private boolean viewDetails;


    public EpisodeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentEpisodeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_episode, container,false);
        return fragmentEpisodeBinding.getRoot();
        
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.rcvEpisodes = fragmentEpisodeBinding.rcvEpisodes;


        try {
            this.episodeList = getRelatedViewModel().gatAllOfPatient(getSelectedPatient());
            this.lastDispense=getRelatedViewModel().getLastDispenseOfPatient(getSelectedPatient());
            if(!episodeList.isEmpty()) {
                //get First
                firstEpisode = episodeList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (Utilities.listHasElements(episodeList)) {
            episodeAdapter = new EpisodeAdapter(this.rcvEpisodes, this.episodeList, getMyActivity(),firstEpisode,lastDispense);
            displayDataOnRecyclerView(rcvEpisodes, episodeAdapter, getContext());


            fragmentEpisodeBinding.newEpisode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(getRelatedViewModel().patientHasEndingEpisode(getSelectedPatient())){
                        Utilities.displayAlertDialog(EpisodeFragment.this.getContext(),"Nao pode Criar um novo episodio para o paciente uma vez que ele ja tem um episodio de fim").show();
                        return;
                    }

                    Intent intent = new Intent(getContext(), EpisodeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", getCurrentUser());
                    bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                    bundle.putSerializable("patient", getMyActivity().getPatient());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            rcvEpisodes.addOnItemTouchListener(
                    new ClickListener(
                            getContext(), rcvEpisodes, new ClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                             episode = episodeList.get(position);
                            position1=position;
                            PopupMenu popup = new PopupMenu(view.getContext(),view);
                            MenuInflater inflater = popup.getMenuInflater();
                            popup.setOnMenuItemClickListener(EpisodeFragment.this::onMenuItemClick);
                            inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
                            popup.show();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                             episode = episodeList.get(position);
                            position1=position;
                            PopupMenu popup = new PopupMenu(view.getContext(),view);
                            MenuInflater inflater = popup.getMenuInflater();
                            popup.setOnMenuItemClickListener(EpisodeFragment.this::onMenuItemClick);
                            inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
                            popup.show();
                        }

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    }
                    ));





        }
    }



    public boolean onMenuItemClick(MenuItem item){
         switch (item.getItemId()){

             //Por retirar chamando tela de criar, por ser editado
             case R.id.edit:
                 if(episode.getSyncStatus().equals("R")) {
                     //Call activity to Edit viewDetails
                     Intent intent = new Intent(getContext(), EpisodeActivity.class);
                     Bundle bundle = new Bundle();
                     bundle.putSerializable("user", getCurrentUser());
                     bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                     bundle.putSerializable("episode", episode);
                     bundle.putSerializable("patient", getMyActivity().getPatient());
                     intent.putExtras(bundle);
                     startActivity(intent);
                     return true;
                 }
                 else {
                     Utilities.displayAlertDialog(EpisodeFragment.this.getContext(),getString(R.string.episode_cannot_be_edited_already_synchronized)).show();
                     return true;
                 }

             case R.id.remove:

              Utilities.displayDeleteConfirmationDialogFromList(EpisodeFragment.this.getContext(),EpisodeFragment.this.getString(R.string.list_item_delete_msg),position1,EpisodeFragment.this).show();
                 return true;
             case R.id.viewDetails:

                 //Call activity to Edit
                 //viewDetails=true;
                 Intent intent = new Intent(getContext(), EpisodeActivity.class);
                 Bundle bundle = new Bundle();
                 bundle.putSerializable("user", getCurrentUser());
                 bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                 bundle.putSerializable("episode", episode);
                 bundle.putSerializable("patient", getMyActivity().getPatient());
                 bundle.putSerializable("viewDetails", true);
                 intent.putExtras(bundle);
                 startActivity(intent);
             default:
                return false;
         }

    }

    @Override
    public void onResume() {
        super.onResume();
        this.rcvEpisodes = fragmentEpisodeBinding.rcvEpisodes;

        try {
            this.episodeList = getRelatedViewModel().gatAllOfPatient(getSelectedPatient());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (Utilities.listHasElements(episodeList)) {
            episodeAdapter = new EpisodeAdapter(rcvEpisodes, this.episodeList, getMyActivity(),firstEpisode,lastDispense);
            displayDataOnRecyclerView(rcvEpisodes, episodeAdapter, getContext());
        }
    }


    public PatientPanelActivity getMyActivity(){
        return (PatientPanelActivity) getActivity();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(EpisodeVM.class);
    }

    @Override
    public EpisodeVM getRelatedViewModel() {
        return (EpisodeVM) super.getRelatedViewModel();
    }

    private Patient getSelectedPatient(){
        return getMyActivity().getPatient();
    }


    @Override
    public void remove(int position) {

        if(episodeList.get(position).getSyncStatus().equals("R")){
        episodeList.remove(episodeList.get(position));

        for (int i = 0; i < episodeList.size(); i++){
            episodeList.get(i).setListPosition(i+1);
        }
        rcvEpisodes.getAdapter().notifyItemRemoved(position);
        rcvEpisodes.removeViewAt(position);
        rcvEpisodes.getAdapter().notifyItemRangeChanged(position, rcvEpisodes.getAdapter().getItemCount());

        getSelectedPatient().setEpisodes(episodeList);

            try {
                getRelatedViewModel().deleteEpisode(episode);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Utilities.displayAlertDialog(EpisodeFragment.this.getContext(),getString(R.string.episode_cannot_be_removed_already_synchronized)).show();
        }

    }

    @Override
    public void remove(BaseModel baseModel) {

    }
}