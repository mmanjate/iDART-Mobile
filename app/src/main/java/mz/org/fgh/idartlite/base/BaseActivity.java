package mz.org.fgh.idartlite.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Map;

import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;

public abstract class BaseActivity extends AppCompatActivity implements GenericActivity {

    protected BaseViewModel relatedViewModel;

    protected User currentUser;
    protected Clinic currentClinic;
    private ApplicationStep applicationStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applicationStep = ApplicationStep.fastCreate(ApplicationStep.STEP_INIT);

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

    public void nextActivity(Class clazz){
        nextActivity(clazz, null);
    }
    /**
     * Move from one {@link android.app.Activity} to another
     *
     * @param clazz
     * @param params
     */
    public void nextActivity(Class clazz, Map<String, Object> params){

        Intent intent = new Intent(getApplication(), clazz);
        Bundle bundle = new Bundle();

        if (params != null && params.size() > 0){
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof Serializable) {
                    bundle.putSerializable(entry.getKey(), (Serializable) entry.getValue());
                }
            }
            intent.putExtras(bundle);
        }
        startActivity(intent);
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

    protected void changeApplicationStepToInit(){
        this.applicationStep.changeToInit();
    }

    protected void changeApplicationStepToList(){
        this.applicationStep.changeToList();
    }

    protected void changeApplicationStepToDisplay(){
        this.applicationStep.changeToDisplay();
    }

    protected void changeApplicationStepToEdit(){
        this.applicationStep.changeToEdit();
    }

    protected void changeApplicationStepToSave(){
        this.applicationStep.changeToSave();
    }

    protected void changeApplicationStepToCreate(){
        this.applicationStep.changetocreate();
    }

    public ApplicationStep getApplicationStep() {
        return applicationStep;
    }
}
