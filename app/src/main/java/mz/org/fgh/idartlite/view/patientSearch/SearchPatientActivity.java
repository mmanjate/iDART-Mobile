package mz.org.fgh.idartlite.view.patientSearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.internal.LinkedTreeMap;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.patient.ContentListPatientAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.application.IdartLiteApplication;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivitySearchPatientBinding;
import mz.org.fgh.idartlite.listener.recyclerView.ClickListener;
import mz.org.fgh.idartlite.listener.recyclerView.IOnLoadMoreListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.view.about.AboutActivity;
import mz.org.fgh.idartlite.viewmodel.patient.PatientVM;

public class SearchPatientActivity extends BaseActivity {


    private RecyclerView recyclerPatient;
    private ActivitySearchPatientBinding searchPatientBinding;
    private ContentListPatientAdapter adapter;
    public static final String TAG = "SearchPatientActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPatientBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_patient);
        searchPatientBinding.setViewModel(getRelatedViewModel());

        if (getRelatedViewModel().getCurrentClinicSector() != null && getRelatedViewModel().getCurrentClinicSector().getClinicSectorType().getCode().contains("PROVEDOR")) {
            String url = BaseRestService.baseUrl + "/sync_temp_check_loading?mainclinicuuid=eq." + getRelatedViewModel().getCurrentClinic().getUuid();

            RESTServiceHandler handler = new RESTServiceHandler();
            handler.addHeader("Content-Type", "Application/json");
            handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                @Override
                public void onResponse(Object[] loadingStatusList) {

                    if (loadingStatusList.length > 0) {
                        for (Object loading : loadingStatusList) {
                            Log.i(TAG, "onResponse: " + loading);
                            try {
                                LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) loading;
                                Boolean isLoading = itemresult.get("isloading") != null ? Boolean.valueOf(itemresult.get("isloading").toString()) : null;
                                if (isLoading != null) {
                                    if (isLoading) {
                                        getRelatedViewModel().issueNotification("O carregamento de pacientes ainda está em curso.", IdartLiteApplication.CHANNEL_1_ID, false);

                                    } else {
                                        getRelatedViewModel().issueNotification("Carregamento de pacientes Terminado.", IdartLiteApplication.CHANNEL_1_ID, false);

                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                continue;
                            }
                        }
                    } else
                        Log.w(TAG, "Response Sem Info." + loadingStatusList.length);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Response", BaseRestService.generateErrorMsg(error));
                }
            });

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

        recyclerPatient = searchPatientBinding.reyclerPatient;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPatient.setLayoutManager(layoutManager);
        recyclerPatient.setHasFixedSize(true);
        recyclerPatient.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                getRelatedViewModel().changeReportSearchMode ((String) bundle.getSerializable("searchMode"));

            }
        }

        if (!getRelatedViewModel().isOnlineSearch()) {
            recyclerPatient.addOnItemTouchListener(
                new ClickListener(getApplicationContext(), recyclerPatient, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        nextActivity(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                })
            );
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
        if (adapter == null) {
            adapter = new ContentListPatientAdapter(recyclerPatient, getRelatedViewModel().getAllDisplyedRecords(), this,false);
            recyclerPatient.setAdapter(adapter);
        }else adapter.notifyDataSetChanged();

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
        getRelatedViewModel().goToPatientPanel(getRelatedViewModel().getSearchResults().get(position));
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