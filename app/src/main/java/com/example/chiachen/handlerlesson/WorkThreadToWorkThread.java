package com.example.chiachen.handlerlesson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by chiachen on 2017/1/22.
 */

public class WorkThreadToWorkThread extends AppCompatActivity {
	private Worker mWorker;
	private Handler mHandler;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.e("class", "WorkThreadToWorkThread");
		mWorker = new Worker("Work Thread B");
		// MyHandler myHandler = new MyHandler(mWorker.getLooper());

		new Thread("Work Thread A"){
			@Override
			public void run() {
				mHandler = new MyHandler(mWorker.getLooper());//1

				// Looper.prepare();//2
				// mHandler = new MyHandler();//2

				Message message = mHandler.obtainMessage();
				message.sendToTarget();
				// Looper.loop();//2
			}
		}.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("jason chien", "onDestroy");
		mWorker.getLooper().quit();
	}

	private static class Worker implements Runnable {
		private Object lock = new Object();
		private Looper mLooper;

		public Worker(String name) {
			Thread thread = new Thread(null, this, name);
			thread.start();

			synchronized (lock) {

				while (mLooper == null) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
					}

				}
			}
		}

		@Override
		public void run() {
			synchronized (lock) {
				Looper.prepare();
				mLooper = Looper.myLooper();
				lock.notifyAll();
			}
			Looper.loop();
		}

		public Looper getLooper(){
			return mLooper;
		}
	}

	private static class MyHandler extends Handler {

		public MyHandler() {
		}

		public MyHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.e("jason chien", "message send to " + Thread.currentThread().getName());

		}
	}
}

