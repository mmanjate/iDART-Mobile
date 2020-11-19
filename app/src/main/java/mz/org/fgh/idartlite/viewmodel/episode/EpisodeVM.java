package mz.org.fgh.idartlite.viewmodel.episode;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.episode.EpisodeActivity;

public class EpisodeVM extends BaseViewModel {

    private IEpisodeService episodeService;
    private IDispenseService dispenseService;
    private Patient patient;
    private Episode episode;


    public EpisodeVM(@NonNull Application application) {
        super(application);
        initNewEpisode();
        episodeService = new EpisodeService(application, getCurrentUser());
        dispenseService = new DispenseService(application, getCurrentUser());
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected <T extends BaseService> Class<T> getRecordServiceClass() {
        return null;
    }

    @Override
    protected void initFormData() {

    }

    private void initNewEpisode() {
       this.episode = new Episode();
    }

    public List<Episode> gatAllOfPatient(Patient selectedPatient) throws SQLException {
        return episodeService.getAllEpisodesByPatient(selectedPatient);
    }

    public Dispense getLastDispenseOfPatient(Patient patient) throws SQLException {
        //If Patient doesnt Have Dispenses , nao e suposto nao ter dispensas, sempre tera ao menos uma
        if(!dispenseService.getAllOfPatient(patient).isEmpty()){
            return dispenseService.getAllOfPatient(patient).get(0);
        }
        return new Dispense();
    }

    public boolean patientHasEndingEpisode(Patient patient) {
        return episodeService.patientHasEndingEpisode(patient);
    }

    public void createEpisode(Episode episode) throws SQLException {
        episodeService.createEpisode(episode);
    }
    public void deleteEpisode(Episode episode) throws SQLException {
        episodeService.deleteEpisode(episode);
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    @Override
    public EpisodeActivity getRelatedActivity() {
        return (EpisodeActivity) super.getRelatedActivity();
    }

    public void save1(){

        List<Episode> episodes=new ArrayList<>();

        getRelatedActivity().populateEpisode();

        String validationErros=episode.validateEpisodeData(getRelatedActivity());

        if(validationErros.isEmpty()){
            try {
                episodes= gatAllOfPatient(getPatient());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(DateUtilities.dateDiff(getEpisode().getEpisodeDate(),
                        episodes.get(0).getEpisodeDate(), DateUtilities.DAY_FORMAT) > 0){
                    if (getEpisode().getId() == 0) {

                        this.episode.setSanitaryUnit(episodes.get(0).getSanitaryUnit());
                        this.episode.setUsUuid(episodes.get(0).getUsUuid());

                        episode.setSyncStatus("R");
                        episode.setUuid(Utilities.getNewUUID().toString());
                        episodeService.createEpisode(this.episode);
                    } else {
                        episodeService.udpateEpisode(this.episode);
                    }

                    Utilities.displayAlertDialog( getRelatedActivity(), getRelatedActivity().getString(R.string.episode_created_sucessfuly),getRelatedActivity()).show();
                }

                else {
                    Utilities.displayAlertDialog( getRelatedActivity(),getRelatedActivity().getString(R.string.visit_date_cannot_be_before_first_episode)).show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.save_error_msg_episode)+e.getLocalizedMessage()).show();
            }
        }
        else {
            Utilities.displayAlertDialog( getRelatedActivity(),validationErros).show();
        }


    }
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
