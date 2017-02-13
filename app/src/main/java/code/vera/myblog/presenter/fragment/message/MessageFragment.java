package code.vera.myblog.presenter.fragment.message;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.adapter.TabMessageAdapter;
import code.vera.myblog.model.message.MessageModel;
import code.vera.myblog.presenter.base.PresenterFragment;
import code.vera.myblog.view.message.MessageView;

/**
 * 消息
 * Created by vera on 2017/1/20 0020.
 */

public class MessageFragment extends PresenterFragment<MessageView,MessageModel> {

    @BindView(R.id.tab_message_detail)
    TabLayout tabLayout;
    @BindView(R.id.vp_message_view)
    ViewPager vpMessage;

    private TabMessageAdapter adapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        setAdapter();

    }

    private void setAdapter() {
        adapter=new TabMessageAdapter(getFragmentManager());
        //给ViewPager设置适配器
        vpMessage.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来
        tabLayout.setupWithViewPager(vpMessage);
        //给Tabs设置适配器
        tabLayout.setTabsFromPagerAdapter(adapter);
    }
}
