package com.example.homework10_14;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends Activity {

	ImageButton image1, image2;
	Button button1, button2;
	boolean flag1 = false;
	boolean flag2 = false;
	int i = -1;
	int j = -1;
	int[] picture = {R.drawable.red_light,
			R.drawable.yellow_light, R.drawable.green_light };
	private   Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==-1) {
				image1.setImageResource(R.drawable.no_light);
				i = -1;
			}else if (msg.what==-2){
				image2.setImageResource(R.drawable.no_light);
				j = -1;
			}else {
				String  tag=msg.getData().getString("tag");
				if (tag.equals("one")) {
					int getValue = msg.what % 3;
					image1.setImageResource(picture[getValue]);
				}else if (tag.equals("two")){
					int getValue = msg.what % 3;
					image2.setImageResource(picture[getValue]);
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		image1 = (ImageButton) findViewById(R.id.image1);
		image2 = (ImageButton) findViewById(R.id.image2);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!flag1) {
					button1.setText("停止");
					flag1 = true;
					new Thread(new UpdateTrafficOneThread()).start();
				} else {
					button1.setText("启动");
					flag1 = false;
				}
			}
		});

		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!flag2) {
					button2.setText("停止");
					flag2 = true;
					new Thread(new UpdateTrafficTwoThread()).start();
				} else {
					button2.setText("启动");
					flag2 = false;
				}
			}
		});

	}


	private class UpdateTrafficOneThread implements Runnable{

		@Override
		public void run() {
			Bundle bundle=new Bundle();
			bundle.putString("tag","one");
			while (flag1){
				Message message=new Message();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i++;
				message.what=i;
				message.setData(bundle);
				handler.sendMessage(message);
			}
			if (!flag1){
				handler.sendEmptyMessage(-1);
			}

		}
	}
	private class UpdateTrafficTwoThread implements Runnable{

		@Override
		public void run() {
			Bundle bundle=new Bundle();
			bundle.putString("tag","two");
			while (flag2){
				Message message=new Message();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				j++;
				message.what=j;
				message.setData(bundle);
				handler.sendMessage(message);
			}
			if (!flag2){
				handler.sendEmptyMessage(-2);
			}

		}
	}

}
