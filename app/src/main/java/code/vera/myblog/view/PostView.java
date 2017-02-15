package code.vera.myblog.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import code.vera.myblog.R;
import code.vera.myblog.config.Constants;
import code.vera.myblog.view.base.BaseView;

/**
 * Created by vera on 2017/2/9 0009.
 */

public class PostView extends BaseView {
    @BindView(R.id.et_text)
    EditText etMessage;
    @BindView(R.id.tv_text_num)
    TextView tvNum;
    @BindView(R.id.btn_post)
    Button btnUpload;
    @BindView(R.id.tv_post_title)
    TextView tvTitle;//标题
    @BindView(R.id.iv_choose_pic)
    ImageView ivChoosePic;//选择图片
    @BindView(R.id.iv_repost)
    ImageView ivRepost;

    private Context context;
    private TextWatcher textWatcher;
    private CharSequence temp;
    private int editStart;
    private int editEnd;
    @Override
    public void onAttachView(@NonNull View view) {
        super.onAttachView(view);
        context = view.getContext();
        //文字改变监听
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = etMessage.getSelectionStart();
                editEnd = etMessage.getSelectionEnd();
                tvNum.setText(temp.length()+"");
                if (temp.length()>0){
                    btnUpload.setBackgroundResource(R.drawable.btn_send_shape_2);
                    btnUpload.setTextColor(Color.parseColor("#ff8162"));
                    btnUpload.setEnabled(true);
                }else {
                    btnUpload.setBackgroundResource(R.drawable.btn_send_shape);
                    btnUpload.setTextColor(Color.parseColor("#ff8162"));
                    btnUpload.setEnabled(false);

                }
                if (temp.length() > 140) {
                    Toast.makeText(context, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                    //改变颜色
                    tvNum.setTextColor(Color.parseColor("#ff8162"));
                    s.delete(editStart - 1, editEnd);
                    int tempSelection = editStart;
                    etMessage.setText(s);
                    etMessage.setSelection(tempSelection);
                }
            }
        };
        etMessage.addTextChangedListener(textWatcher);
    }
    public String getEditStr(){
        return etMessage.getText().toString();
    }

    /*
     * 显示标题和提示
     */
    public void showTitleAndHint(int type) {
        switch (type){
            case Constants.COMMENT_TYPE:
                tvTitle.setText("发评论");
                etMessage.setHint("写评论");
                break;
            case Constants.REPOST_TYPE:
                tvTitle.setText("转发");
                etMessage.setHint("转发");
                break;
        }
    }
    public void setRepostVisible(boolean isVisible){
        if (isVisible){
            ivRepost.setVisibility(View.VISIBLE);
        }else {
            ivRepost.setVisibility(View.GONE);

        }
    }
    public EditText getEt(){
        return etMessage;
    }

}
