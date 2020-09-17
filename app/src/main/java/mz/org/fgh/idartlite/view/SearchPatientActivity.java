package mz.org.fgh.idartlite.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.ContentListPatientAdapter;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivitySearchPatientBinding;
import mz.org.fgh.idartlite.generated.callback.OnClickListener;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.viewmodel.PatientVM;

public class SearchPatientActivity extends BaseActivity {

    private RecyclerView recyclerPatient;
    private List<Patient> patientList;
    private ActivitySearchPatientBinding searchPatientBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPatientBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_patient);
        try {
            displaySearchResult();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        searchPatientBinding.imageViewSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(searchPatientBinding.edtSearchParam.getText() != null){
                    searPatient();
                    if(!patientList.isEmpty()){
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
           // patientList = getRelatedViewModel().searchPatient(searchPatientBinding.edtSearchParam.getText().toString());
            patientList = getRelatedViewModel().getAllPatient();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displaySearchResult() throws SQLException {
        patientList = getRelatedViewModel().getAllPatient();
        recyclerPatient = searchPatientBinding.reyclerPatient;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPatient.setLayoutManager(layoutManager);
        recyclerPatient.setHasFixedSize(true);
        recyclerPatient.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        ContentListPatientAdapter adapter = new ContentListPatientAdapter(recyclerPatient, patientList,this);
        recyclerPatient.setAdapter(adapter);
        System.out.println("Nome da farmacia: " + patientList.get(0).getId() + " " +patientList.get(0).getFirstName());
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