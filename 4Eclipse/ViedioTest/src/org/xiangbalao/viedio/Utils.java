package org.xiangbalao.viedio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class Utils {
	Context mContext;

	public Utils() {
		super();
		
	}

	public Utils(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public void copyFile(String filename,String targetPath) {
	//	File file = new File(mContext.getFilesDir(), filename);	
		File file = new File(targetPath);
		if (file.exists() && file.length() > 0) {
		} else {
			try {
				InputStream is = mContext.getAssets().open(filename);
				FileOutputStream fos = new FileOutputStream(file);
				int len = 0;
				byte[] buffer = new byte[1024];
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static boolean checkFileExists(String name) {
		boolean status;
		if (!name.equals("")) {
			File newPath = new File(name);
			status = newPath.exists();
		} else {
			status = false;
		}
		return status;
	}

}
