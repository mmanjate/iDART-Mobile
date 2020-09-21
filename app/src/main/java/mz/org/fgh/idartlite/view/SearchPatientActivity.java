package mz.org.fgh.idartlite.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.ClickListener;
import mz.org.fgh.idartlite.adapter.ContentListPatientAdapter;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivitySearchPatientBinding;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.PatientActivity;
import mz.org.fgh.idartlite.viewmodel.PatientVM;

public class SearchPatientActivity extends BaseActivity {

    private RecyclerView recyclerPatient;
    private List<Patient> patientList;
    private ActivitySearchPatientBinding searchPatientBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPatientBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_patient);
        searchPatientBinding.buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(Utilities.stringHasValue(searchPatientBinding.edtSearchParam.getText().toString())){
                    searPatient();
                    if(Utilities.listHasElements(patientList)){
                        try {
                            displaySearchResult();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else{

                    }
                }
            }
        });

    }

    public void searPatient(){
        try {
            patientList = getRelatedViewModel().searchPatient(searchPatientBinding.edtSearchParam.getText().toString());
            System.out.println(patientList.get(0).getFirstName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displaySearchResult() throws SQLException {
        recyclerPatient = searchPatientBinding.reyclerPatient;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPatient.setLayoutManager(layoutManager);
        recyclerPatient.setHasFixedSize(true);
        recyclerPatient.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        ContentListPatientAdapter adapter = new ContentListPatientAdapter(recyclerPatient, patientList,this);
        recyclerPatient.setAdapter(adapter);
        recyclerPatient.addOnItemTouchListener(
                new ClickListener(
                        getApplicationContext(), recyclerPatient, new ClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Patient patient = patientList.get(position);
                        Intent intent = new Intent(getApplication(), PatientActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", getCurrentUser());
                        bundle.putSerializable("patient", patient);
                        bundle.putSerializable("clinic", getCurrentClinic());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Patient patient = patientList.get(position);
                        Intent intent = new Intent(getApplication(), PatientActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", getCurrentUser());
                        bundle.putSerializable("clinic", getCurrentClinic());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
                )
        );
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