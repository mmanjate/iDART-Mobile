package mz.org.fgh.idartlite.view.patientPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.sql.SQLException;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.tab.patient.PatientTabAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityPatientBinding;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.about.AboutActivity;
import mz.org.fgh.idartlite.viewmodel.patient.PatientVM;


public class PatientPanelActivity extends BaseActivity {

    private PatientTabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ActivityPatientBinding patientBinding;

    private String selectedTab;

    private Integer positionRemoved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patientBinding = DataBindingUtil.setContentView(this, R.layout.activity_patient);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                getRelatedViewModel().setPatient((Patient) bundle.getSerializable("patient"));
                patientBinding.setPatient(getRelatedViewModel().getPatient());
                if (getRelatedViewModel().getPatient() == null){
                    throw new RuntimeException("Não foi seleccionado um paciente para detalhar.");
                }
                this.selectedTab = (String) bundle.getSerializable("requestedFragment");

                positionRemoved= (Integer) bundle.getSerializable("positionRemoved");
            }
        }
        try {
            getRelatedViewModel().loadPatientEpisodes();
        } catch (SQLException e) {
            e.printStackTrace();
            Utilities.displayAlertDialog(PatientPanelActivity.this, getString(R.string.error_loading_patient_data)).show();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new PatientTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new PatientDemographicFragment(), getString(R.string.general_info));
        if (!getRelatedViewModel().getPatient().isFaltosoOrAbandono()) adapter.addFragment(new EpisodeFragment(), getString(R.string.episode));
        if (!getRelatedViewModel().getPatient().isFaltosoOrAbandono()) adapter.addFragment(new ClinicInfoFragment(), getString(R.string.clinic_info));
        adapter.addFragment(new PrescriptionFragment(), getString(R.string.prescription));
        adapter.addFragment(new DispenseFragment(), getString(R.string.dispense));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        if (!getRelatedViewModel().getPatient().isFaltosoOrAbandono()) {
            tabLayout.getTabAt(0).setIcon(R.mipmap.ic_patient);
            tabLayout.getTabAt(1).setIcon(R.mipmap.ic_episode);
            tabLayout.getTabAt(2).setIcon(R.mipmap.ic_clinic_info_last);
            tabLayout.getTabAt(3).setIcon(R.mipmap.ic_precricao);
            tabLayout.getTabAt(4).setIcon(R.mipmap.ic_dispense);
        } else {
            tabLayout.getTabAt(0).setIcon(R.mipmap.ic_patient);
            tabLayout.getTabAt(1).setIcon(R.mipmap.ic_precricao);
            tabLayout.getTabAt(2).setIcon(R.mipmap.ic_dispense);
        }

        if (Utilities.stringHasValue(selectedTab) && selectedTab.equals(PrescriptionFragment.FRAGMENT_CODE_PRESCRIPTION)) {
            tabLayout.getTabAt(3).select();
        }else if(Utilities.stringHasValue(selectedTab) && selectedTab.equals(DispenseFragment.FRAGMENT_CODE_DISPENSE)){
            tabLayout.getTabAt(4).select();
        }
    }


    //Handling Action Bar button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //Back button
            case R.id.about:
                //If this activity started from other activity
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public Patient getPatient() {
        return getRelatedViewModel().getPatient();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(PatientVM.class);
    }

    @Override
    public PatientVM getRelatedViewModel() {
        return (PatientVM) super.getRelatedViewModel();
    }


    public Integer getPositionRemoved() {
        return positionRemoved;
    }

    public void setPositionRemoved(Integer positionRemoved) {
        this.positionRemoved = positionRemoved;
    }
}