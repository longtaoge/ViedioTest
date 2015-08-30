package org.xiangbalao.viedio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.xiangbalao.viedio.frame.Config4VideoUtils;
import org.xiangbalao.viedio.frame.VideoUtils;
import org.xiangbalao.viedio.ui.ImageAdapter;
import org.xiangbalao.viedio.utils.BitmapUtil;
import org.xiangbalao.viedio.utils.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import de.greenrobot.event.EventBus;

public class MainActivity extends Activity implements OnClickListener {

	private String videoPath;
	private String potoPath;
	private MediaController mMediaController;
	private VideoView mVideoView;
	private RelativeLayout rl;
	private Button button;
	private Button button1;
	private ImageView image;

	private GridView mGridView;

	private ImageAdapter mAdapter;

	private List<String> mThumbPath = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EventBus.getDefault().register(this);
		getPath();

		if (!Utils.checkFileExists(videoPath)) {
			Utils mUtils = new Utils(this);
			//将备用文件拷到SD卡
			mUtils.copyFile("videoviewdemo.mp4", videoPath);

		}else {
			initView();
		}

	}

	public void onEventMainThread(String event) {

		initView();
	}

	private void initView() {
		button = (Button) findViewById(R.id.button);
		button1 = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		button1.setOnClickListener(this);
		mVideoView = (VideoView) findViewById(R.id.videoView);
		image = (ImageView) findViewById(R.id.image);
		// mVideoView.setVideoPath(path);

		if (new File(videoPath).exists()) {
			mVideoView.setVideoURI(Uri.parse(videoPath));
		}

		rl = (RelativeLayout) findViewById(R.id.rl);

		mGridView = (GridView) findViewById(R.id.gridview);

		mMediaController = new MediaController(this);
		// = (MediaController) findViewById(R.id.mediaController);
		ViewGroup mvView = (ViewGroup) mMediaController.getParent();

		mvView.removeView(mMediaController);

		// mVideoView.measure(0, 0);
		//
		// int with = mVideoView.getWidth();
		//
		// if (with < 100) {
		// with = 300;
		// }
		LayoutParams lpLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		mMediaController.setLayoutParams(lpLayoutParams);

		rl.addView(mMediaController);
		mVideoView.setMediaController(mMediaController);
		mVideoView.requestFocus();

	}

	private void getPath() {
		videoPath = Config4VideoUtils.videoPath;
		potoPath = Config4VideoUtils.potoPath;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button:
			Thread mThread = new Thread() {
				@Override
				public void run() {
					// 获取帧
					VideoUtils mVideoUtils = new VideoUtils();
					// 获取帧
					mThumbPath = mVideoUtils.getFrams(videoPath, potoPath);
					if (mThumbPath.size() > 0) {
						MainActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								mAdapter = new ImageAdapter(MainActivity.this,
										mThumbPath);
								mGridView.setAdapter(mAdapter);

							}
						});

					}

					super.run();
				}
			};

			mThread.start();

			break;
		case R.id.button1:

			// 获取帧
			VideoUtils mVideoUtils = new VideoUtils();
			// 获取帧
			String potop = mVideoUtils.getFrams(videoPath, potoPath, 5);

			Bitmap mBitmap = BitmapFactory.decodeFile(potop);

			// image.setImageURI(Uri.parse(potop));

			// image.setImageBitmap(BitmapUtil.rotateBitMap(mBitmap, potop, 8));
			// 旋转图片
			String pathString = BitmapUtil.saveBitmap(
					BitmapUtil.rotateBitMap(mBitmap, potop, 8), potop);

			image.setImageURI(Uri.parse(pathString));
			break;
		default:
			break;
		}

	}

}
