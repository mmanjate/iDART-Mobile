package mz.org.fgh.idartlite.base;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.PackageInfoCompat;

import java.io.Serializable;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.view.LoginActivity;

public abstract class BaseActivity extends AppCompatActivity implements GenericActivity {

    protected BaseViewModel relatedViewModel;

    protected User currentUser;
    protected Clinic currentClinic;
    private ApplicationStep applicationStep;

    private PackageInfo pinfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applicationStep = ApplicationStep.fastCreate(ApplicationStep.STEP_INIT);


        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                currentUser = (User) bundle.getSerializable("user");
                if (currentUser == null) currentUser = new User();

                currentClinic = (Clinic) bundle.getSerializable("clinic");
                applicationStep = ApplicationStep.fastCreate((String) bundle.getSerializable("step"));
            }
        }

        this.relatedViewModel = initViewModel();
        if (this.relatedViewModel != null) {
            this.relatedViewModel.setRelatedActivity(this);
            this.relatedViewModel.setCurrUser(currentUser);
        }


        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("mz.org.fgh.idartlite.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                nextActivity(LoginActivity.class);
                finish();
            }
        }, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public long getAppVersionNumber(){
        return PackageInfoCompat.getLongVersionCode(pinfo);
    }

    public String getAppVersionName(){
        return pinfo.versionName;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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


    public boolean isViewListEditButton() {
        return getRelatedViewModel().isViewListEditButton();
    }

    public void setViewListEditButton(boolean viewListEditButton) {
        this.relatedViewModel.setViewListEditButton(viewListEditButton);
    }

    public boolean isViewListRemoveButton() {
        return getRelatedViewModel().isViewListRemoveButton();
    }

    public void setViewListRemoveButton(boolean viewListRemoveButton) {
        this.relatedViewModel.setViewListRemoveButton(viewListRemoveButton);
    }
}
