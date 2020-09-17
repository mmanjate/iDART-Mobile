package mz.org.fgh.idartlite.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import mz.org.fgh.idartlite.model.User;

public abstract class GenericFragment extends Fragment implements GenericActivity{

    protected BaseViewModel relatedViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.relatedViewModel = initViewModel();
        if (this.relatedViewModel != null) {
            this.relatedViewModel.setRelatedActivity(getMyActivity());
            this.relatedViewModel.setCurrentUser(getMyActivity().getCurrentUser());
        }
    }

    protected BaseActivity getMyActivity(){
        return (BaseActivity) getActivity();
    }

    public BaseViewModel getRelatedViewModel() {
        return relatedViewModel;
    }

    public User getCurrentUser() {
        return getMyActivity().getCurrentUser();
    }
}
