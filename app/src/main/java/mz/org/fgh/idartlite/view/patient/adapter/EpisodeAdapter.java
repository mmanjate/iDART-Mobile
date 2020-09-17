package mz.org.fgh.idartlite.view.patient.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.databinding.PatientEpisodeRowBinding;
import mz.org.fgh.idartlite.databinding.PatientPrescriptionRowBinding;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Prescription;

public class EpisodeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Episode> episodeList;

    public EpisodeAdapter(RecyclerView recyclerView, List<Episode> episodeList, Activity activity) {
        this.activity = activity;
        this.episodeList = episodeList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PatientEpisodeRowBinding patientEpisodeRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.patient_prescription_row, parent, false);
        return new EpisodeViewHolder(patientEpisodeRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((EpisodeViewHolder) viewHolder).patientEpisodeRowBinding.setEpisode(episodeList.get(position));
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
