package org.xiangbalao.viedio.test;

import android.util.Log;
import junit.framework.TestCase;

public class StringTest extends TestCase {

	public void StringTest() {


		Log.i("StringTest", String.format("%02djjy", 3+3)); //String.format("00", "1"
		Log.i("StringTest", String.format("%02d", 67));
	}
	
}
