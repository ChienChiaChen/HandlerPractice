package com.example.chiachen.handlerlesson;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
	// private Handler mHandler = new Handler(){
	// 	@Override
	// 	public void handleMessage(Message msg) {
	// 		super.handleMessage(msg);
	// 		Log.i("jason chien", "message send to " + Thread.currentThread().getName());
	//
	// 	}
	// };

	private TextView textView;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MSG_DOWNLOADED:{
					Log.e("jason","it's over: "+MSG_DOWNLOADED);
					// What to do when ready, example:
					// openFile();
					Log.e("jason", "handleMessage current thread " + Thread.currentThread().getName());
					Log.e("jason", "handleMessage current thread " + Thread.currentThread().getId());
					textView.setText("it's over");
					break;
				}
			}
		}
	};
	private final static int MSG_DOWNLOADED = 1;

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.text);

		new Thread() {
			public void run() {
				// Download image then continue
				// For Example: downloadImg("file");
				Log.e("jason", "downloading");
				Log.e("jason", "current thread " + Thread.currentThread().getName());
				Log.e("jason", "current thread " + Thread.currentThread().getId());
				for (int i=0;i<100;i++ ) {

					Log.e("jason1", "wait: " + i);
				}
				handler.sendEmptyMessage(MSG_DOWNLOADED); // public static final int MSG_DOWNLOADED = 0
			}
		}.start();

		// new Thread() {
		// 	@Override
		// 	public void run() {
		// 		try {
		// 			Thread.sleep(2);
		// 			Message message = mHandler.obtainMessage();
		// 			message.sendToTarget();
		// 		} catch (InterruptedException e) {
		// 			e.printStackTrace();
		// 		} finally {
		// 		}
		//
		// 	}
		// }.start();

		//worker thread send message to main thread
		// MyThread myThread = new MyThread(new MyHandler());
		// myThread.start();
	}

	private static class MyThread extends Thread {
		//Thread中持有MyHandler的一个弱引用
		private WeakReference<Handler> mHandler;

		public MyThread(Handler handler) {
			mHandler = new WeakReference<>(handler);
		}

		@Override
		public void run() {
			Handler handler = mHandler.get();
			if (handler != null) {
				try {
					Thread.sleep(2);
					Message message = handler.obtainMessage();
					message.sendToTarget();

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
				}
			}
		}
	}


	private static class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			Log.i("jason chien", "message send to " + Thread.currentThread().getName());
		}
	}

}
