package mz.org.fgh.idartlite.view.patient;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import mz.org.fgh.idartlite.databinding.FragmentEpisodeBinding;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.adapter.EpisodeAdapter;
import mz.org.fgh.idartlite.viewmodel.EpisodeVM;

public class EpisodeFragment extends GenericFragment implements ListbleDialogListener {

    private RecyclerView rcvEpisodes;
    private List<Episode> episodeList;
    private Episode firstEpisode;
    private FragmentEpisodeBinding fragmentEpisodeBinding;
    private EpisodeAdapter episodeAdapter;
    private Dispense lastDispense;
    int position1;


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
            firstEpisode=episodeList.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (Utilities.listHasElements(episodeList)) {
            episodeAdapter = new EpisodeAdapter(this.rcvEpisodes, this.episodeList, getMyActivity(),firstEpisode,lastDispense);
            displayDataOnRecyclerView(rcvEpisodes, episodeAdapter, getContext());





            fragmentEpisodeBinding.newEpisode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getContext(), CreateEpisodeActivity.class);
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
                            Episode episode = episodeList.get(position);
                            position1=position;
                            PopupMenu popup = new PopupMenu(view.getContext(),view);
                            MenuInflater inflater = popup.getMenuInflater();
                            popup.setOnMenuItemClickListener(EpisodeFragment.this::onMenuItemClick);
                            inflater.inflate(R.menu.edit_remove_menu, popup.getMenu());
                            popup.show();
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            Episode episode = episodeList.get(position);
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
                 Intent intent = new Intent(getContext(), CreateEpisodeActivity.class);
                 Bundle bundle = new Bundle();
                 bundle.putSerializable("user", getCurrentUser());
                 bundle.putSerializable("clinic", getMyActivity().getCurrentClinic());
                 bundle.putSerializable("patient", getMyActivity().getPatient());
                 intent.putExtras(bundle);
                 startActivity(intent);
                 return true;

             case R.id.remove:

              Utilities.displayDeleteConfirmationDialogFromList(EpisodeFragment.this.getContext(),EpisodeFragment.this.getString(R.string.list_item_delete_msg),position1,EpisodeFragment.this).show();
                // Utilities.displayAlertDialog(EpisodeFragment.this.getContext(), String.valueOf(this.position1)).show();
               //  Utilities.displayDeleteConfirmationDialog(EpisodeFragment.this.getContext(),EpisodeFragment.this.getString(R.string.list_item_delete_msg),EpisodeFragment.this,EpisodeFragment.this);

                 return true;
             default:
                return false;
         }

    }






    /*private void displayPrescriptions(RecyclerView rcvEpisodes) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        this.rcvEpisodes.setLayoutManager(mLayoutManager);
        this.rcvEpisodes.setItemAnimator(new DefaultItemAnimator());
        this.rcvEpisodes.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        episodeAdapter = new EpisodeAdapter(this.rcvEpisodes, this.episodeList, getMyActivity());
        this.rcvEpisodes.setAdapter(episodeAdapter);

        this.rcvEpisodes.addOnItemTouchListener(new RecyclerTouchListener(getContext(), this.rcvEpisodes, new RecyclerTouchListener.ClickListener() {
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

    public PatientActivity getMyActivity(){
        return (PatientActivity) getActivity();
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

        if(!episodeList.get(position).getSyncStatus().equals("R")){
        episodeList.remove(episodeList.get(position));

        for (int i = 0; i < episodeList.size(); i++){
            episodeList.get(i).setListPosition(i+1);
        }
        rcvEpisodes.getAdapter().notifyItemRemoved(position);
        rcvEpisodes.removeViewAt(position);
        rcvEpisodes.getAdapter().notifyItemRangeChanged(position, rcvEpisodes.getAdapter().getItemCount());


            try {
                getRelatedViewModel().deleteEpisode(episodeList.get(position));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Utilities.displayAlertDialog(EpisodeFragment.this.getContext(),"O Episodio nao pode ser removido uma vez que ja foi sicronizado com a central").show();
        }

    }

    @Override
    public void remove(BaseModel baseModel) {

    }
}