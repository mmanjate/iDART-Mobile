package mz.org.fgh.idartlite.view.reports;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.Calendar;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.ClickListener;
import mz.org.fgh.idartlite.adapter.ContentListDispenseAdapter;
import mz.org.fgh.idartlite.adapter.ContentListPatientAdapter;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.common.OnLoadMoreListener;
import mz.org.fgh.idartlite.databinding.ContentDispensesReportBinding;
import mz.org.fgh.idartlite.databinding.DispenseReportBinding;
import mz.org.fgh.idartlite.service.DispenseService;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.viewmodel.DispenseReportVM;
import mz.org.fgh.idartlite.viewmodel.PatientVM;

public class DispenseReportActivity extends BaseActivity {

    private RecyclerView recyclerDispenses;
    private DispenseReportBinding dispenseReportBinding;
    private ContentDispensesReportBinding contentDispenseReportBinding;
    private ContentListDispenseAdapter adapter;
    private DispenseService dispenseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dispenseReportBinding=   DataBindingUtil.setContentView(this, R.layout.dispense_report);
     //   contentDispenseReportBinding=DataBindingUtil.setContentView(this, R.layout.content_dispenses_report);
        dispenseService= new DispenseService(getApplication(), getCurrentUser());
        recyclerDispenses = dispenseReportBinding.reyclerPatient;

        dispenseReportBinding.setViewModel(getRelatedViewModel());

        dispenseReportBinding.executePendingBindings();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerDispenses.setLayoutManager(layoutManager);
        recyclerDispenses.setHasFixedSize(true);
        recyclerDispenses.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        if (adapter == null) {
           // try {
              //  adapter = new ContentListDispenseAdapter(recyclerDispenses, dispenseService.getAllDispenses(), this);
           // } catch (SQLException e) {
            //    e.printStackTrace();
           // }
            recyclerDispenses.setAdapter(adapter);
        }

        dispenseReportBinding.edtSearchParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(DispenseReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        dispenseReportBinding.edtSearchParam.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        dispenseReportBinding.edtSearchParam.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(DispenseReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            dispenseReportBinding.edtSearchParam.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        dispenseReportBinding.edtSearchParam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(DispenseReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        dispenseReportBinding.edtSearchParam.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        dispenseReportBinding.edtSearchParam2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    int mYear, mMonth, mDay;

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(DispenseReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            dispenseReportBinding.edtSearchParam2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

     /*   dispenseReportBinding.reyclerPatient.addOnItemTouchListener(
                new ClickListener(getApplicationContext(), recyclerDispenses, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                      //  contentDispenseReportBinding.linearDetails.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) { }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
                )
        );*/

    }

    public void displaySearchResult() {
        if (adapter == null) {
            try {
                adapter = new ContentListDispenseAdapter(recyclerDispenses, dispenseService.getDispensesBetweenStartDateAndEndDate(DateUtilitis.createDate(dispenseReportBinding.edtSearchParam.getText().toString(), DateUtilitis.DATE_FORMAT),DateUtilitis.createDate(dispenseReportBinding.edtSearchParam2.getText().toString(), DateUtilitis.DATE_FORMAT)), this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            recyclerDispenses.setAdapter(adapter);
        }


    }


    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(DispenseReportVM.class);
    }

    @Override
    public DispenseReportVM getRelatedViewModel() {
        return (DispenseReportVM) super.getRelatedViewModel();
    }
}