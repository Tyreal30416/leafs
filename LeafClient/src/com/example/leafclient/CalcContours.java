package com.example.leafclient;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

public class CalcContours {
	Mat source ;
	int width,height;
	public CalcContours(Mat source,int width,int height){
		this.source = source;
		this.width = width;
		this.height = height;
	}
	public void convert3toSingle(Mat source,Mat resmat){
		//Mat resmat = new Mat();
		Imgproc.cvtColor(source, resmat, Imgproc.COLOR_BGR2GRAY);
		Imgproc.threshold(resmat, resmat, 150, 1, Imgproc.THRESH_BINARY);
		return;
	}
	//消除叶子中的false positive regions
	public MatOfPoint cutFPRfromLeaf(){
		//List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		this.convert3toSingle(source, source);
		List<MatOfPoint> contours = getLeafPoints(source);
		//System.out.println(sourse.type()+" "+sourse.channels());
		double area;
		double max = 0;
		int loc = 0;
		//System.out.println(contours.size());
		for(int i = 0;i < contours.size();i ++){
			boolean b = true;
			area = Imgproc.contourArea(contours.get(i));
			for(int j = 0 ; j<contours.get(i).rows() ; j++){
				//System.out.println(contours.get(i).get(j, 0)[0]+" "+(int)contours.get(i).get(j, 0)[1]);
				if((int)contours.get(i).get(j, 0)[0] == 1&&(int)contours.get(i).get(j, 0)[1] == 1){
					b = false;
					break;
				}
			}
			if(area > max&&b){
				max = area;
				loc = i;
			}
		}
//		List<MatOfPoint> leaf = new ArrayList<MatOfPoint>();
		MatOfPoint leafPoints = contours.get(loc);
//		leaf.add(leafPoints);
		
		return leafPoints;
		
	}
	public List<MatOfPoint> getLeafPoints(Mat source){
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(source, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);
		
		return contours;
	}
}
