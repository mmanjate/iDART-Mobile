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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.patient.ContentListPatientAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.databinding.ActivityNewPatientSearchBinding;
import mz.org.fgh.idartlite.listener.dialog.IDialogListener;
import mz.org.fgh.idartlite.listener.recyclerView.ClickListener;
import mz.org.fgh.idartlite.listener.recyclerView.IOnLoadMoreListener;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.about.AboutActivity;
import mz.org.fgh.idartlite.view.patientPanel.AddNewPatientActivity;
import mz.org.fgh.idartlite.view.patientPanel.PatientPanelActivity;
import mz.org.fgh.idartlite.viewmodel.patient.NewPatientSearchVM;

public class NewPatientSearchActivity extends BaseActivity implements RestResponseListener<Patient>, IDialogListener {


    private RecyclerView recyclerPatient;
    private ActivityNewPatientSearchBinding newPatientSearchBinding;
    private ContentListPatientAdapter adapter;
    private int patientPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newPatientSearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_patient_search);
        newPatientSearchBinding.setViewModel(getRelatedViewModel());

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

        recyclerPatient = newPatientSearchBinding.reyclerPatient;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPatient.setLayoutManager(layoutManager);
        recyclerPatient.setHasFixedSize(true);
        recyclerPatient.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        recyclerPatient.addOnItemTouchListener(
                new ClickListener(getApplicationContext(), recyclerPatient, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                     //   nextActivity(position);
                        changeApplicationStepToDownload();
                        download(position);
                        patientPosition=position;
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

    private void download(int position){
        try {
            if(!getRelatedViewModel().checkIfPatientExists(getRelatedViewModel().getSearchResults().get(position))){
                Utilities.displayConfirmationDialog(this, this.getString(R.string.are_sure_work_this_patient), this.getString(R.string.yes), this.getString(R.string.no), getRelatedViewModel().getRelatedActivity()).show();
            }
            else {
                nextActivity(position);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
 //       if (adapter == null) {
            adapter = new ContentListPatientAdapter(recyclerPatient, getRelatedViewModel().getAllDisplyedRecords(), this,false);
            recyclerPatient.setAdapter(adapter);
   //     }

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

        try {
            this.getRelatedViewModel().createPatientAndEpisodeIfNotExists(getRelatedViewModel().getSearchResults().get(position));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("patient", getRelatedViewModel().getSearchResults().get(position));
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        nextActivity(PatientPanelActivity.class,params);
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(NewPatientSearchVM.class);
    }

    @Override
    public NewPatientSearchVM getRelatedViewModel() {
        return (NewPatientSearchVM) super.getRelatedViewModel();
    }


    @Override
    public void doOnRestSucessResponse(String flag) {

        this.getRelatedViewModel().getSearchResults().clear();
        this.getRelatedViewModel().getAllDisplyedRecords().clear();
        if (Utilities.stringHasValue(flag)){
        //    this.getRelatedViewModel().getSearchResults().addAll(newPatients);
          //  this.getRelatedViewModel().getAllDisplyedRecords().addAll(newPatients);
            this.getRelatedViewModel().displaySearchResults();
        }
      //  if(newPatients.isEmpty()){
     //       Utilities.displayConfirmationDialog(this, this.getString(R.string.would_like_create_patient), this.getString(R.string.yes), this.getString(R.string.no), ((NewPatientSearchActivity)this)).show();

      //  }

      //  return  newPatients;
    }

    @Override
    public void doOnRestErrorResponse(String errormsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utilities.displayAlertDialog(NewPatientSearchActivity.this, errormsg, NewPatientSearchActivity.this).show();
            }
        });
    }

    @Override
    public void doOnRestSucessResponseObjects(String flag, List<Patient> newPatients) {

        this.getRelatedViewModel().getSearchResults().clear();
        this.getRelatedViewModel().getAllDisplyedRecords().clear();
        if (Utilities.stringHasValue(flag)){
            this.getRelatedViewModel().getSearchResults().addAll(newPatients);
            this.getRelatedViewModel().getAllDisplyedRecords().addAll(newPatients);
            this.getRelatedViewModel().displaySearchResults();
        }
        if(newPatients.isEmpty()){
            Utilities.displayConfirmationDialog(this, this.getString(R.string.would_like_create_patient), this.getString(R.string.yes), this.getString(R.string.no), ((NewPatientSearchActivity)this)).show();
        }

    }


    @Override
    public void doOnConfirmed() {

        if(this.getApplicationStep().isApplicationstepDownload()){
            nextActivity(patientPosition);
        }
        else {


            //     Intent intent = new Intent(getRelatedViewModel(), AddNewPatientActivity.class);
            Map<String, Object> params = new HashMap<>();
            params.put("user", getCurrentUser());
            params.put("clinic", getRelatedViewModel().getCurrentClinic());
            params.put("step", ApplicationStep.STEP_CREATE);
            //      intent.putExtras(bundle);
            nextActivityFinishingCurrent(AddNewPatientActivity.class, params);
        }
    }

    @Override
    public void doOnDeny() {

    }
}