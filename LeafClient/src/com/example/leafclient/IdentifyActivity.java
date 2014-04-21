package com.example.leafclient;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IdentifyActivity extends Activity {
	private Button fromphone = null;
	private Button fromcamera = null;
    private Bitmap alterBitmap;
    private Bitmap bitmap;
	private final static int RESULT = 0;
	private static final int CAMERA_REQUEST = 1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.indenlayout);
		
		this.fromphone = (Button) super.findViewById(R.id.fromphone);
		this.fromcamera = (Button) super.findViewById(R.id.fromcamera);
		
		this.fromphone.setOnClickListener(new GetLeafFromPhone());
		this.fromcamera.setOnClickListener(new GetLeafFromCamera());
	}
	public class GetLeafFromCamera implements OnClickListener{

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			
	        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        startActivityForResult(cameraIntent, CAMERA_REQUEST);
		}
		
	}
	
	public class GetLeafFromPhone implements OnClickListener{

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			
			Intent intent = new Intent(Intent.ACTION_PICK,
			android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, RESULT);
		}
		
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
        	if(requestCode == RESULT){
            Uri uri = data.getData();
            Display display = getWindowManager().getDefaultDisplay();
            float dw = display.getWidth();
            float dh = display.getHeight();

	            try {
	                BitmapFactory.Options options = new BitmapFactory.Options();
	                options.inJustDecodeBounds = true;
	                bitmap = BitmapFactory.decodeStream(getContentResolver()
	                        .openInputStream(uri), null, options);
	                int heightRatio = (int) Math.ceil(options.outHeight / dh);
	                int widthRatio = (int) Math.ceil(options.outWidth / dw);
	                if (heightRatio > 1 && widthRatio > 1) {
	                    if (heightRatio > widthRatio) {
	                        options.inSampleSize = heightRatio;
	                    } else {
	                        options.inSampleSize = widthRatio;
	                    }
	                }
	                options.inJustDecodeBounds = false;
	                bitmap = BitmapFactory.decodeStream(getContentResolver()
	                        .openInputStream(uri), null, options);
	                Intent it = new Intent(IdentifyActivity.this,OriginalLeafActivity.class);
	                bitmap = this.resizeImage(bitmap, 300, 400);
	                it.putExtra("original", bitmap);
	                this.startActivity(it);
	                
	            } catch (FileNotFoundException e) {
	
	                e.printStackTrace();
	            }
            
        	}else if(requestCode == CAMERA_REQUEST){
            	Bitmap photo = (Bitmap) data.getExtras().get("data");
            	photo = this.resizeImage(photo, 300, 400);
            	Intent it = new Intent(IdentifyActivity.this,OriginalLeafActivity.class);
//              it.setData(imageFileUri);
            	it.putExtra("original", photo);
              	this.startActivity(it);
            }
        	

        }
    }
	public Bitmap resizeImage(Bitmap bmp,float newWidth,float newHeight){
		
		float scaleWidth;
		float scaleHeight;
		Matrix matrix = new Matrix();
		if((bmp.getWidth()/bmp.getHeight())>=1){
			matrix.postRotate(90);
	
			scaleWidth = (newWidth)/bmp.getHeight();
			scaleHeight = (newHeight)/bmp.getWidth();
		}else{
			scaleWidth = (newWidth)/bmp.getWidth();
			scaleHeight = (newHeight)/bmp.getHeight();
		}
		
	    matrix.postScale(scaleWidth, scaleHeight);
	    // 得到新的图片
	    Bitmap newbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
	  
//		Utils.bitmapToMat(newbmp, rgbmat);
//		hsvmat = RGBtoSV(rgbmat);
		return newbmp;
}
}