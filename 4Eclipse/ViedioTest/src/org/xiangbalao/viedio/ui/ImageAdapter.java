package org.xiangbalao.viedio.ui;

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context mContext;

	private List<String> images;

	public ImageAdapter(Context c, List<String> im) {
		mContext = c;
		images = im;
	}

	public int getCount() {
		return images.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			/*imageView.setLayoutParams(new GridView.LayoutParams(45, 45));
			imageView.setAdjustViewBounds(false);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);*/
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}

		// imageView.setImageResource(images[position]);
		imageView.setImageURI(Uri.parse(images.get(position)));

		return imageView;
	}

}
