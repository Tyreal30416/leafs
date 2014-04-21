#include <iostream>
#include <winsock.h>
#include <windows.h>
#include "stdlib.h"
#include <string>
#include "cv.h"
#include "highgui.h"
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <vector>
#include<time.h>
#include <fstream>
#include <sstream>
using namespace cv;
using namespace std;
vector<Mat> Data;

vector<Mat> getData(){
    double line;
    ifstream f("d:\\leafs.txt");
    int len = 21*25;
    Mat res(len,1,CV_32FC1);
    int i = 0;
    int loc = 0;
    vector<Mat> result;
    while(f>>line){

        res.at<float>(i,0) = line;
        i++;
        if(i == len){
            result.push_back(res);
            i=0;
            //id[loc] = loc;
            loc ++;
            res = Mat(len,1,CV_32FC1);
        }
    }
    return result;
}
void bubble_sort(double unsorted[],int id[],int len)
{
    for (int i = 0; i < len; i++)
    {
        for (int j = i; j < len; j++)
        {
            if (unsorted[i] < unsorted[j])
            {
                double temp = unsorted[i];
                unsorted[i] = unsorted[j];
                unsorted[j] = temp;
                int t = id[i];
                id[i] = id[j];
                id[j] = t;
            }
        }
    }
}
vector<int> CompareLeaf(Mat test,vector<Mat> data){
    double *res = new double[data.size()];
    for(int i = 0 ; i < data.size() ; i ++){
        res[i] = compareHist(test,data[i],CV_COMP_CORREL);
        cout<<"res "<<res[i]<<endl;
    }
    int *id = new int[data.size()];
    for(int j = 0 ; j < data.size() ; j ++){
        id[j] = j+1;
    }
    //int id[] = {1,2,3,4};
    bubble_sort(res,id,data.size());
    vector<int> result ;
    //cout<<res[0]<<" "<<res[1]<<" "<<data.size()<<endl;
    for(int p = 0 ; p < data.size() ; p++){
        result.push_back(id[p]);
    }
    //result.push_back(id[0]);
    //result.push_back(id[1]);
    //result.push_back(id[2]);
    //result.push_back(id[3]);
	delete[] res;
	delete[] id;
    return result;
}
void NMattoOne(Mat &res , Mat hist , int n){

    //Mat res(allmats[0].rows*allmats.size(),1,CV_32FC1);
    int loc = 0;
   for(int i = hist.rows*n ; i < hist.rows*n + hist.rows ; i ++){

        res.at<float>(i,0) = hist.at<float>(loc,0);
        loc++;

    }
    //return res;
}

