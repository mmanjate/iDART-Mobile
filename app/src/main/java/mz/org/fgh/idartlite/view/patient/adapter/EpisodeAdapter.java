package mz.org.fgh.idartlite.view.patient.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.widget.PopupMenuCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.databinding.PatientEpisodeRowBinding;
import mz.org.fgh.idartlite.databinding.PatientPrescriptionRowBinding;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Prescription;

public class EpisodeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Episode> episodeList;

    private Episode firstEpisode;
    private Dispense lastDispense;

    public EpisodeAdapter(RecyclerView recyclerView, List<Episode> episodeList, Activity activity,Episode firstEpisode,Dispense lastDispense) {
        this.activity = activity;
        this.episodeList = episodeList;
        this.firstEpisode=firstEpisode;
        this.lastDispense=lastDispense;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PatientEpisodeRowBinding patientEpisodeRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.patient_episode_row, parent, false);
        return new EpisodeViewHolder(patientEpisodeRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((EpisodeViewHolder) viewHolder).patientEpisodeRowBinding.setEpisode(episodeList.get(position));
        ((EpisodeViewHolder) viewHolder).patientEpisodeRowBinding.setFirstEpisode(episodeList.get(0));
     ((EpisodeViewHolder) viewHolder).patientEpisodeRowBinding.setLastDispense(this.lastDispense);
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder{

        private PatientEpisodeRowBinding patientEpisodeRowBinding;


        public EpisodeViewHolder(@NonNull PatientEpisodeRowBinding patientEpisodeRowBinding) {
            super(patientEpisodeRowBinding.getRoot());
            this.patientEpisodeRowBinding = patientEpisodeRowBinding;
        }


    }


}
