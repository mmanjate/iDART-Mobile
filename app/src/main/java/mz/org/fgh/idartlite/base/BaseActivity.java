package mz.org.fgh.idartlite.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mz.org.fgh.idartlite.model.User;

public abstract class BaseActivity extends AppCompatActivity implements GenericActivity {

    protected BaseViewModel relatedViewModel;

    protected User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.relatedViewModel = initViewModel();
        if (this.relatedViewModel != null) {
            this.relatedViewModel.setRelatedActivity(this);
            this.relatedViewModel.setCurrentUser(currentUser);
        }

    }

    public BaseViewModel getRelatedViewModel() {
        return relatedViewModel;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
