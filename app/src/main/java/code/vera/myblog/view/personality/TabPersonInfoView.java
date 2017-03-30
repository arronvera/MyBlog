package code.vera.myblog.view.personality;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.bean.home.UserInfoBean;
import code.vera.myblog.utils.TimeUtils;
import code.vera.myblog.view.base.BaseView;

/**
 * Created by vera on 2017/3/7 0007.
 */

public class TabPersonInfoView extends BaseView {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R.id.tv_url)
    TextView tvUrl;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_create)
    TextView tvCreate;



    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
    }
    public void showInfo(UserInfoBean userInfoBean){
        tvName.setText(userInfoBean.getName());
        if (userInfoBean.getGender().equals("f")){
            tvGender.setText("女");
        }else if (userInfoBean.getGender().equals("m")){
            tvGender.setText("男");
        }else {
            tvGender.setText("未知");
        }
        tvLocation.setText(userInfoBean.getLocation());
        tvIntroduce.setText(userInfoBean.getDescription());
        tvUrl.setText(userInfoBean.getUrl());
        if (userInfoBean.getOnline_status()==0){
            tvState.setText("不在线");
        }else  if (userInfoBean.getOnline_status()==1){
            tvState.setText("在线");
        }
        tvCreate.setText(TimeUtils.dateTransfer(userInfoBean.getCreated_at()));
    }
}
