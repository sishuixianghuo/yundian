package com.yundian.android.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.yundian.android.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 忘记密码
 * 
 * @author ShaoZhen-PC
 *
 */
public class Activity_Forget_Password extends BaseActivity implements OnClickListener {

	private static final String Tag = "Activity_Forget_Password";

	private EditText edit_username;
	private EditText edit_password;

	private ImageView image_edit_username_delete;
	
	private TextView text_edit_verification;
	
	private Button button_next;
	private boolean password = false;
	private boolean password2 = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);

		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		image_edit_username_delete = (ImageView) findViewById(R.id.image_edit_username_delete);
		text_edit_verification = (TextView) findViewById(R.id.text_edit_verification);
		edit_username = (EditText) findViewById(R.id.edit_username);
		edit_password = (EditText) findViewById(R.id.edit_password);
		button_next = (Button) findViewById(R.id.button_next);

		image_edit_username_delete.setOnClickListener(this);
		text_edit_verification.setOnClickListener(this);
		edit_username.setOnClickListener(this);
		edit_password.setOnClickListener(this);
		button_next.setOnClickListener(this);

		findViewById(R.id.image_return).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void initView() {
		edit_username.addTextChangedListener(new EditChangedListener(1));
		edit_password.addTextChangedListener(new EditChangedListener(2));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.image_edit_username_delete:
			edit_username.setText("");
			break;
		case R.id.button_next:
			startActivity(new Intent(this,Activity_Forget_Password2.class));;
			break;
		case R.id.text_edit_verification:
			timer = new Timer();
			timer.schedule(task, 0, 1000);
			text_edit_verification.setText("10");
			text_edit_verification.setClickable(false); //设置不可点击  
			Toast.makeText(getApplicationContext(), "验证码已发送到邮箱中", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}

	}

	class EditChangedListener implements TextWatcher {
		private int editStart; // 光标开始位置
		private int id;

		public EditChangedListener(int id) {
			this.id = id;
		}

		// 输入文本之前的状态
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		// 输入文字中的状态，count是一次性输入字符数
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (id == 1) {
				if (edit_username.getSelectionStart() != 0) {
					image_edit_username_delete.setVisibility(View.VISIBLE);
					password = true;
				}else{
					password = false;
				}
			}else{
				if (edit_password.getSelectionStart() != 0) {
					password2 = true;
				}else{
					password2 = false;
				}
			}
		}

		// 输入文字后的状态
		@Override
		public void afterTextChanged(Editable s) {
			if (id == 1) {
				if (edit_username.getSelectionStart() == 0) {
					image_edit_username_delete.setVisibility(View.GONE);
					password = false;
				}else{
					password = true;
				}
				// 有中文的正则匹配邮箱
				String str1 = "^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
				String str2 = "[a-zA-Z0-9\u4e00-\u9fa5\\s]+";
				if (edit_username.getText().toString().trim().matches(str1)) {

				}
			}else{
				if (edit_password.getSelectionStart() == 0) {
					password2 = false;
				}else{
					password2 = true;
				}
			}
			if(password&password2){
				button_next.setEnabled(true);
				button_next.setBackgroundResource(R.drawable.style_login_button);
			}
		}
	};
	
	Timer timer;

    TimerTask task = new TimerTask() {

        public void run() {
            Message msg = new Message();
            msg.obj = "";
            msg.what = 0;
            handler.sendMessage(msg);
        }
    };

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                	int text = Integer.parseInt(text_edit_verification.getText().toString().trim());
                	if(text == 1){
                    	text_edit_verification.setText("获取验证码");
            			text_edit_verification.setClickable(true); //设置不可点击  
                        timer.cancel();
                	}else{
                    	text_edit_verification.setText(text-1+"");
                	}
                    break;
            }
            super.handleMessage(msg);
        }
    };

    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

}
