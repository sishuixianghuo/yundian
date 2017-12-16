package com.yundian.android.ui;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yundian.android.R;
import com.yundian.android.URLs;
import com.yundian.android.utils.LogUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 登陆
 * 
 * @author ShaoZhen-PC
 *
 */
public class Activity_Login extends BaseActivity implements OnClickListener {

	private static final String Tag = "Activity_Login";

	private Button button_register;
	private Button button_login;

	private EditText edit_username;
	private EditText edit_password;

	private ImageView image_edit_username_delete;
	private ImageView image_edit_password_delete;
	
	private TextView text_edit_password_delete;
	
	private boolean password = false;
	private boolean password2 = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		button_register = (Button) findViewById(R.id.button_register);
		button_login = (Button) findViewById(R.id.button_login);
		image_edit_username_delete = (ImageView) findViewById(R.id.image_edit_username_delete);
		image_edit_password_delete = (ImageView) findViewById(R.id.image_edit_password_delete);
		text_edit_password_delete = (TextView) findViewById(R.id.text_edit_password_delete);
		edit_username = (EditText) findViewById(R.id.edit_username);
		edit_password = (EditText) findViewById(R.id.edit_password);

		button_register.setOnClickListener(this);
		button_login.setOnClickListener(this);
		image_edit_username_delete.setOnClickListener(this);
		image_edit_password_delete.setOnClickListener(this);
		text_edit_password_delete.setOnClickListener(this);
		edit_username.setOnClickListener(this);
		edit_password.setOnClickListener(this);

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

		case R.id.button_register:
			startActivity(new Intent(this, Activity_Register.class));

			// “00” – 银联正式环境
			// “01” – 银联测试环境，该环境中不发生真实交易
			// String serverMode = "01";
			// UPPayAssistEx.startPay (this, null, null, tn, serverMode);
			break;
		case R.id.image_edit_username_delete:
			edit_username.setText("");
			break;
		case R.id.image_edit_password_delete:
			edit_password.setText("");
			break;
		case R.id.text_edit_password_delete:
			startActivity(new Intent(this,Activity_Forget_Password.class));
			break;
		case R.id.button_login:
			OkGo.<String>post(URLs.URL_LOGIN)
					.tag(this)
					.params("strUserName", "paramValue1")
					.params("strUserPwd", "paramValue1")
					.execute(new StringCallback() {
						@Override
						public void onSuccess(Response<String> response) {
							LogUtils.e(TAG, "response code: " + response.code() + " , message: " + response.message() + " , isSuccessful: " + response.isSuccessful());
//							WeiboDialogUtils.closeDialog(mWeiboDialog);
							finish();
						}

						@Override
						public void onError(Response<String> response) {
							super.onError(response);
//							WeiboDialogUtils.closeDialog(mWeiboDialog);
						}
					});
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
			} else {
				if (edit_password.getSelectionStart() != 0) {
					image_edit_password_delete.setVisibility(View.VISIBLE);
					text_edit_password_delete.setVisibility(View.GONE);
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
					password = true;
				}else{
					password = false;
				}
				// 有中文的正则匹配邮箱
				String str1 = "^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
				String str2 = "[a-zA-Z0-9\u4e00-\u9fa5\\s]+";
				if (edit_username.getText().toString().trim().matches(str1)) {

					image_edit_password_delete.setVisibility(View.VISIBLE);
					text_edit_password_delete.setVisibility(View.GONE);
				}
			} else {
				if (edit_password.getSelectionStart() == 0) {
					image_edit_password_delete.setVisibility(View.GONE);
					text_edit_password_delete.setVisibility(View.VISIBLE);
					password2 = true;
				}else{
					password2 = false;
				}
			}
			if(password&password2){
				button_register.setEnabled(true);
				button_register.setBackgroundResource(R.drawable.style_login_button);
			}
		}
	};

}
