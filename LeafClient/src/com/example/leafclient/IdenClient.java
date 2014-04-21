package com.example.leafclient;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.opencv.core.MatOfPoint;

import android.annotation.SuppressLint;
import android.os.StrictMode;

public class IdenClient {
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	private boolean bConnected = false;
	Thread tRecv = new Thread(new RecvThread()); 
	MatOfPoint mop ;
	public IdenClient(MatOfPoint mop){
		this.mop = mop;
	}
	@SuppressLint("NewApi")
	public String getDis() throws Exception{
//		ServerSocket server = new ServerSocket(9999);
//		Socket client = server.accept();
//		PrintStream out = new PrintStream(client.getOutputStream());
//		BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
//		StringBuffer info = new StringBuffer();
//		info.append("Android : ");
//		String str = buf.readLine();
//		info.append(str);
//		System.out.println(str);
//		out.println(info);
//		out.close();
//		buf.close();
//		client.close();
//		server.close();
		byte[] res = new byte[20];
		//byte[] x = new byte[]

		connect();
		String str;
//		double points[] = MattoArray();
		NetDataTypeTransform ndtt = new NetDataTypeTransform();
		System.out.println("rows:"+mop.rows());
		//dos.writeUTF("hello");
		//		System.out.println(ndtt.ByteArraytoString(ndtt.StringToByteArray("1512"), ndtt.StringToByteArray("1512").length));
		dos.write(ndtt.StringToByteArray(mop.rows()+"\0"));
		dis.read(res);
		str = ndtt.ByteArraytoString(res, res.length);
		if(str.equalsIgnoreCase("OK")){
			int loc = 0;
			String data = ""+(int)mop.get(loc, 0)[0]+" "+(int)mop.get(loc, 0)[1];
			System.out.println(data);
			loc++;
			
			while(loc < mop.rows()){
				System.out.println(mop.get(loc, 0)[0]+" "+mop.get(loc, 0)[1]);
				data += " " + (int)mop.get(loc, 0)[0] + " " + (int)mop.get(loc, 0)[1];
				loc++;
				//dos.write(ndtt.StringToByteArray(mop.get(loc++, 0)[0]+"\0"));
				//dos.write(ndtt.StringToByteArray(mop.get(loc++, 0)[1]+"\0"));
			}
			System.out.println(data.length()+" "+data);
			dos.write(ndtt.StringToByteArray(data.length()+"\0"));
			dis.read(res);
			str = ndtt.ByteArraytoString(res, res.length);
			if(str.equalsIgnoreCase("OK")){
				dos.write(ndtt.StringToByteArray(data+"\0"));
			}
	//		dos.write(ndtt.IntToByteArray(5));
	//		System.out.println(points[0])points;
	//		ObjectOutputStream oops = new ObjectOutputStream(dos);
	//		oops.writeObject(points);
			str = "";
			while(true){
				dis.read(res);
				System.out.println(res.length);
				str = ndtt.ByteArraytoString(res, res.length);
				System.out.println("res:"+str);
				if(!str.equals(""))
					break;
			}
//			String str2 = "";
//			str += " ";
//			dos.write(ndtt.StringToByteArray("OK\0"));
//			while(true){
//				dis.read(res);
//				str2 = ndtt.ByteArraytoString(res, res.length);
//				System.out.println("res:"+str2);
//				str += str2;
//				if(!str2.equals(""))
//					break;
//			}
			
		}
//		server.tRecv.start();
		return str;
		
	}
//	public double[] MattoArray(){
//		double[] points = new double[this.mop.rows()*2];
//		int loc = 0;
//		for(int i = 0 ; i < this.mop.rows() ; i ++){
//			points[loc] = mop.get(i, 0)[0];
//			loc++;
//			points[loc] = mop.get(i, 0)[1];
//			loc++;
//		}
//		
//		return points;
//	}
	public void connect() {
		try {
//			s = new Socket("192.168.1.109", 8888);
			s = new Socket("10.214.8.111", 8888);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
//System.out.println("connected!");
			bConnected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void disconnect() {
		try {
			dos.close();
			dis.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		try {
			bConnected = false;
			tRecv.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				dos.close();
				dis.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		*/
	}
	
	private class RecvThread implements Runnable {

		public void run() {
			try {
				while(bConnected) {
					String str = dis.readUTF();
					//System.out.println(str);
				}
			} catch (SocketException e) {
				System.out.println("退出了，bye!");
			} catch (EOFException e) {
				System.out.println("推出了，bye - bye!");
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
	}

}
