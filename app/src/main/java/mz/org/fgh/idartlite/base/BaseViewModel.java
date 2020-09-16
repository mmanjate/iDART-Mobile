package mz.org.fgh.idartlite.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import mz.org.fgh.idartlite.model.User;

public abstract class BaseViewModel  extends AndroidViewModel implements Observable {

    private PropertyChangeRegistry callbacks;
    private BaseActivity relatedActivity;

    protected User currentUser;

    public BaseActivity getRelatedActivity() {
        return relatedActivity;
    }

    public void setRelatedActivity(BaseActivity relatedActivity) {
        this.relatedActivity = relatedActivity;
    }

    public BaseViewModel(@NonNull Application application) {
        super(application);
        callbacks = new PropertyChangeRegistry();
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

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
