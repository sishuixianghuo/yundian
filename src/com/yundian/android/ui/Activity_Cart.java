package com.yundian.android.ui;

import com.yundian.android.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 购物车
 * 
 * @author ShaoZhen-PC
 *
 */
public class Activity_Cart extends BaseActivity implements OnClickListener {

	private ImageView image_return;
	private TextView text_compile;
	private Intent mIntent;
	private ListView list_gouwuche;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		findViewById();
		initView();

	}

	@Override
	protected void findViewById() {
		image_return = (ImageView) this.findViewById(R.id.image_return);
		text_compile = (TextView) this.findViewById(R.id.text_compile);
		list_gouwuche = (ListView) findViewById(R.id.list_gouwuche);
	}

	@Override
	protected void initView() {
		image_return.setOnClickListener(this);
		text_compile.setOnClickListener(this);
		list_gouwuche.setAdapter(new myAdapter(this));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_return:
			finish();
			break;

		case R.id.text_compile:

			break;

		default:
			break;
		}

	}
	
	public final class ViewHolder{
        public Button button_reduce;
        public TextView title;
        public TextView info;
        public Button viewBtn;
    }

	public class myAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public myAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {

				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.item_cart_info, null);
				holder.button_reduce = (Button) convertView.findViewById(R.id.button_reduce);
//				holder.title = (TextView) convertView.findViewById(R.id.title);
//				holder.info = (TextView) convertView.findViewById(R.id.info);
//				holder.viewBtn = (Button) convertView.findViewById(R.id.view_btn);
				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}
			if(position == 1){
				holder.button_reduce.setSelected(false);
			}else{
				holder.button_reduce.setSelected(true);
			}
//			holder.img.setBackgroundResource((Integer) mData.get(position).get("img"));
//			holder.title.setText((String) mData.get(position).get("title"));
//			holder.info.setText((String) mData.get(position).get("info"));
//
//			holder.viewBtn.setOnClickListener(new View.OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					showInfo();
//				}
//			});

			return convertView;
		}

	}

}
