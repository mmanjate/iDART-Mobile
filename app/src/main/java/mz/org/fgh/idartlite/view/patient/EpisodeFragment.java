package mz.org.fgh.idartlite.view.patient;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.GenericFragment;
import mz.org.fgh.idartlite.common.RecyclerTouchListener;
import mz.org.fgh.idartlite.databinding.FragmentEpisodeBinding;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.adapter.EpisodeAdapter;
import mz.org.fgh.idartlite.viewmodel.EpisodeVM;

public class EpisodeFragment extends GenericFragment {

    private RecyclerView rcvEpisodes;
    private List<Episode> episodeList;

    private FragmentEpisodeBinding fragmentEpisodeBinding;
    private EpisodeAdapter episodeAdapter;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (Utilities.listHasElements(episodeList)) {
            episodeAdapter = new EpisodeAdapter(this.rcvEpisodes, this.episodeList, getMyActivity());
            displayDataOnRecyclerView(rcvEpisodes, episodeAdapter, getContext());
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
}