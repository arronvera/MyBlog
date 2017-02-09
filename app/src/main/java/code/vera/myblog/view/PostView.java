package code.vera.myblog.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import code.vera.myblog.R;

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
                tvNum.setText("您输入了" + temp.length() + "个字符");
                if (temp.length()>0){
                    btnUpload.setBackgroundResource(R.drawable.btn_send_shape_2);
                    btnUpload.setTextColor(Color.parseColor("#e8512e"));
                    btnUpload.setEnabled(true);
                }else {
                    btnUpload.setBackgroundResource(R.drawable.btn_send_shape);
                    btnUpload.setTextColor(Color.parseColor("#bcbcbc"));
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
}
