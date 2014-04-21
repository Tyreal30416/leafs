package com.example.leafclient;

import java.util.ArrayList;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EMActivity extends Activity {
	private Button confirmem = null;
	private Button emback = null;
	private ImageView original = null;
	private ImageView emresult = null;
	private TextView result = null;
	private int height = 400;
	private int width = 300;
	Bitmap embitmap = null ;
	MatOfPoint mp ;
	Handler handler;  
    Handler wife_handler;
    Bitmap original2;
//	private String dis="";
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
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.emlayout);
		
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
		
		confirmem = (Button) super.findViewById(R.id.confirmem);
//		emback = (Button) super.findViewById(R.id.emback);
		
		original = (ImageView) super.findViewById(R.id.original);
		emresult = (ImageView) super.findViewById(R.id.emresult);
		
		confirmem.setOnClickListener(new ConfirmEmListener());
		
		result = (TextView) super.findViewById(R.id.result);
//		emback.setOnClickListener(new EmBackListener());

//		original.setLayoutParams(new LayoutParams(,));
		Intent it = super.getIntent();
		original2 = (Bitmap)it.getParcelableExtra("original2");
		String th = it.getStringExtra("tophat");
		System.out.println(th);
		this.original.setImageBitmap(original2);
		//System.out.println("h");
//		this.embitmap = (Bitmap)it.getParcelableExtra("EM");
		CalcEm calcem = new CalcEm(original2,height,width);
		
//		if(this.embitmap == null) System.out.println("null");
		this.embitmap = calcem.getEmResult();
		//while(this.embitmap==null){}
		Mat mat = new Mat();
		Utils.bitmapToMat(this.embitmap, mat);
		CalcContours calc = new CalcContours(mat,width,height);
		mp = calc.cutFPRfromLeaf();
		Mat image = new Mat(height,width,CvType.CV_8UC3);
		List<MatOfPoint> leaf = new ArrayList<MatOfPoint>();
		leaf.add(mp);
		Imgproc.drawContours(image, leaf, -1, new Scalar(255,255,255), Core.FILLED);
		if(th.equalsIgnoreCase("tophat")){
			image = TopHat(image);
		}
		Matrix matrix = new Matrix();
		Bitmap newbmp = Bitmap.createBitmap(embitmap, 0, 0, embitmap.getWidth(), embitmap.getHeight(), matrix , true);
		Utils.matToBitmap(image, newbmp);
		this.emresult.setImageBitmap(newbmp);
		
//		System.out.println(original.getHeight()+" "+original.getWidth()+" "+embitmap.getHeight()+" "+embitmap.getWidth());
	}
	public Mat TopHat(Mat sample){
		int th_size=10;
		Size size=new Size(2*th_size + 1, 2*th_size+1);
		Point point=new Point(th_size,th_size);
		//Mat sample = new Mat(EMActivity.this.height,EMActivity.this.width,CvType.CV_32FC3);
		Mat dst = new Mat(EMActivity.this.height,EMActivity.this.width,CvType.CV_32FC3);
		//Utils.bitmapToMat(bitmap, sample);
		Mat kernel=Imgproc.getStructuringElement( Imgproc.MORPH_CROSS,size,point);
		Imgproc.morphologyEx(sample,dst,org.opencv.imgproc.Imgproc.MORPH_TOPHAT,kernel);
		CalcContours calc = new CalcContours(dst,width,height);
		mp = calc.cutFPRfromLeaf();
		List<MatOfPoint> leaf = new ArrayList<MatOfPoint>();
		leaf.add(mp);
		Mat image = new Mat(height,width,CvType.CV_8UC3);
		Imgproc.drawContours(image, leaf, -1, new Scalar(255,255,255), Core.FILLED);
		Core.absdiff(sample, image, dst);
		return dst;
	}
	public class ConfirmEmListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			Mat mat = new Mat();
//			Utils.bitmapToMat(EMActivity.this.embitmap, mat);
//			CalcContours calc = new CalcContours(mat,width,height);
//			MatOfPoint mp = calc.cutFPRfromLeaf();
			final ProgressDialog diag = new ProgressDialog(EMActivity.this);
			diag.setTitle("正在与数据库进行匹配中。。。。");
			diag.setMessage("少女祈祷中...");
			diag.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			//diag.setProgress(30);
			//diag.setMax(100);
			
			diag.setButton("Runing background", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					diag.dismiss();
				}
			});
			diag.setButton2("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					diag.cancel();
				}
			});
			new Thread(){
//				Handler uihandler;  
//		        Handler wifehandler = handler; 
		        
				public void run(){
					//for(int i = 0 ; i <100 ; i ++){
						String dis = "";
						
						try {
							System.out.println("OK");
							IdenClient ic = new IdenClient(EMActivity.this.mp);
							try {
								dis = ic.getDis();
//								dis = "1 2";
								System.out.println("dis+ "+dis);
								//EMActivity.this.result.setText("这片叶子所属的种类可能是：\n"+dis+"!");
								
//								EMActivity.this.finish();
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("error1");
							}
							//Thread.sleep(500);
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("error2");
//						}finally{
//							diag.dismiss();
//						}
						}
						//diag.incrementProgressBy(1);
					//}
						
					diag.dismiss();
					while(!dis.equals("")){
						Intent it = new Intent(EMActivity.this,resultActivity.class);
						it.putExtra("result", dis);
						it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						EMActivity.this.startActivity(it);
						break;
					}
				}
			}.start();
			diag.show();
//			handler = new Handler(){
//				 @Override  
//		            public void handleMessage(Message msg) {  
//		                super.handleMessage(msg);  
//				 }
//
//			};
//			IdenClient ic = new IdenClient(EMActivity.this.mp);
//			diag.show();
//			String dis;
//			try {
//				dis = ic.getDis();
//				while(dis.equalsIgnoreCase("")){
//					
//				}
//				diag.dismiss();
//				System.out.println("dis+ "+dis);
//				
//				EMActivity.this.result.setText("这片叶子所属的种类可能是：\n"+dis+"!");
//				Intent it = new Intent(EMActivity.this,resultActivity.class);
//				
//				it.putExtra("result", dis);
//				
//				EMActivity.this.startActivity(it);
//				EMActivity.this.finish();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		String dis = "1 2";
			
			
			
		}
		
		
	}
//	public class EmBackListener implements OnClickListener{
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Intent it = new Intent(EMActivity.this,OriginalLeafActivity.class);
//			EMActivity.this.startActivity(it);
//			it.setData(imageFileUri);
//			EMActivity.this.finish();
//		}
//		
//	}
	public void onResume(){  
        super.onResume();  
        //通过OpenCV引擎服务加载并初始化OpenCV类库，所谓OpenCV引擎服务即是   
        //OpenCV_2.4.3.2_Manager_2.4_*.apk程序包，存在于OpenCV安装包的apk目录中   
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);  
    }  
}