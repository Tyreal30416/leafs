package com.example.leafclient;


import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.EM;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;

public class CalcEm {
	private int height,width;
	Bitmap bmp ;
	private static final double FLT_EPSILON = 1.192093e-007 ;
	public CalcEm(Bitmap bmp,int height,int width){
		
		this.bmp = bmp;
		this.height = height;
		this.width = width;
		
		
	}
	public Bitmap getEmResult(){
		Mat hsvmat = this.resizeImage(this.bmp, this.width, this.height);
		Mat resmat = trainEM(hsvmat,this.height,this.width);
		Bitmap resbmp = Bitmap.createBitmap(this.width, this.height, Config.RGB_565);
//		Utils.matToBitmap(graymat, grayBmp);
		Utils.matToBitmap(resmat, resbmp);
		return resbmp;
	}
	public Mat resizeImage(Bitmap bmp,float newWidth,float newHeight){
		
		Mat hsvmat = new Mat();
		Mat rgbmat = new Mat();
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
		  
			Utils.bitmapToMat(newbmp, rgbmat);
			hsvmat = RGBtoSV(rgbmat);
			return hsvmat;
	}//name:图片的名称
		//获得Drawable
	public Mat trainEM(Mat test_Img,int height,int width){
		Mat testImg =new Mat();
		int scale = 25;
		int num = height*width/scale;
		Size dsize = new Size(width,height);
		Imgproc.resize(test_Img, testImg, dsize);
		TermCriteria termCrit = new TermCriteria(TermCriteria.MAX_ITER|TermCriteria.EPS, 200,FLT_EPSILON );
		EM em = new EM(2,EM.COV_MAT_SPHERICAL,termCrit);
		Mat logLikelihoods = new Mat();
		Mat labels = new Mat();
		Mat probs = new Mat();
		Mat simple = new Mat(num,3,CvType.CV_64F);
		int count = 0;
		for(int i = 0;i<testImg.rows();i+=5){
			for(int j = 0;j<testImg.cols();j+=5){
				simple.put(count, 0, testImg.get(i, j)[0]);
				simple.put(count, 1, testImg.get(i, j)[1]);
				simple.put(count, 2, testImg.get(i, j)[2]);
				count++;
			}
		}
		em.train(simple, logLikelihoods, labels, probs);
		
		//System.out.println(probs.cols()+" "+probs.rows());
		Mat prob = new Mat();
		Mat HSV = new Mat(1,3,CvType.CV_64FC1);
		Mat resmat = Mat.zeros(height, width, CvType.CV_8UC3);
		double[] h_s_v = new double[3];
		double h,s,v;
		for(int i = 0;i<testImg.rows();i++){
			for(int j = 0;j<testImg.cols();j++){
				h=testImg.get(i, j)[0];
				s=testImg.get(i, j)[1];
				v=testImg.get(i, j)[2];
				HSV.put(0, 0, h);
				HSV.put(0, 1, s);
				HSV.put(0, 2, v);
				double[] res = em.predict(HSV, prob);
				if(prob.get(0,0)[0]<prob.get(0, 1)[0]){
					h_s_v[0]=255;
					h_s_v[1]=255;
					h_s_v[2]=255;
					resmat.put(i, j, h_s_v);
				}
				/**
				for(int p = 0;p<prob.rows();p++){
					for(int q = 0;q<prob.cols();q++){
						System.out.print(prob.get(p, q)[0]+" ");
					}System.out.println();
				}
				**/
				//for(int p = 0;p<res.length;p++){
				//	System.out.println(res[p]);
				//}
			}
		}
		return resmat;
		
	}
	public Mat RGBtoSV(Mat rgbmat){
		
		Mat graymat = new Mat();
		
		Imgproc.cvtColor(rgbmat, graymat, Imgproc.COLOR_BGR2HSV);
		int cols = graymat.cols();
		int rows = graymat.rows();
		double s,v;
		for(int i = 0;i<rows;i++){
			for(int j = 0;j<cols;j++){
				
				s = graymat.get(i, j)[1];
				v = graymat.get(i, j)[2];
				double[] hsv = {0,s,v};
				graymat.put(i, j, hsv);
			}
		}
		return graymat;
	}
}
