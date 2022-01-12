package mz.org.fgh.idartlite.adapter.recyclerview.generic;

import android.app.Activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.listener.recyclerView.IOnLoadMoreListener;

public abstract class AbstractRecycleViewAdapter<T extends BaseModel> extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    protected List<T> records = new ArrayList<>();

    protected final int VIEW_TYPE_ITEM = 0;
    protected final int VIEW_TYPE_LOADING = 1;
    protected BaseActivity activity;
    protected IOnLoadMoreListener onLoadMoreListener;
    protected boolean isLoading;
    protected int visibleThreshold = 5;
    protected int lastVisibleItem, totalItemCount;

    public AbstractRecycleViewAdapter(RecyclerView recyclerView, List<T> records, Activity activity) {
        this.records = records;
        this.activity = (BaseActivity) activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoading && linearLayoutManager.findLastCompletelyVisibleItemPosition() == records.size() - 1) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
        });
    }

    public void setOnLoadMoreListener(IOnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public BaseActivity getActivity() {
        return this.activity;
    }

    public IOnLoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return records.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public boolean isLoading() {
        return isLoading;
    }
}
