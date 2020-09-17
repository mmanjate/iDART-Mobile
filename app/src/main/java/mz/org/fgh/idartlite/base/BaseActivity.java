package mz.org.fgh.idartlite.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;

public abstract class BaseActivity extends AppCompatActivity implements GenericActivity {

    protected BaseViewModel relatedViewModel;

    protected User currentUser;
    protected Clinic currentClinic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                currentUser = (User) bundle.getSerializable("user");
                currentClinic = (Clinic) bundle.getSerializable("clinic");
            }
        }

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

    public Clinic getCurrentClinic() {
        return currentClinic;
    }

    public void setCurrentClinic(Clinic currentClinic) {
        this.currentClinic = currentClinic;
    }
}
