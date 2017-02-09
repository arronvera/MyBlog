package code.vera.myblog.presenter.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.adapter.TabAdapter;
import code.vera.myblog.model.CommentDetailModel;
import code.vera.myblog.presenter.PresenterActivity;
import code.vera.myblog.view.CommentDetailView;

/**
 * 评论详情
 */
public class CommentDetailActivity extends PresenterActivity<CommentDetailView,CommentDetailModel> {

    @BindView(R.id.tab_comment_detail)
    TabLayout tabLayout;
    @BindView(R.id.vp_comment_view)
    ViewPager vpComment;

    private TabAdapter adapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment_detail;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        setAdapter();
    }

    private void setAdapter() {
        adapter=new TabAdapter(getSupportFragmentManager());
        //给ViewPager设置适配器
        vpComment.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(vpComment);
        //给Tabs设置适配器
        tabLayout.setTabsFromPagerAdapter(adapter);
    }
}
