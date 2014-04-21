package com.example.leafclient;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MenuImageAdapter extends BaseAdapter {
	private ImageView[] menuImg; // ��������Ҫ�������
	private Context context = null; // �����Ķ���
	private int selectedMenuImg; // ѡ�е�����

	public MenuImageAdapter(Context context, int imgIds[], int width,
			int height, int selectedMenuImg) {
		this.context = context;
		this.selectedMenuImg = selectedMenuImg;
		this.menuImg = new ImageView[imgIds.length]; // �����µ�����
		for (int x = 0; x < imgIds.length; x++) {
			this.menuImg[x] = new ImageView(this.context); // ʵ����ImageView
			this.menuImg[x].setLayoutParams(new GridView.LayoutParams(width,
					height));	// ����ͼƬ�Ĵ�С
			this.menuImg[x].setAdjustViewBounds(false); // �������߽���ʾ
			this.menuImg[x].setPadding(3, 3, 3, 3);// ���ü��
			this.menuImg[x].setImageResource(imgIds[x]);// ������ʾͼƬ
		}
	}

	@Override
	public int getCount() {
		return this.menuImg.length;
	}

	@Override
	public Object getItem(int position) {
		return this.menuImg[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imgView = null ;
		if(convertView == null) {
			imgView = this.menuImg[position] ; 
		} else {
			imgView = (ImageView) convertView ;
		}
		return imgView;
	}

	public void setFocus(int selId) {	// ����ѡ�е���ʾ
		for (int x = 0; x < this.menuImg.length; x++) {
			if (x != selId) {	// û��ѡ�е�
				this.menuImg[x].setBackgroundResource(0) ;	// �����ñ���ͼƬ
			}
		}
		this.menuImg[selId].setBackgroundResource(this.selectedMenuImg) ;
	}
}
