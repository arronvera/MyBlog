package code.vera.myblog.view.tab;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.adapter.RvAdapter;
import code.vera.myblog.view.base.BaseView;

/**
 * Created by vera on20172/9 0009.
 */

public class TabRepostView extends BaseView {
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;

    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        initView();

    }

    private void initView() {
        rvPhoto.setHasFixedSize(true);
        rvPhoto.setLayoutManager(new StaggeredGridLayoutManager(3,  StaggeredGridLayoutManager.VERTICAL));
    }

    public void setAdapter(RvAdapter adapter) {
        rvPhoto.setAdapter(adapter);
    }
}
