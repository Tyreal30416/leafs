package com.example.leafclient;


import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

public class MainActivity extends ActivityGroup {
	private GridView gridviewToolbar; // ���߲˵���
	private MenuImageAdapter menu = null; // ͼƬ������
	private LinearLayout content = null; // �������
	private int menu_img[] = new int[] { R.drawable.menu_main,
			R.drawable.menu_news, R.drawable.menu_sms, R.drawable.menu_more,
			R.drawable.menu_exit }; // ����ͼƬ����Դ
	private int width = 0; // ���ƽ���Ŀ��
	private int height = 0; // ���ƽ���ĸ߶ȣ���λ��ʾ
	private Intent intent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE); // ȡ������
		super.setContentView(R.layout.activity_main);
		this.gridviewToolbar = (GridView) super.findViewById(R.id.gridviewbar);
		this.content = (LinearLayout) super.findViewById(R.id.content);

		// ���幤������һЩ��Ϣ��ʾ
		this.gridviewToolbar.setNumColumns(this.menu_img.length); // ������Ա���ĸ���
		this.gridviewToolbar.setSelector(new ColorDrawable(Color.TRANSPARENT));
		this.gridviewToolbar.setGravity(Gravity.CENTER);
		this.gridviewToolbar.setVerticalSpacing(0);

		this.width = super.getWindowManager().getDefaultDisplay().getWidth()
				/ this.menu_img.length;
		this.height = super.getWindowManager().getDefaultDisplay().getHeight() / 8;

		this.menu = new MenuImageAdapter(this, this.menu_img, this.width,
				this.height, R.drawable.menu_selected);
		this.gridviewToolbar.setAdapter(this.menu);
		this.switchActivity(0); // ��һ����ѡ��
		this.gridviewToolbar
				.setOnItemClickListener(new OnItemClickListenerImpl());
	}

	private class OnItemClickListenerImpl implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			MainActivity.this.switchActivity(position);
		}
	}

	private void switchActivity(int id) { // �л�ѡ�еĲ���
		this.menu.setFocus(id); // ����ѡ��ͼƬ�ı���
		this.content.removeAllViews(); // ɾ�����е�����
		switch (id) {
		case 0:
			this.intent = new Intent(MainActivity.this, IndexActivity.class);
			break;
		case 1:
			this.intent = new Intent(MainActivity.this, IdentifyActivity.class);
			break;
		case 2:
			this.intent = new Intent(MainActivity.this, SearchActivity.class);
			break;
		case 3:
			this.intent = new Intent(MainActivity.this, IndexActivity.class);
			break;
		case 4:
			this.exitDialog() ;
			return;
		}
		this.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Window subActivity = super.getLocalActivityManager().startActivity(
				"subActivity", this.intent);
		this.content.addView(subActivity.getDecorView(),
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	}

	private void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(this).setIcon(R.drawable.pic_m)
				.setTitle("�����˳��� ").setMessage("��ȷ��Ҫ�˳���������")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.this.finish() ;
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.this.switchActivity(0);
					}
				}).create();

		dialog.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			this.exitDialog() ;
		}
		return false ;
	}
	
}
