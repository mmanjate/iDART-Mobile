package mz.org.fgh.idartlite.view.home.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.spinner.listable.ListableSpinnerAdapter;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.FragmentSettingsBinding;
import mz.org.fgh.idartlite.view.home.IDartHomeActivity;
import mz.org.fgh.idartlite.viewmodel.settings.SettingsVM;


public class AppSettingsFragment extends GenericFragment {

    private FragmentSettingsBinding settingsBinding;

    private ListableSpinnerAdapter syncPeriodAdapter;
    private ListableSpinnerAdapter metadataSyncPeriodAdapter;
    private ListableSpinnerAdapter dataRemovingAdapter;

    private NotificationManagerCompat notificationManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);

        return settingsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationManager = NotificationManagerCompat.from(getContext());

        settingsBinding.setViewModel(getRelatedViewModel());

        syncPeriodAdapter = new ListableSpinnerAdapter(getMyActivity(), R.layout.simple_auto_complete_item, getRelatedViewModel().getSyncPeriodList());
        settingsBinding.spnPeriod.setAdapter(syncPeriodAdapter);
        settingsBinding.setSyncPeriodAdapter(syncPeriodAdapter);

        metadataSyncPeriodAdapter = new ListableSpinnerAdapter(getMyActivity(), R.layout.simple_auto_complete_item, getRelatedViewModel().getMetadataSyncPeriodList());
        settingsBinding.spnUpdatePeriod.setAdapter(metadataSyncPeriodAdapter);
        settingsBinding.setMetadataSyncPeriodAdapter(metadataSyncPeriodAdapter);

        dataRemovingAdapter = new ListableSpinnerAdapter(getMyActivity(), R.layout.simple_auto_complete_item, getRelatedViewModel().getDataDeletionPeriodList());
        settingsBinding.spnRemovePeriod.setAdapter(dataRemovingAdapter);
        settingsBinding.setDataRemovingAdapter(dataRemovingAdapter);
    }

    public NotificationManagerCompat getNotificationManager() {
        return notificationManager;
    }

    @Override
    protected IDartHomeActivity getMyActivity() {
        return (IDartHomeActivity) super.getMyActivity();
    }

    @Override
    public SettingsVM getRelatedViewModel() {
        return (SettingsVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(SettingsVM.class);
    }


}