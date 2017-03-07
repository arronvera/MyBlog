package code.vera.myblog.view.other;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.adapter.SortFriendAdapter;
import code.vera.myblog.listener.OnTouchingLetterChangedListener;
import code.vera.myblog.view.base.BaseView;
import code.vera.myblog.view.widget.SlideBar;

/**
 * Created by vera on 2017/2/23 0023.
 */

public class AtSomebodyView extends BaseView {
    @BindView(R.id.lv_friends)
    ListView lvFreiends;
    @BindView(R.id.slidebar_letter)
    SlideBar slideBar;
    private SortFriendAdapter adapter;
    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }

    public void setAdapter(final SortFriendAdapter adapter) {
        lvFreiends.setAdapter(adapter);
        this.adapter=adapter;
        slideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //字母改变了
                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    lvFreiends.setSelection(position);//跳转到位置
                }
            }
        });
    }
}
