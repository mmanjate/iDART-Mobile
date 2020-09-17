package mz.org.fgh.idartlite.view.patient;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;

public class PatientActivity extends BaseActivity {

    private PatientTabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        adapter = new PatientTabAdapter(getSupportFragmentManager());
        adapter.addFragment(new PatientDetailsFragment(), getString(R.string.general_info));
        adapter.addFragment(new PrescriptionFragment(), getString(R.string.prescription));
        adapter.addFragment(new DispenseFragment(), getString(R.string.dispense));
        adapter.addFragment(new EpisodeFragment(), getString(R.string.episode));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_pacient);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_prescription);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_dispense);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_episode);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }
}