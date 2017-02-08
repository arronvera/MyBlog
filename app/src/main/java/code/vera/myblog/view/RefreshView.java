package code.vera.myblog.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.BindView;
import butterknife.Optional;
import code.vera.myblog.R;
import code.vera.myblog.adapter.RvAdapter;
import code.vera.myblog.listener.SimpleItemTouchListener;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * Created by vera on 2017/1/25 0025.
 */

public class RefreshView extends BaseView {
    @Nullable
    @BindView(R.id.csr_layout)
    public CustomSwipeRefreshLayout csrLayout;
    @Nullable
    @BindView(R.id.crv)
    public CustomRecyclerView crv;

    protected RvAdapter adapter;
    private View emptyView;

    private CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener
            refreshListener;


    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        if (crv != null) {
//            Debug.d("refreshview存在");
            crv.setLayoutManager(llm);
            emptyView = LayoutInflater.from(view.getContext())
                    .inflate(R.layout.layout_empty, null, false);
            ScreenUtil.scale(emptyView);

            crv.addEmpty(emptyView);

            emptyView.setVisibility(View.VISIBLE);
        }
        if (csrLayout != null)
            csrLayout.setRefreshView(crv);
        if (csrLayout != null)
            csrLayout.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout
                    .OnSwipeRefreshLayoutListener() {
                @Override
                public void onHeaderRefreshing() {
                    if (refreshListener != null) {
                        refreshListener.onHeaderRefreshing();
                    }
                }

                @Override
                public void onFooterRefreshing() {
                    if (refreshListener != null) {
                        refreshListener.onFooterRefreshing();
                    }
                }
            });


    }
    @Optional
    public void refreshFinished() {
//        hideMoreView();
        csrLayout.setRefreshFinished();
    }

    @Optional
    public void refresh() {
        csrLayout.setRefreshing(true);
    }

    @Optional
    public void refreshDelay() {
        csrLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 200);
    }

    @Optional
    public void setAdapter(RvAdapter adapter) {
        if (crv != null)
            crv.setAdapter(adapter);
        this.adapter = adapter;
    }

    @Optional
    public RvAdapter getAdapter() {
        return adapter;
    }

    //itemview监听事件
    @Optional
    public void addOnItemTouchListener() {
        crv.addOnItemTouchListener(new SimpleItemTouchListener(rootView.getContext()) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
                onItemTouchListener();
            }
        });
    }

    @Optional
    public void onItemTouchListener() {

    }

    @Optional
    public void setOnSwipeRefreshListener(CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener
                                                  refreshListener) {
        this.refreshListener = refreshListener;
    }
}
