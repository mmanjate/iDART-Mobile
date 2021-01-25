package mz.org.fgh.idartlite.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ActivityIDartHomeBinding;
import mz.org.fgh.idartlite.databinding.NavHeaderMainBinding;
import mz.org.fgh.idartlite.model.ClinicSector;
import mz.org.fgh.idartlite.view.home.ui.home.HomeViewModel;

public class IDartHomeActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIDartHomeBinding homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_i_dart_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null) {

                this.getRelatedViewModel().setCurrentClinicSector((ClinicSector) bundle.getSerializable("clinicSector"));
            }
        }

        DrawerLayout drawer = homeBinding.drawerLayout;
        NavigationView navigationView = homeBinding.navView;

        NavHeaderMainBinding headerMainBinding = NavHeaderMainBinding.bind(navigationView.getHeaderView(0));
        headerMainBinding.setViewModel(getRelatedViewModel());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder( R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow) .setDrawerLayout(drawer) .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.i_dart_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public HomeViewModel getRelatedViewModel() {
        return (HomeViewModel) super.getRelatedViewModel();
    }
}