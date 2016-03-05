package com.jikexueyuan.gesturelock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class GestureLock extends View {
    private Point[][] points = new Point[3][3];
    public boolean isDraw =false;
    private boolean inited = false;

    private ArrayList<Point> pointlist= new ArrayList<Point>();
    private ArrayList<Integer> passList = new ArrayList<Integer>();//0-8

    private Bitmap bitmapPointNormal;
    private Bitmap bitmapPointPress;
    private Bitmap bitmapPointError;


    private  OnDrawFinishedListener listener;
    float mouseX;
    float mouseY;


    private float bitmapR;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint pressPaint = new Paint();
    Paint errorPaint = new Paint();

    public GestureLock(Context context) {
        super(context);
    }

    public GestureLock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureLock(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
        int[] ij;
        int i,j;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                resetPoints();
                 ij=getSelectedPoint();
                if(ij!=null){

                    isDraw=true;
                     i=ij[0];
                     j=ij[1];
                    points[i][j].state= Point.STATE_PRESS;
                    pointlist.add(points[i][j]);
                    passList.add(i*3+j);//二维数组专一维

                }

                break;
            case MotionEvent.ACTION_MOVE:

                if(isDraw){
                    ij=getSelectedPoint();
                    if(ij!=null){
                        i=ij[0];
                        j=ij[1];
                        if(!pointlist.contains(points[i][j]))
                        {
                            points[i][j].state=Point.STATE_PRESS;
                            pointlist.add(points[i][j]);
                            passList.add(i*3+j);
                        }


                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                boolean valid=false;

                if(listener!=null && isDraw)
                {
                    valid=listener.OnDrawFinished(passList);
                }

                if (!valid)
                {
                    for(Point p:pointlist)
                    {
                        p.state=Point.STSTE_ERROR;
                    }
                }
                isDraw=false;
                break;

        }
        this.postInvalidate();//及时显示
        return true;
    }

    private int[] getSelectedPoint(){
        //返回值是int型数组
        Point pMouse = new Point(mouseX,mouseY);//定义一个新点
        for(int i=0;i<points.length;i++){
            for (int j=0;j<points[i].length;j++){
                if (points[i][j].distance(pMouse)<bitmapR){


                    int[] result = new int[2];
                    result[0]=i;
                    result[1]=j;
                    return  result;

                }

            }

        }


        return  null;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化
        if (!inited) {
            init();
        }
        drawPoints(canvas);

        if(pointlist.size()>0){
            Point a=pointlist.get(0);
            for(int i=1;i<pointlist.size();i++)
            {
                Point b =pointlist.get(i);
                drawLine(canvas,a,b);
                a=b;//更新起点


            }
            if (isDraw)
            {
                drawLine(canvas,a,new Point(mouseX,mouseY));
            }
        }

    }
    private  void  drawLine(Canvas canvas,Point a,Point b){

        if (a.state == Point.STATE_PRESS) {
            canvas.drawLine(a.x,a.y,b.x,b.y,pressPaint);

        }
        else if (a.state == Point.STSTE_ERROR) {
            canvas.drawLine(a.x,a.y,b.x,b.y,errorPaint);

        }

    }


    private void drawPoints(Canvas canvas) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {

                if (points[i][j].state == Point.STATE_NONAL) {

                    canvas.drawBitmap(bitmapPointNormal, points[i][j].x - bitmapR, points[i][j].y - bitmapR, paint);
                    //normal
                } else if (points[i][j].state == Point.STATE_PRESS) {
                    //press
                    canvas.drawBitmap(bitmapPointPress, points[i][j].x - bitmapR, points[i][j].y - bitmapR, paint);

                } else {
                    //error
                    canvas.drawBitmap(bitmapPointError, points[i][j].x - bitmapR, points[i][j].y - bitmapR, paint);
                }

            }
        }

    }


    private void init() {

        pressPaint.setColor(Color.RED);
        pressPaint.setStrokeWidth(12);//kuan
        errorPaint.setColor(Color.BLACK);
        errorPaint.setStrokeWidth(12);//kuan
        bitmapPointError = BitmapFactory.decodeResource(getResources(), R.drawable.error);
        bitmapPointNormal = BitmapFactory.decodeResource(getResources(), R.drawable.normal);
        bitmapPointPress = BitmapFactory.decodeResource(getResources(), R.drawable.press);

        bitmapR = bitmapPointError.getHeight() / 2;


        int width = getWidth();
        int height = getHeight();
        int offset = Math.abs(width - height) / 2;
        int offsetX, offsetY, space;
        if (width > height) {

            offsetX = offset;
            space = height / 4;
            offsetY = 0;

        } else {

            offsetY = offset;
            space = width / 4;
            offsetX = 0;

        }
        points[0][0] = new Point(offsetX + space, offsetY + space);
        points[0][1] = new Point(offsetX + space * 2, offsetY + space);
        points[0][2] = new Point(offsetX + space * 3, offsetY + space);

        points[1][0] = new Point(offsetX + space, offsetY + space * 2);
        points[1][1] = new Point(offsetX + space * 2, offsetY + space * 2);
        points[1][2] = new Point(offsetX + space * 3, offsetY + space * 2);

        points[2][0] = new Point(offsetX + space, offsetY + space * 3);
        points[2][1] = new Point(offsetX + space * 2, offsetY + space * 3);
        points[2][2] = new Point(offsetX + space * 3, offsetY + space * 3);

        inited = true;


    }

    public  void  resetPoints()
    {
        passList.clear();
        pointlist.clear();

        for(int i=0;i<points.length;i++){

            for(int j=0;j<points[i].length;j++){

                points[i][j].state=Point.STATE_NONAL;
            }
        }
        this.postInvalidate();//界面重绘
    }

    public interface  OnDrawFinishedListener
    {//暴漏给外部
        boolean OnDrawFinished(List<Integer> passList);

    }

    public void  setOnDrawFinishedListener(OnDrawFinishedListener listener)
    {
        this.listener=listener;
    }
}

