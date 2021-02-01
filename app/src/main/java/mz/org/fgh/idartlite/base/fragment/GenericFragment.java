package mz.org.fgh.idartlite.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;

import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.activity.GenericActivity;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.listener.dialog.IListbleDialogListener;
import mz.org.fgh.idartlite.model.User;

public abstract class GenericFragment extends Fragment implements GenericActivity, IListbleDialogListener {

    protected BaseViewModel relatedViewModel;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.relatedViewModel = initViewModel();
        if (this.relatedViewModel != null) {
            this.relatedViewModel.setRelatedActivity(getMyActivity());
            this.relatedViewModel.setCurrentUser(getMyActivity().getCurrentUser());
            this.relatedViewModel.setCurrentClinic(getMyActivity().getCurrentClinic());
            this.relatedViewModel.setRelatedFragment(this);

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (this.relatedViewModel != null) {
            this.relatedViewModel.preInit();
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



    public void nextActivity(Class clazz){
        nextActivity(clazz, null, false);
    }

    public void nextActivityFinishingCurrent(Class clazz){
        nextActivity(clazz, null, true);
    }

    public void nextActivity(Class clazz, Map<String, Object> params){
        nextActivity(clazz, params, false);
    }

    public void nextActivityFinishingCurrent(Class clazz, Map<String, Object> params){
        nextActivity(clazz, params, true);
    }

    /**
     * Move from one {@link android.app.Activity} to another
     *
     * @param clazz
     * @param params
     */
    private void nextActivity(Class clazz, Map<String, Object> params, boolean finishCurrentActivity){

        Intent intent = new Intent(getContext(), clazz);
        Bundle bundle = new Bundle();

        if (params != null && params.size() > 0){
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof Serializable) {
                    bundle.putSerializable(entry.getKey(), (Serializable) entry.getValue());
                }
            }
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (finishCurrentActivity) getMyActivity().finish();
    }

    protected void displayDataOnRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter, Context context) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, 0));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void remove(int position) throws SQLException {

    }

    @Override
    public void remove(BaseModel baseModel) {

    }
}
