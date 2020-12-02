package mz.org.fgh.idartlite.view.reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.print.pdf.PrintedPdfDocument;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.util.Calendar;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.dispense.DispenseReportAdapter;
import mz.org.fgh.idartlite.adapter.recyclerview.patient.ContentListPatientAdapter;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityPatientRegisterReportBinding;
import mz.org.fgh.idartlite.databinding.ContentDispensesReportBinding;
import mz.org.fgh.idartlite.databinding.DispenseReportBinding;
import mz.org.fgh.idartlite.listener.recyclerView.IOnLoadMoreListener;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.view.about.AboutActivity;
import mz.org.fgh.idartlite.viewmodel.dispense.DispenseReportVM;
import mz.org.fgh.idartlite.viewmodel.patient.PatientRegisterReportVM;

public class PatientRegisterReportActivity extends BaseActivity {


    private RecyclerView reyclerPatient;
    private ActivityPatientRegisterReportBinding patientRegisterReportBinding;
    private ContentDispensesReportBinding contentDispenseReportBinding;
    private ContentListPatientAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patientRegisterReportBinding=   DataBindingUtil.setContentView(this, R.layout.activity_patient_register_report);

        reyclerPatient = patientRegisterReportBinding.reyclerPatient;

        patientRegisterReportBinding.setViewModel(getRelatedViewModel());

        patientRegisterReportBinding.executePendingBindings();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        reyclerPatient.setLayoutManager(layoutManager);
        reyclerPatient.setHasFixedSize(true);
        reyclerPatient.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        getApplicationStep().changeToDisplay();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        patientRegisterReportBinding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PatientRegisterReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        patientRegisterReportBinding.start.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        patientRegisterReportBinding.start.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(PatientRegisterReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            patientRegisterReportBinding.start.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        patientRegisterReportBinding.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PatientRegisterReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        patientRegisterReportBinding.end.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        patientRegisterReportBinding.end.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(PatientRegisterReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            patientRegisterReportBinding.end.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });


    }

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

            adapter = new ContentListPatientAdapter(reyclerPatient, getRelatedViewModel().getAllDisplyedRecords(), this,true);

            reyclerPatient.setAdapter(adapter);
        }

        if (adapter.getOnLoadMoreListener() == null) {
            adapter.setOnLoadMoreListener(new IOnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    getRelatedViewModel().loadMoreRecords(reyclerPatient, adapter);
                }
            });
        }



    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(PatientRegisterReportVM.class);
    }

    @Override
    public PatientRegisterReportVM getRelatedViewModel() {
        return (PatientRegisterReportVM) super.getRelatedViewModel();
    }
}