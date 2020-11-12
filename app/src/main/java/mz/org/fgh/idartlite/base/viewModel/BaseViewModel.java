package mz.org.fgh.idartlite.base.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.service.BaseServiceFactory;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;

public abstract class BaseViewModel  extends AndroidViewModel implements Observable {

    private PropertyChangeRegistry callbacks;
    private BaseActivity relatedActivity;

    private boolean viewListEditButton;
    private boolean viewListRemoveButton;

    private Listble selectedListble;
    protected User currentUser;
    protected Clinic currentClinic;

    protected BaseServiceFactory baseServiceFactory;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        callbacks = new PropertyChangeRegistry();

        baseServiceFactory = BaseServiceFactory.getInstance(application);

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

    public BaseServiceFactory getBaseServiceFactory() {
        return baseServiceFactory;
    }
}
