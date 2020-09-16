package mz.org.fgh.idartlite.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.User;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public BaseViewModel initViewModel() {
        return null;
    }
}