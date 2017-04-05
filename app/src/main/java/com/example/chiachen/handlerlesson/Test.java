package com.example.chiachen.handlerlesson;

import android.os.Handler;

/**
 * Created by chiachen on 2017/1/22.
 */

public class Test {

	public static void main(String[] args) {
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {

					}
				});
			}
		}).start();
	}
}
