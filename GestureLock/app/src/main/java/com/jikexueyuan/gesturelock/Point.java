package com.jikexueyuan.gesturelock;

/**
 * Created by xiaoyaoxianzhidao on 16/1/10.
 */
public class Point {
    public  static  int STATE_NONAL=0;
    public  static  int STATE_PRESS=1;
    public  static  int STSTE_ERROR=2;

    float x;
    float y;
    int state=STATE_NONAL;

    public  Point(float x,float y){
        this.x=x;
        this.y=y;

    }

public  float distance(Point a){

    float distance = (float) Math.sqrt((x-a.x)*(x-a.x)+(y-a.y)*(y-a.y));
    return distance;

}
}
