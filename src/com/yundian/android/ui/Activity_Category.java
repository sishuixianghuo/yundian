package com.yundian.android.ui;

import java.util.ArrayList;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yundian.android.R;
import com.yundian.android.R.color;
import com.yundian.android.URLs;
import com.yundian.android.bean.CategoryBean;
import com.yundian.android.utils.JsonUtil;
import com.yundian.android.utils.LogUtils;
import com.yundian.android.utils.SettingUtils;
import com.yundian.android.widgets.WeiboDialogUtils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 分类
 * 
 * @author ShaoZhen-PC
 *
 */
public class Activity_Category extends BaseActivity implements OnClickListener {

	private ListView catergory_listview;
	private LayoutInflater layoutInflater;
	private LinearLayout ll_l, ll_r;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private int buttonID = 0;
	private int buttonLSum = 0;
	private Dialog mWeiboDialog;
	private CategoryBean categoryBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		buttonLSum = 5;
		setContentView(R.layout.activity_category);
		up();
		findViewById();
		initView();
	}

	private void up(){
		mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
		mWeiboDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});
		OkGo.<String>post(URLs.URL_CAT)
				.tag(this)
				.execute(new StringCallback() {
					@Override
					public void onSuccess(Response<String> response) {
						LogUtils.e(TAG, "response code: " + response.code() + " , message: " + response.message() + " , isSuccessful: " + response.isSuccessful()+ " , body: " + response.body());
							WeiboDialogUtils.closeDialog(mWeiboDialog);
						SettingUtils.set(URLs.CATEGORY_JSON,response.body());
					}

					@Override
					public void onError(Response<String> response) {
						super.onError(response);
							WeiboDialogUtils.closeDialog(mWeiboDialog);
					}
				});
	}

	@Override
	protected void findViewById() {
		ll_l = (LinearLayout) findViewById(R.id.ll_l);
		ll_r = (LinearLayout) findViewById(R.id.ll_r);

		// catergory_listview=(ListView)this.findViewById(R.id.catergory_listview);
		//
		// catergory_listview.setAdapter(new CatergorAdapter());
		// catergory_listview.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> adapterview, View view, int
		// parent,
		// long id) {
		// Toast.makeText(Activity_Category.this, "你点击了地"+id+"项", 1).show();
		//
		// }
		// });
	}

	@Override
	protected void initView() {
		String temp = SettingUtils.get(URLs.CATEGORY_JSON,"");
		if (!"".equals(temp)) {
			try {
				categoryBean = JsonUtil.jsonToBean(temp,CategoryBean.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			addLeftButton();
			addRightButton();
		}
	}

	/**
	 * 添加左侧按钮
	 */
	private void addLeftButton() {
//		String[] mTitleValues = { "通用机械配件", "专用机械配件", "整机销售", "建筑材料", "其他" };
		LogUtils.e(TAG,"categoryBean.getInfo().size() : "+categoryBean.getInfo().size());
		for (int i = 0; i < categoryBean.getInfo().size(); i++) {
			Button button = new Button(this);
			button.setOnClickListener(this);
			button.setId(buttonID);
			buttonID += 1;
			buttons.add(button);
			button.setTextSize(14);
			if (i == 0) {
				button.setBackgroundResource(color.f6f6f6);
				button.setTextColor(color.e60012);
			} else {
				button.setBackgroundResource(color.white);
				button.setTextColor(color.c3c3c3c);
			}
			// gravity
			button.setGravity(Gravity.CENTER_HORIZONTAL);
			button.setPadding(0, 20, 0, 20);
			// 设置RelativeLayout布局的宽高
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(0, 1, 0, 1);// 4个参数按顺序分别是左上右下
			button.setText(categoryBean.getInfo().get(i).getName());
			ll_l.addView(button, layoutParams);
		}

		TextView textView = new TextView(this);
		textView.setBackgroundResource(color.white);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		layoutParams.setMargins(0, 1, 0, 1);// 4个参数按顺序分别是左上右下
		ll_l.addView(textView, layoutParams);
	}

	/**
	 * 添加右侧按钮
	 */
	private void addRightButton() {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("总成件");
		list2.add("发动机,马达,发电机,气泵,水箱,散热器,变速器,变矩器,电机,增压器,车桥,驾驶室,销轴,减速机,配电柜,离合器,离合器");
		list.add(list2);
		list.add(list2);
		list.add(list2);
		list.add(list2);
		list.add(list2);
		String[] mTitleValues = { "通用机械配件", "专用机械配件", "整机销售", "建筑材料", "其他" };
		for (int i = 0; i < list.size(); i++) {
			TextView textView = new TextView(this);
			textView.setTextSize(14);
			TextPaint tp = textView.getPaint();
			tp.setFakeBoldText(true);
			textView.setTextColor(color.c3c3c3c);
			textView.setPadding(0, 20, 0, 20);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(0, 1, 0, 1);
			textView.setText(list.get(i).get(0));
			ll_r.addView(textView, layoutParams);
			String[] strArray = list.get(i).get(1).split(",");
			Log.e(TAG, "strArray.length " + strArray.length);
			for (int j = 0; j < Math.ceil(strArray.length / 3) + 1; j++) {
				Log.e(TAG, "Math.ceil(strArray.length / 3) " + Math.floor(strArray.length / 3));
				LinearLayout ll = new LinearLayout(this);
				ll.setOrientation(LinearLayout.HORIZONTAL);

				for (int z = 0; z < 3; z++) {
					Button button = new Button(this);
					button.setOnClickListener(this);
					button.setId(buttonID);
					buttonID += 1;
					buttons.add(button);
					button.setTextSize(14);
					button.setBackgroundResource(R.drawable.style_category_r_button);
					button.setTextColor(color.c3c3c3c);
					button.setGravity(Gravity.CENTER_HORIZONTAL);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
					lp.setMargins(4, 2, 2, 4);
					Log.e(TAG, "j * 3 + z " + (j * 3 + z));
					if (j * 3 + z >= strArray.length) {
						button.setVisibility(View.INVISIBLE);
						;
					} else {
						button.setText(strArray[j * 3 + z]);
					}
					ll.addView(button, lp);
				}

				ll_r.addView(ll, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			}
		}

		TextView textView = new TextView(this);
		textView.setBackgroundResource(color.white);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		layoutParams.setMargins(0, 1, 0, 1);
		ll_l.addView(textView, layoutParams);
	}

	@Override
	public void onClick(View v) {
		for (int i = 0; i < buttons.size(); i++) {
			if (v.getId() == buttons.get(i).getId()) { // 这里获取的id就不会错啦。
				if(v.getId() == 0){
					ll_r.removeAllViews();
					setLBS(v.getId());
					addRightButton();
				}else if(v.getId() == 1){
					ll_r.removeAllViews();
					setLBS(v.getId());
					addRightButton();
				}else if(v.getId() == 2){
					ll_r.removeAllViews();
					setLBS(v.getId());
					addRightButton();
				}else if(v.getId() == 3){
					ll_r.removeAllViews();
					setLBS(v.getId());
					addRightButton();
				}else if(v.getId() == 4){
					ll_r.removeAllViews();
					setLBS(v.getId());
					addRightButton();
				}else if(v.getId() == 5){
					ll_r.removeAllViews();
					setLBS(v.getId());
					addRightButton();
				}
				Toast.makeText(this, buttons.get(i).getText(), Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 * 设置左侧按钮样式
	 */
	public void setLBS(int i){
		for (int j = 0; j < buttonLSum; j++) {
			buttons.get(j).setBackgroundResource(color.white);
			buttons.get(i).setTextColor(color.c3c3c3c);
		}
		buttons.get(i).setBackgroundResource(color.f6f6f6);
		buttons.get(i).setTextColor(color.c3c3c3c);
	}

	// private class CatergorAdapter extends BaseAdapter{
	//
	// @Override
	// public int getCount() {
	// // TODO Auto-generated method stub
	// return mImageIds.length;
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// @SuppressWarnings("null")
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	//
	// ViewHolder holder=new ViewHolder();
	// layoutInflater=LayoutInflater.from(Activity_Category.this);
	//
	// //组装数据
	// if(convertView==null){
	// convertView=layoutInflater.inflate(R.layout.activity_category_item,
	// null);
	// holder.image=(ImageView) convertView.findViewById(R.id.catergory_image);
	// holder.title=(TextView)
	// convertView.findViewById(R.id.catergoryitem_title);
	// holder.content=(TextView)
	// convertView.findViewById(R.id.catergoryitem_content);
	// //使用tag存储数据
	// convertView.setTag(holder);
	// }else{
	// holder=(ViewHolder) convertView.getTag();
	// }
	// holder.image.setImageResource(mImageIds[position]);
	// holder.title.setText(mTitleValues[position]);
	// holder.content.setText(mContentValues[position]);
	// // holder.title.setText(array[position]);
	//
	// return convertView;
	//
	// }
	//
	//
	//
	// }

	// 适配显示的图片数组
	// private Integer[] mImageIds =
	// {R.drawable.catergory_appliance,R.drawable.catergory_book,R.drawable.catergory_cloth,R.drawable.catergory_deskbook,
	// R.drawable.catergory_digtcamer,R.drawable.catergory_furnitrue,R.drawable.catergory_mobile,R.drawable.catergory_skincare
	// };
	// //给照片添加文字显示(Title)
	// private String[] mTitleValues = { "家电", "图书", "衣服", "笔记本", "数码",
	// "家具", "手机", "护肤" };
	//
	// private String[] mContentValues={"家电/生活电器/厨房电器", "电子书/图书/小说","男装/女装/童装",
	// "笔记本/笔记本配件/产品外设", "摄影摄像/数码配件",
	// "家具/灯具/生活用品", "手机通讯/运营商/手机配件", "面部护理/口腔护理/..."};
	//
	//
	// public static class ViewHolder {
	// ImageView image;
	// TextView title;
	// TextView content;
	// }

}
