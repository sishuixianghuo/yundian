package com.yundian.android.adapter;

import java.util.List;

import com.yundian.android.R;
import com.yundian.android.bean.ImageAndText;
import com.yundian.android.bean.ViewCache;
import com.yundian.android.widgets.AsyncImageLoader;
import com.yundian.android.widgets.AsyncImageLoader.ImageCallback;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAndTextListAdapter extends ArrayAdapter<ImageAndText> {
	private GridView gridView;
	private AsyncImageLoader asyncImageLoader;

	public ImageAndTextListAdapter(Activity activity,
			List<ImageAndText> imageAndTexts, GridView gridView) {
		super(activity, 0, imageAndTexts);
		this.gridView = gridView;
		asyncImageLoader = new AsyncImageLoader();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();

		// Inflate the views from XML
		View rowView = convertView;
		ViewCache viewCache;
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.imageitem, null);
			viewCache = new ViewCache(rowView);
			rowView.setTag(viewCache);
		} else {
			viewCache = (ViewCache) rowView.getTag();
		}
		ImageAndText imageAndText = getItem(position);

		// Load the image and set it on the ImageView
		String imageUrl = imageAndText.getImageUrl();
		ImageView imageView = viewCache.getImageView();
		imageView.setTag(imageUrl);
		Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						ImageView imageViewByTag = (ImageView) gridView
								.findViewWithTag(imageUrl);
						if (imageViewByTag != null) {
							imageViewByTag.setImageDrawable(imageDrawable);
						}
					}
				});
		if (cachedImage == null) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else {
			imageView.setImageDrawable(cachedImage);
		}
		TextView textView = viewCache.getTextView();
		if(position%2 == 0){
			textView.setText(imageAndText.getText());
		}else{
			textView.setText(imageAndText.getText()+imageAndText.getText());
		}
		return rowView;
	}
}