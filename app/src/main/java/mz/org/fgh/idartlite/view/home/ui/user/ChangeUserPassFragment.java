package mz.org.fgh.idartlite.view.home.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.fragment.GenericFragment;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.databinding.ChangeUserPassFragmentBinding;
import mz.org.fgh.idartlite.viewmodel.user.ChangeUserPassVM;

public class ChangeUserPassFragment extends GenericFragment {

    private ChangeUserPassFragmentBinding changeUserPassFragmentBinding;

    public static ChangeUserPassFragment newInstance() {
        return new ChangeUserPassFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        changeUserPassFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.change_user_pass_fragment, container, false);

        return changeUserPassFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeUserPassFragmentBinding.setViewModel(getRelatedViewModel());
    }



    @Override
    public ChangeUserPassVM getRelatedViewModel() {
        return (ChangeUserPassVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(ChangeUserPassVM.class);
    }
}