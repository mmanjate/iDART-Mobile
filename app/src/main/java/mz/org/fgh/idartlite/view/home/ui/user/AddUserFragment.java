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
import mz.org.fgh.idartlite.databinding.FragmentAddUserBinding;
import mz.org.fgh.idartlite.view.home.IDartHomeActivity;
import mz.org.fgh.idartlite.viewmodel.user.UserVM;


public class AddUserFragment extends GenericFragment {

    private FragmentAddUserBinding addUserBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addUserBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_user, container, false);

        return addUserBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addUserBinding.setViewModel(getRelatedViewModel());
    }

    @Override
    protected IDartHomeActivity getMyActivity() {
        return (IDartHomeActivity) super.getMyActivity();
    }

    @Override
    public UserVM getRelatedViewModel() {
        return (UserVM) super.getRelatedViewModel();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(UserVM.class);
    }
}