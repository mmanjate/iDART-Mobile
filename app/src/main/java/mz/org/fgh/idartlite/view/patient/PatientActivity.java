package mz.org.fgh.idartlite.view.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.sql.SQLException;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityPatientBinding;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.adapter.PatientTabAdapter;
import mz.org.fgh.idartlite.viewmodel.PatientVM;


public class PatientActivity extends BaseActivity {

    private PatientTabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ActivityPatientBinding patientBinding;

    private String selectedTab;

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
            }
        }
        try {
            getRelatedViewModel().loadPatientEpisodes();
        } catch (SQLException e) {
            e.printStackTrace();
            Utilities.displayAlertDialog(PatientActivity.this, getString(R.string.error_loading_patient_data)).show();
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
        adapter.addFragment(new PatientDetailsFragment(), getString(R.string.general_info));
        adapter.addFragment(new PrescriptionFragment(), getString(R.string.prescription));
        adapter.addFragment(new DispenseFragment(), getString(R.string.dispense));
        adapter.addFragment(new EpisodeFragment(), getString(R.string.episode));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_patient);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_prescription);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_dispense);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_episode);

        if (Utilities.stringHasValue(selectedTab) && selectedTab.equals(PrescriptionFragment.FRAGMENT_CODE_PRESCRIPTION)) {
            tabLayout.getTabAt(1).select();
        }else if(Utilities.stringHasValue(selectedTab) && selectedTab.equals(DispenseFragment.FRAGMENT_CODE_DISPENSE)){
            tabLayout.getTabAt(2).select();
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
}