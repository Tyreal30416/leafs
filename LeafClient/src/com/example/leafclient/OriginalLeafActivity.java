package com.example.leafclient;

import java.io.FileNotFoundException;
import java.io.OutputStream;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class OriginalLeafActivity extends Activity implements OnTouchListener{
	private ImageView img = null;
	private Paint paint;
    private Canvas canvas;
    private Bitmap bitmap;
    private Bitmap alterBitmap;
    private Button autocut = null;
    private Button cutfinish = null;
    private Button cancel = null;
    private ImageView original = null;
	private ImageView emresult = null;
	private Bitmap bit;
	private int height = 350;
	private int width = 250;
	
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		
		public void onManagerConnected(int status){
			switch(status){
			
			case LoaderCallbackInterface.SUCCESS:
			{
			}break;
			default:{  
                super.onManagerConnected(status);  
            } break;  
			}
			
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.dealleaflayout);
		//System.out.println("h");
		
		img = (ImageView) super.findViewById(R.id.originalleaf);
		autocut = (Button) super.findViewById(R.id.autocut);
		cutfinish = (Button) super.findViewById(R.id.cutfinish);
		cancel = (Button) super.findViewById(R.id.cancel);
		
		original = (ImageView) super.findViewById(R.id.original);
		emresult = (ImageView) super.findViewById(R.id.emresult);
		
		cutfinish.setOnClickListener(new CutFinishListener());
		cancel.setOnClickListener(new CancelListener());
		autocut.setOnClickListener(new AutocutListener());
		
		Intent it = super.getIntent();
		bitmap = (Bitmap)it.getParcelableExtra("original");
		bitmap = this.resizeImage(bitmap, width, height);
        alterBitmap = Bitmap.createBitmap(bitmap.getWidth(),
              bitmap.getHeight(), bitmap.getConfig());
        canvas = new Canvas(alterBitmap);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, paint);
        img.setImageBitmap(alterBitmap);
        img.setOnTouchListener(this);
	}
	public class AutocutListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			int th_size=3;
//			Size size=new Size(2*th_size + 1, 2*th_size+1);
//			Point point=new Point(th_size,th_size);
//			Mat sample = new Mat(OriginalLeafActivity.this.height,OriginalLeafActivity.this.width,CvType.CV_32FC3);
//			Mat dst = new Mat(OriginalLeafActivity.this.height,OriginalLeafActivity.this.width,CvType.CV_32FC3);
//			Utils.bitmapToMat(bitmap, sample);
//			Mat kernel=Imgproc.getStructuringElement( Imgproc.MORPH_CROSS,size,point);
//			Imgproc.morphologyEx(sample,dst,org.opencv.imgproc.Imgproc.MORPH_TOPHAT,kernel);
//			//System.out.println(dstSample.cols());
//			Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(),
//		              bitmap.getHeight(), bitmap.getConfig());
//			Utils.matToBitmap(dst, bmp);
//			final ProgressDialog diag = new ProgressDialog(OriginalLeafActivity.this);
//			diag.setTitle("正在对叶子进行分割操作。。。。");
//			diag.setMessage("少女祈祷中...");
//			diag.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			diag.setButton("Runing background", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					diag.dismiss();
//				}
//			});
//			diag.setButton2("Cancel", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					// TODO Auto-generated method stub
//					diag.cancel();
//				}
//			});
//			OriginalLeafActivity.this.bit = null;
//			new Thread(){
////				Handler uihandler;  
////		        Handler wifehandler = handler; 
//		        
//				public void run(){
//					//for(int i = 0 ; i <100 ; i ++){
//						String dis = "";
//						
//						try {
//							
//							try {//System.out.println("show");
//								CalcEm calcem = new CalcEm(OriginalLeafActivity.this.alterBitmap,400,300);
//								OriginalLeafActivity.this.bit = calcem.getEmResult();
//								
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//								System.out.println("error1");
//							}
//							//Thread.sleep(500);
//							
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//							System.out.println("error2");
////						}finally{
////							diag.dismiss();
////						}
//						}
//						//diag.incrementProgressBy(1);
//					//}
//						
//					diag.dismiss();
//					while(OriginalLeafActivity.this.bit==null){}
					Intent it = new Intent(OriginalLeafActivity.this,EMActivity.class);
		            it.putExtra("original2", OriginalLeafActivity.this.bitmap);
		            it.putExtra("tophat", "tophat");
	               
//	                it.putExtra("EM", OriginalLeafActivity.this.bit);
	                OriginalLeafActivity.this.startActivity(it);
//				}
//			}.start();
//			diag.show();
			
			
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
		  
//			Utils.bitmapToMat(newbmp, rgbmat);
//			hsvmat = RGBtoSV(rgbmat);
			return newbmp;
	}
	public class CancelListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Matrix matrix = new Matrix();
            canvas.drawBitmap(bitmap, matrix, paint);
			canvas = new Canvas(alterBitmap);
			img.setImageBitmap(alterBitmap);
		}
		
	}
	public class CutFinishListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(alterBitmap!=null){
//				final ProgressDialog diag = new ProgressDialog(OriginalLeafActivity.this);
//				diag.setTitle("正在对叶子进行分割操作。。。。");
//				diag.setMessage("少女祈祷中...");
//				diag.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//				//diag.setProgress(30);
//				//diag.setMax(100);
//				
//				diag.setButton("Runing background", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						diag.dismiss();
//					}
//				});
//				diag.setButton2("Cancel", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						diag.cancel();
//					}
//				});
//				OriginalLeafActivity.this.bit = null;
//				new Thread(){
////					Handler uihandler;  
////			        Handler wifehandler = handler; 
//			        
//					public void run(){
//						//for(int i = 0 ; i <100 ; i ++){
//							String dis = "";
//							
//							try {
//								
//								try {//System.out.println("show");
//									CalcEm calcem = new CalcEm(OriginalLeafActivity.this.alterBitmap,400,300);
//									OriginalLeafActivity.this.bit = calcem.getEmResult();
//									
//								} catch (Exception e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//									System.out.println("error1");
//								}
//								//Thread.sleep(500);
//								
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//								System.out.println("error2");
////							}finally{
////								diag.dismiss();
////							}
//							}
//							//diag.incrementProgressBy(1);
//						//}
//							
//						diag.dismiss();
//						while(OriginalLeafActivity.this.bit==null){}
		                Intent it = new Intent(OriginalLeafActivity.this,EMActivity.class);
		                it.putExtra("original2", OriginalLeafActivity.this.alterBitmap);
		                it.putExtra("tophat", "notophat");
		               
//		                it.putExtra("EM", OriginalLeafActivity.this.bit);
		                OriginalLeafActivity.this.startActivity(it);
//					}
//				}.start();
//				diag.show();
				
				
            }
		}
		
		
	}
	private float downx = 0;
    private float downy = 0;
    private float upx = 0;
    private float upy = 0;
	public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            downx = event.getX();
            downy = event.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            // 路径画板
        	
            upx = event.getX();
            upy = event.getY();
//            System.out.println(upx+" "+upy);
            canvas.drawLine(downx, downy, upx, upy, paint);
            img.invalidate();
            downx = upx;
            downy = upy;
            break;
        case MotionEvent.ACTION_UP:
            // 直线画板

            upx = event.getX();
            upy = event.getY();
            canvas.drawLine(downx, downy, upx, upy, paint);
            img.invalidate();// 刷新
            break;

        default:
            break;
        }

        return true;
    }
	@Override  
    public void onResume(){  
        super.onResume();  
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);  
    }  
}