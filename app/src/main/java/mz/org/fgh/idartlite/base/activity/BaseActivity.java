package mz.org.fgh.idartlite.base.activity;

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
import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.view.login.LoginActivity;

/**
 * Generic class that represent all application activities
 */
public abstract class BaseActivity extends AppCompatActivity implements GenericActivity {

    /**
     * {@link BaseViewModel} Activity related viewModel
     */
    protected BaseViewModel relatedViewModel;

    /**
     * {@link ApplicationStep} application current step
     */
    private ApplicationStep applicationStep;

    /**
     * {@link PackageInfo} application info
     */
    private PackageInfo pinfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applicationStep = ApplicationStep.fastCreate(ApplicationStep.STEP_INIT);

        this.relatedViewModel = initViewModel();

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                if (this.relatedViewModel != null) {
                    this.relatedViewModel.setCurrentUser((User) bundle.getSerializable("user"));
                    this.relatedViewModel.setCurrentClinic((Clinic) bundle.getSerializable("clinic"));
                }
                applicationStep = ApplicationStep.fastCreate((String) bundle.getSerializable("step"));
            }
        }

        if (this.relatedViewModel != null) {
            this.relatedViewModel.setRelatedActivity(this);
            if (this.relatedViewModel.getCurrentUser() == null) this.relatedViewModel.setCurrentUser(new User());
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

    /**
     *
     * @return application version number
     */
    public long getAppVersionNumber(){
        return PackageInfoCompat.getLongVersionCode(pinfo);
    }

    /**
     *
     * @return application version name
     */
    public String getAppVersionName(){
        return pinfo.versionName;
    }

    /**
     * Forward from current activity to a new one passed on the param without finishing current one
     * @param clazz target activity
     */
    public void nextActivity(Class clazz){
        nextActivity(clazz, null, false);
    }

    /**
     * Forward from current activity to a new one passed on the param finishing current one
     * @param clazz target activity
     */
    public void nextActivityFinishingCurrent(Class clazz){
        nextActivity(clazz, null, true);
    }

    /**
     * Forward from current activity to a new one passed on the param without finishing current one, sending params
     *
     * @param clazz target activity
     * @param params params to be sent to the other activity
     */
    public void nextActivity(Class clazz, Map<String, Object> params){
        nextActivity(clazz, params, false);
    }

    /**
     * Forward from current activity to a new one passed on the param finishing current one, sending params
     *
     * @param clazz target activity
     * @param params params to be sent to the other activity
     */
    public void nextActivityFinishingCurrent(Class clazz, Map<String, Object> params){
        nextActivity(clazz, params, true);
    }

    /**
     * Move from one {@link android.app.Activity} to another
     *
     * @param clazz target activity
     * @param params params to be sent
     * @param finishCurrentActivity condition to finish or not the current activity
     */
    private void nextActivity(Class clazz, Map<String, Object> params, boolean finishCurrentActivity){

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
        if (finishCurrentActivity) finish();
    }

    public <T extends BaseActivity> void nextActivityWithGenericParams(Class<T> clazz){
        Map<String, Object> params = new HashMap<>();
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        nextActivity(clazz,params);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (this instanceof  LoginActivity){
            finishAffinity();
            System.exit(0);
        }else
        super.onBackPressed();
    }

    /**
     *
     * @return the related {@link BaseViewModel}
     */
    public BaseViewModel getRelatedViewModel() {
        return relatedViewModel;
    }

    /**
     *
     * @return the application current {@link User}
     */
    public User getCurrentUser() {
        if (getRelatedViewModel() == null) return null;

        return getRelatedViewModel().getCurrentUser();
    }

    /**
     * Set the current user on the related viewModel
     *
     * @param currentUser to be set
     */
    public void setCurrentUser(User currentUser) {
        this.getRelatedViewModel().setCurrentUser(currentUser);
    }

    /**
     *
     * @return the current {@link Clinic}
     */
    public Clinic getCurrentClinic() {
        if (getRelatedViewModel() == null) return null;

        return getRelatedViewModel().getCurrentClinic();
    }

    /**
     * Set the current clinic on related vewModel
     * @param currentClinic to be set
     */
    public void setCurrentClinic(Clinic currentClinic) {
        getRelatedViewModel().setCurrentClinic(currentClinic);
    }

    /**
     * Change the current {@link ApplicationStep} to {@link ApplicationStep#STEP_INIT}
     */
    protected void changeApplicationStepToInit(){
        this.applicationStep.changeToInit();
    }

    /**
     * Change the current {@link ApplicationStep} to {@link ApplicationStep#STEP_LIST}
     */
    protected void changeApplicationStepToList(){
        this.applicationStep.changeToList();
    }

    /**
     * Change the current {@link ApplicationStep} to {@link ApplicationStep#STEP_DISPLAY}
     */
    protected void changeApplicationStepToDisplay(){
        this.applicationStep.changeToDisplay();
    }

    /**
     * Change the current {@link ApplicationStep} to {@link ApplicationStep#STEP_EDIT}
     */
    protected void changeApplicationStepToEdit(){
        this.applicationStep.changeToEdit();
    }

    /**
     * Change the current {@link ApplicationStep} to {@link ApplicationStep#STEP_SAVE}
     */
    protected void changeApplicationStepToSave(){
        this.applicationStep.changeToSave();
    }

    /**
     * Change the current {@link ApplicationStep} to {@link ApplicationStep#STEP_CREATE}
     */
    protected void changeApplicationStepToCreate(){
        this.applicationStep.changetocreate();
    }

    /**
     *
     * @return the application current step
     */
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
