package mz.org.fgh.idartlite.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mz.org.fgh.idartlite.common.RecyclerTouchListener;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.view.patient.adapter.EpisodeAdapter;

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

    protected void displayDataOnRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter, Context context) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        //episodeAdapter = new EpisodeAdapter(recyclerView, this.episodeList, getMyActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(context, "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(context, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
    }
}
