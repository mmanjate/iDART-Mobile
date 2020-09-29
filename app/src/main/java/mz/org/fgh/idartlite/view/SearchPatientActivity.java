package mz.org.fgh.idartlite.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.ClickListener;
import mz.org.fgh.idartlite.adapter.ContentListPatientAdapter;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivitySearchPatientBinding;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.view.patient.PatientActivity;
import mz.org.fgh.idartlite.viewmodel.PatientVM;

public class SearchPatientActivity extends BaseActivity {

    private RecyclerView recyclerPatient;
    private ActivitySearchPatientBinding searchPatientBinding;
    private ContentListPatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPatientBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_patient);
        searchPatientBinding.setViewModel(getRelatedViewModel());

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    public void displaySearchResult() {
        recyclerPatient = searchPatientBinding.reyclerPatient;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPatient.setLayoutManager(layoutManager);
        recyclerPatient.setHasFixedSize(true);
        recyclerPatient.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
         adapter = new ContentListPatientAdapter(recyclerPatient, getRelatedViewModel().getSearchResults(),this);
        recyclerPatient.setAdapter(adapter);
        recyclerPatient.addOnItemTouchListener(
                new ClickListener(getApplicationContext(), recyclerPatient, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        nextActivity(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        nextActivity(position);
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
                )
        );
    }

    private void nextActivity(int position) {
        Map<String, Object> params = new HashMap<>();
        params.put("patient", getRelatedViewModel().getSearchResults().get(position));
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        nextActivity(PatientActivity.class,params);
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(PatientVM.class);
    }

    @Override
    public PatientVM getRelatedViewModel() {
        return (PatientVM) super.getRelatedViewModel();
    }

    public Clinic getClinic(){
        return getRelatedViewModel().getCurrentClinic();
    }


}