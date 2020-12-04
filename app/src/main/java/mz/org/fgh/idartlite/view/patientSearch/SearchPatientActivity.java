package mz.org.fgh.idartlite.view.patientSearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import mz.org.fgh.idartlite.adapter.recyclerview.patient.ContentListPatientAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivitySearchPatientBinding;
import mz.org.fgh.idartlite.listener.recyclerView.ClickListener;
import mz.org.fgh.idartlite.listener.recyclerView.IOnLoadMoreListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.view.about.AboutActivity;
import mz.org.fgh.idartlite.view.patientPanel.PatientPanelActivity;
import mz.org.fgh.idartlite.viewmodel.patient.PatientVM;

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

        recyclerPatient = searchPatientBinding.reyclerPatient;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPatient.setLayoutManager(layoutManager);
        recyclerPatient.setHasFixedSize(true);
        recyclerPatient.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        recyclerPatient.addOnItemTouchListener(
                new ClickListener(getApplicationContext(), recyclerPatient, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        nextActivity(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) { }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
                )
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if(Utilities.stringHasValue(getRelatedViewModel().getSearchParam())) {
            getRelatedViewModel().initSearch();
            Utilities.hideSoftKeyboard(SearchPatientActivity.this);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.toolbar_items, menu);

        return true;
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

    public void displaySearchResult() {
        if (adapter == null) {
            adapter = new ContentListPatientAdapter(recyclerPatient, getRelatedViewModel().getAllDisplyedRecords(), this,false);
            recyclerPatient.setAdapter(adapter);
        }

        if (adapter.getOnLoadMoreListener() == null) {
            adapter.setOnLoadMoreListener(new IOnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    getRelatedViewModel().loadMoreRecords(recyclerPatient, adapter);
                }
            });
        }
    }



    private void nextActivity(int position) {
        Map<String, Object> params = new HashMap<>();
        params.put("patient", getRelatedViewModel().getSearchResults().get(position));
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        nextActivity(PatientPanelActivity.class,params);
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