Mat dealContours(vector<vector<Point> > contours,int rows,int cols){
    double circlearea[] = {13,29,49,81,113,149,197,253,317,377,441,529,613,709,797,901,1009,1129,1257,1373,1517,1653,1793,1961,2121};
    Point p;
    vector<Point> leafpoint = contours[0];
    Mat image = Mat::zeros(rows, cols, CV_8UC1);
    Mat image2 = Mat::zeros(rows, cols, CV_8UC1);
    //Scalar color( 255, 255, 255 );
    Scalar color( 1 );
    drawContours( image, contours, -1, color, CV_FILLED, 0, Mat() );
	//imshow("image",image);
	//waitKey(0);
    //Mat_<Vec3b> _image = image;
    //cout<<"h"<<endl;
    int maxr = 26;
    int count = 0;
    float curvature;
    Mat *allcontours = new Mat[maxr-1];
    uchar *p1;
    uchar *p2;
    for(int r = 2 ;r <= maxr ; r++){
        Mat curvatureforscale(rows,cols,CV_32FC1,Scalar::all(-1));
        for(int i = 0 ; i<leafpoint.size() ; i++){

            count = 0;
            image2 = Mat::zeros(rows, cols, CV_8UC1);
            //Mat_<Vec3b> _image2 = image2;
            p.x = leafpoint[i].x;
            p.y = leafpoint[i].y;
            //cout<<p.x<<" "<<p.y<<endl;
            circle(image2, p, r, color, CV_FILLED, 8, 0);
            //imwrite("result2.png", image2);
            //imshow("circle",image2);
			//waitKey(0);
            for(int row = (int) (p.y-r) ; row <= p.y+r&&row < rows&&row >= 0  ; row ++){
                p1 = image.ptr<uchar>(row);
                p2 = image2.ptr<uchar>(row);
                for(int col = (int) (p.x-r) ; col <= p.x+r&&col < cols&&col >= 0 ; col ++){
                    //if(image.at<uchar>(row, col) == 1 && image2.at<uchar>(row, col) == 1){
                    if(p1[col] == 1 && p2[col] == 1){
  //                  if(p1[col] == 1&&((row - p.y)*(row - p.y)+(col - p.x)*(col - p.x)) <= r*r){
                        count++;
                    }
                }
                //delete(p1);
                //delete(p2);
            }
            curvature = count / circlearea[r-2];
            //cout<<count<<" "<<curvature<<" "<<circlearea[r-2]<<endl;
            curvatureforscale.at<float>((int)p.y,(int)p.x) = curvature;
            //float *pData = curvatureforscale.ptr<float>(p.y);
            //pData[p.x] = curvature;
            //cout<<curvatureforscale.at<float>((int)p.y,(int)p.x)<<endl;
            image2.release();
        }
        //cout<<"h"<<endl;
        //cout<<curvatureforscale<<endl;
        allcontours[r-2] = curvatureforscale;
    }
	//if(p1)
	//delete  p1;
	//if(p2)
	//delete p2;
    int channels[] = {0};
    int histSize[] = {21};
    float hranges[] = { 0, 1 };
    Mat hist;
    const float* ranges[] = {hranges};
    //vector<Mat> allmats;
    Mat res((maxr-1)*21,1,CV_32FC1);
    for(int j = 0 ; j < maxr-1 ; j ++){
        channels[0] = j;
        calcHist(allcontours,maxr-1,channels,Mat(),hist,1,histSize,ranges,true,false);
        normalize(hist,hist);
       // cout<<hist.rows<<" "<<hist.cols<<endl<<" "<<hist<<endl;
        NMattoOne(res,hist,j);
        //allmats.push_back(hist);
    }
    //cout<<res<<endl;
    //for(int p = 0 ;p < allmats.size() ; p ++){
        //cout<<allmats[p]<<endl;
    //}
    //Mat res = NMattoOne(allmats);
    //cout<<res<<endl;
//	delete[] allcontours;
//    delete(ranges);
    return  res;
}
//string[] getCompareResult(Mat testleaf){
DWORD WINAPI Client(LPVOID lparam){
    SOCKET client=(SOCKET)(LPVOID)lparam;
    vector<vector<Point> > contours;
    vector<Point> contour;
    Point p ;

    //int length,x,y;
 //   char s_password[100];
    char  pointslength[100];

    //char plen[20];
    int pointlen;
    //char cx[100],cy[100];
    string sx = "";
    string sy = "";
    //while(true)
    //{
       // if(client = accept(sok, (sockaddr*)&clientaddr, &len))
       // {
            cout<<"OK"<<endl;


ag: if(recv(client, pointslength, sizeof(pointslength), 0) != SOCKET_ERROR)
    {

    vector<vector<Point> > contours;
    vector<Point> contour;
    Point p ;
    //int len = sizeof(clientaddr);
    int length;
 //   char s_password[100];
    //char  pointslength[100];
    cout<<"Server start"<<endl;
    char plen[20];
    int pointlen;

//char da[20];
    //recv(client, da, sizeof(da), 0);

    //cout<<da<<endl;
    //char cx[100],cy[100];
    string sx = "";
    string sy = "";
                    //cout<<"p1: "<<pointslength<<endl;
                    send(client, "OK", sizeof("OK"), 0);
                    if(pointslength!=0)
                    {
                        length = atoi(pointslength);
                        recv(client, plen, sizeof(plen), 0);
                        if(plen!=0){
                            send(client, "OK", sizeof("OK"), 0);
                            pointlen = atoi(plen)+100;
                            //cout<<"p2: "<<pointlen<<endl;
                            char *points = new char[pointlen];
                            recv(client,points,pointlen,0);
							//cout<<sizeof(points)<<endl;
                            //cout<<"p3: "<<points<<endl;

                             int index = 0;
                            for(int i = 0 ; i<pointlen ; i++){

                                if(points[i]<='9'&&points[i]>='0'){
                                    if(index%2==0){
                                        sx+=points[i];
                                    }else{
                                        sy+=points[i];
                                    }
                                }else if(points[i] == ' '){
                                    if(index%2==0){
                                        p.x = atoi(sx.c_str());
                                        //cout<<p.x<<" ";
                                        //cout<<sx<<endl;
                                        sx="";
                                        index++;
                                    }else{
                                        p.y = atoi(sy.c_str());
                                        //cout<<p.y<<endl;
                                        //cout<<sy<<endl;
                                        sy="";
                                        index++;
                                        contour.push_back(p);
                                    }
                                }else break;
                            }
                            p.y = atoi(sy.c_str());
                            contour.push_back(p);
                            //cout<<"p4: "<<contour[contour.size()-1].x<<" "<<contour[contour.size()-1].y<<endl;
                        }

                    }

					//cout<<"contour size :  "<<contour.size()<<endl;
                    contours.push_back(contour);
                   // double TimeStart=GetTickCount();
                    clock_t start,finish;
                    double totaltime;
                    start=clock();
                    Mat res = dealContours(contours,400,300);
                    vector<int> result = CompareLeaf(res,Data);
                    //cout<<"p5: "<<result[0]<<" "<<result[1]<<" "<<result[2]<<" "<<result[3]<<endl;
                    contours.clear();
                   // double TimeEnd=GetTickCount();
                    //double TimeUsed=TimeEnd-TimeStart;
                   // cout<<TimeUsed<<endl;
                    finish=clock();
                    totaltime=(double)(finish-start)/CLOCKS_PER_SEC;
                    cout<<"\n此程序的运行时间为"<<totaltime<<"秒！"<<endl;

                    //string ans = (string)itoa(result[0])+" "+(string)itoa(result[1]);
                    //send(client, "战争古树", sizeof("战争古树"), 0);
                    //cout<<ans.c_str()<<endl;
                    ostringstream oss;
                    oss<<result[0]<<" "<<result[1]<<" "<<result[2]<<" "<<result[3]<<" "<<result[4]<<" "<<result[5];
                    cout<<oss.str()<<endl;
                    //char *c = "52 51 53 14 3 5";
                    send(client, oss.str().c_str(), strlen(oss.str().c_str()), 0);
                   // char rc[20];
                   // recv(client, rc, sizeof(rc), 0);
                    //string rr = rc;
                    //cout<<rr<<endl;
                    //ostringstream oss2;
                    //oss2<<result[3]<<" "<<result[4]<<" "<<result[5];
                   // cout<<oss2.str()<<endl;
                    //if(strcmp(rc,"OK")==0){

                        //send(client, oss2.str().c_str(), strlen(oss2.str().c_str()), 0);
                    //    send(client, oss2.str().c_str(), strlen(oss2.str().c_str()), 0);
                   // }
                    //send(client, "lafafagagaggagaggag", strlen("lafafagagaggagaggag"), 0);

                }
                else
                {
                    //cout<<"有用户试图联入，但是传输的字符串长度为0!/n";
                    send(client, "NOOK", sizeof("NOOK"), 0);
                    goto ag;
                }
    closesocket(client);
    return 0;//不知道对不对


}

int main()
{
    WSADATA wsadata;
    SOCKET sok;

    SOCKADDR_IN ServerAddr;
    SOCKADDR_IN clientaddr;
    int port = 8888;
    SOCKET client;
    WORD ver = MAKEWORD(2,2);
    WSAStartup(ver, &wsadata);

    sok = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    ServerAddr.sin_family = AF_INET;
    ServerAddr.sin_port = htons(port);
    ServerAddr.sin_addr.S_un.S_addr = htonl(INADDR_ANY);

    bind(sok, (SOCKADDR*)&ServerAddr, sizeof(ServerAddr));

    listen(sok, 5);

    Data = getData();
    cout<<Data.size()<<endl;
    //cout<<Data[0]<<endl;
    //system("pause");

    int len = sizeof(clientaddr);
    cout<<"Server start"<<endl;
    while(true){
        if(client = accept(sok, (sockaddr*)&clientaddr, &len))
        {
            DWORD dwThreadId;
            HANDLE hThread;

            hThread=CreateThread(NULL,0,Client,  (LPVOID)client,0,&dwThreadId);
            CloseHandle(hThread);
        }
    }

    closesocket(sok);
    //closesocket(client);
    WSACleanup();



    return 0;
}

