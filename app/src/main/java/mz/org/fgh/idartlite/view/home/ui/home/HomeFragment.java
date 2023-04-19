package mz.org.fgh.idartlite.view.home.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.FragmentHomeBinding;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.viewmodel.home.HomeViewModel;


public class HomeFragment extends GenericFragment {

    private FragmentHomeBinding homeBinding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);


        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeBinding.setViewModel(getRelatedViewModel());



        if(getRelatedViewModel().getAppLastSyncDate().contains("Sem data")) {
            homeBinding.lasSyncDate.setTextColor(Color.RED);
        }
        else {
            String dateString= getRelatedViewModel().getAppLastSyncDate().substring(22,32);

            Date syncDate= DateUtilities.createDate(dateString, DateUtilities.DATE_FORMAT);

          int daysDif= (int) DateUtilities.getDaysBetween(DateUtilities.getCurrentDate(),syncDate);

          if(daysDif>2){
              homeBinding.lasSyncDate.setTextColor(Color.RED);
          }
        }



    }

    @Override
    public HomeViewModel getRelatedViewModel() {
        return (HomeViewModel) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }
}