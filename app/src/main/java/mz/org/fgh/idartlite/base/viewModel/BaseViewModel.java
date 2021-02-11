package mz.org.fgh.idartlite.base.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.service.ServiceProvider;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.listener.dialog.IDialogListener;
import mz.org.fgh.idartlite.model.AppSettings;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicSector;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.Utilities;

public abstract class BaseViewModel  extends AndroidViewModel implements Observable, IDialogListener {

    private PropertyChangeRegistry callbacks;
    private BaseActivity relatedActivity;

    protected BaseModel relatedRecord;

    protected IBaseService relatedService;

    protected GenericFragment relatedFragment;

    private boolean viewListEditButton;
    private boolean viewListRemoveButton;

    private Listble selectedListble;
    protected User currentUser;
    protected Clinic currentClinic;

    protected List<Listble> selectedListbles;


    protected List<AppSettings> systemSettings;

    protected ClinicSector currentClinicSector;

    protected ServiceProvider serviceProvider;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        callbacks = new PropertyChangeRegistry();

        serviceProvider = ServiceProvider.getInstance(application);

        this.relatedRecord = initRecord();

        relatedService = initRelatedService();

        selectedListbles = new ArrayList<>();

        initFormData();

    }

    public boolean isCentralServerConfigured(){
        if (!Utilities.listHasElements(systemSettings)) return false;

        for (AppSettings appSettings : systemSettings){
            if (appSettings.isUrlSetting()) return true;
        }

        return false;
    }

    protected abstract IBaseService initRelatedService();

    protected abstract BaseModel initRecord();

    protected abstract void initFormData();

    public void save(){
        try {
            if (getCurrentStep().isApplicationstepCreate()){
                relatedService.save(relatedRecord);
            }else if (getCurrentStep().isApplicationStepEdit()){
                relatedService.update(relatedRecord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initEdition(Context context){
        String errors = getSelectedRecord().canBeEdited(context);

        if (Utilities.stringHasValue(errors)){
            Utilities.displayAlertDialog(context, errors);
            return;
        }else {
            getCurrentStep().changeToEdit();
        }
    }

    public void initRemotion(Context context){
        String errors = getSelectedRecord().canBeRemoved(context);

        if (Utilities.stringHasValue(errors)){
            Utilities.displayAlertDialog(context, errors);
            return;
        }else {
            getCurrentStep().changeToRemove();
        }
    }



    public BaseActivity getRelatedActivity() {
        return relatedActivity;
    }

    public void setRelatedActivity(BaseActivity relatedActivity) {
        this.relatedActivity = relatedActivity;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    protected void notifyChange() {
        callbacks.notifyCallbacks(this, 0, null);
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    protected void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }

    @Bindable
    public ApplicationStep getCurrentStep(){
        return getRelatedActivity().getApplicationStep();
    }

    public String getAppVersionNumber(){
        if (getRelatedActivity() == null) return null;
        return "iDART Mobile v"+getRelatedActivity().getAppVersionNumber();
    }

    public String getAppVersionName(){
        if (getRelatedActivity() == null) return null;
        return "iDART Mobile v"+getRelatedActivity().getAppVersionName();
    }

    public Listble getSelectedListble() {
        return selectedListble;
    }

    public void setSelectedListble(Listble selectedListble) {
        this.selectedListble = selectedListble;
    }

    public boolean isViewListEditButton() {
        return viewListEditButton;
    }

    public void setViewListEditButton(boolean viewListEditButton) {
        this.viewListEditButton = viewListEditButton;
    }

    public boolean isViewListRemoveButton() {
        return viewListRemoveButton;
    }

    public void setViewListRemoveButton(boolean viewListRemoveButton) {
        this.viewListRemoveButton = viewListRemoveButton;
    }

    @Bindable
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        notifyPropertyChanged(BR.currentUser);
    }

    @Bindable
    public Clinic getCurrentClinic() {
        return currentClinic;
    }

    public void setCurrentClinic(Clinic currentClinic) {
        this.currentClinic = currentClinic;
        notifyPropertyChanged(BR.currentClinic);
    }

    @Bindable
    public ClinicSector getCurrentClinicSector() {
        return currentClinicSector;
    }

    public void setCurrentClinicSector(ClinicSector currentClinicSector) {
        this.currentClinicSector = currentClinicSector;
        notifyPropertyChanged(BR.currentClinicSector);
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    @Bindable
    public BaseModel getRelatedRecord() {
        return relatedRecord;
    }

    @Override
    public void doOnConfirmed() {

    }

    @Override
    public void doOnDeny() {

    }

    public GenericFragment getRelatedFragment() {
        return relatedFragment;
    }

    public void setRelatedFragment(GenericFragment relatedFragment) {
        this.relatedFragment = relatedFragment;
    }

    public IBaseService getRelatedService() {
        return relatedService;
    }

    public BaseModel getSelectedRecord() {
        return relatedRecord;
    }

    public void setSelectedRecord(Serializable selectedRecord) {
        this.relatedRecord = (BaseModel) selectedRecord;
    }

    public void backToPreviusActivity(){
        getRelatedActivity().finish();
    }

    public abstract void preInit();

    public List<AppSettings> getSystemSettings(){
        return this.systemSettings;
    }

    public void setSystemSettings(List<AppSettings> systemSettings) {
        this.systemSettings = systemSettings;
    }

    public List<Listble> getSelectedListbles() {
        return selectedListbles;
    }

    public void addSelectedListable(Listble listble){
        this.selectedListbles.add(listble);
    }

    public void removeSelectedListable(Listble listble){
        this.selectedListbles.remove(listble);
    }
}
