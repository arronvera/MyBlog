package code.vera.myblog.presenter.fragment.message;

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
        view.setAdapter(adapter);
    }
}
