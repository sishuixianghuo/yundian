package com.yundian.android.ui;

import com.yundian.android.R;
import com.yundian.android.R.color;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 忘记密码-设置新密码
 * 
 * @author ShaoZhen-PC
 *
 */
public class Activity_Forget_Password2 extends BaseActivity implements OnClickListener {

	private static final String Tag = "Activity_Forget_Password";

	private EditText edit_password;
	private EditText edit_password2;

	private ImageView image_edit_password_delete;
	private ImageView image_edit_password_delete2;
	private ImageView image_return;
	
	private TextView text_succeed;
	private TextView text_wancheng;
	
	private RelativeLayout rl_password;
	private RelativeLayout rl_password2;
	
	private Button button_next;
	
	private boolean password = false;
	private boolean password2 = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password2);

		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		image_edit_password_delete = (ImageView) findViewById(R.id.image_edit_password_delete);
		image_edit_password_delete2 = (ImageView) findViewById(R.id.image_edit_password_delete2);
		image_return = (ImageView) findViewById(R.id.image_return);
		edit_password = (EditText) findViewById(R.id.edit_password);
		edit_password2 = (EditText) findViewById(R.id.edit_password2);
		button_next = (Button) findViewById(R.id.button_next);
		text_succeed = (TextView) findViewById(R.id.text_succeed);
		text_wancheng = (TextView) findViewById(R.id.text_wancheng);
		rl_password = (RelativeLayout) findViewById(R.id.rl_password);
		rl_password2 = (RelativeLayout) findViewById(R.id.rl_password2);

		image_edit_password_delete.setOnClickListener(this);
		image_edit_password_delete2.setOnClickListener(this);
		image_return.setOnClickListener(this);
		edit_password.setOnClickListener(this);
		edit_password2.setOnClickListener(this);
		button_next.setOnClickListener(this);
		text_wancheng.setOnClickListener(this);

	}

	@Override
	protected void initView() {
		edit_password.addTextChangedListener(new EditChangedListener(1));
		edit_password2.addTextChangedListener(new EditChangedListener(2));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.image_return:
			finish();
			break;
		case R.id.text_wancheng:
			Intent intent = new Intent(this,Activity_Personal.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.image_edit_password_delete:
			edit_password.setText("");
			break;
		case R.id.image_edit_password_delete2:
			edit_password2.setText("");
			break;
		case R.id.button_next:
			text_succeed.setVisibility(View.VISIBLE);
			text_wancheng.setVisibility(View.VISIBLE);
			rl_password.setVisibility(View.GONE);
			rl_password2.setVisibility(View.GONE);
			image_return.setVisibility(View.GONE);
			button_next.setText("去逛逛");
			button_next.setTextColor(color.wheat);
			button_next.setTextColor(color.e42626);
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
				if (edit_password.getSelectionStart() != 0) {
					image_edit_password_delete.setVisibility(View.VISIBLE);
					password = true;
				}else{
					password = false;
				}
			}else{
				if (edit_password2.getSelectionStart() != 0) {
					image_edit_password_delete2.setVisibility(View.VISIBLE);
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
				if (edit_password.getSelectionStart() == 0) {
					image_edit_password_delete.setVisibility(View.GONE);
					password = false;
				}else{
					password = true;
				}
				// 有中文的正则匹配邮箱
				String str1 = "^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
				String str2 = "[a-zA-Z0-9\u4e00-\u9fa5\\s]+";
				if (edit_password.getText().toString().trim().matches(str1)) {

				}
			}else{
				if (edit_password.getSelectionStart() == 0) {
					image_edit_password_delete2.setVisibility(View.GONE);
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
}
