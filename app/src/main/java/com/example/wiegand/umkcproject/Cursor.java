package com.example.wiegand.umkcproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by wiegand on 1/31/18.
 */

public class Cursor implements GameObject {

    private Rect rectangle;
    private int color;
    private Point current;
    private Animation vacant;
    private Animator animate;

    private long startTime;

    public Cursor(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;


        BitmapFactory bf = new BitmapFactory();
        Bitmap cur0 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs0);
        Bitmap cur1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs1);
        Bitmap cur2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs2);
        Bitmap cur3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs3);
        Bitmap cur4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs4);
        Bitmap cur5 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs5);
        Bitmap cur6 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs6);
        Bitmap cur7 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs7);
        Bitmap cur8 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs8);
        Bitmap cur9 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs9);
        Bitmap cur10 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs10);
        Bitmap cur11 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs11);
        Bitmap cur12 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs12);
        Bitmap cur13 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs13);
        Bitmap cur14 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs14);
        Bitmap cur15 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.curs15);

        vacant = new Animation(new Bitmap[]{cur0,cur1,cur2,cur3,cur4,cur5,cur6,cur7,cur8,cur9,cur10,cur11,cur12,cur13,cur14,cur15}, 2f);

        animate = new Animator(new Animation[]{vacant});

        this.current = new Point();
        current.x = Constants.SCREEN_WIDTH/2;
        current.y = Constants.SCREEN_HEIGHT/2;

        this.current = new Point();
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas) {
        animate.draw(canvas, rectangle, current);
    }

    @Override
    public void update() {
        animate.update();
    }

    public void update(Point point) {

        startTime = System.currentTimeMillis();

        //speed at which cursor moves
        float speedx = Constants.SCREEN_HEIGHT / 20000.0f;

        int x1 = current.x;
        int y1 = current.y;
        int x2 = point.x;
        int y2 = point.y;
        boolean xpas = false;
        boolean ypas = false;
        float disx = x2 - x1;
        //if positive move right
        float disy = y2 - y1;
        float slope = disy / disx;
        if(disx == 0 || disy == 0)
            slope = 0;
        if (slope < 0 && disx > 0) {
            //move right
            current.x += (disx * speedx);
            current.y += ((disx * speedx) * slope);
            if(current.x > point.x)
                xpas = true;
            if(current.y < point.y)
                ypas = true;
        } else if (slope > 0 && disx > 0) {
            current.x += (disx * speedx);
            current.y += ((disx * speedx) * slope);
            if(current.x > point.x)
                xpas = true;
            if(current.y > point.y)
                ypas = true;
        } else if (slope < 0 && disx < 0) {
            //move left
            current.x += (disx * speedx);
            current.y += Math.abs(((disx * speedx) * slope));
            if(current.x < point.x)
                xpas = true;
            if(current.y > point.y)
                ypas = true;
        } else if (slope > 0 && disx < 0) {
            current.x += (disx * speedx);
            current.y += ((disx * speedx) * slope);
            if(current.x < point.x)
                xpas = true;
            if(current.y < point.y)
                ypas = true;
        } else if (slope == 0) {
            if(current.x < point.x) {
                current.x += (disx * speedx);
                if(current.x > point.x)
                    xpas = true;
            } else {
                current.x -= (Math.abs(disx) * speedx);
                if(current.x < point.x)
                    xpas = true;
            }

            if(current.y < point.y) {
                current.y += ((disx * speedx) * slope);
                if(current.y > point.y)
                    ypas = true;
            } else {
                current.y -= ((disx * speedx) * slope);
                if(current.y < point.y)
                    ypas = true;
            }
        }

//      System.out.println("xpas:" + xpas);
//      System.out.println("ypas:" + ypas);
        System.out.println("( " + Constants.SCREEN_HEIGHT + " )");
//      System.out.println("aim: (" + point.x + " , " + point.y + ")");
//      System.out.println("slope: " + slope);
//      System.out.println("disx: " + disx);

        if (xpas)
            current.x = point.x;
        if (ypas)
            current.y = point.y;
        rectangle.set(current.x - rectangle.width()/2, current.y - rectangle.height()/2, current.x + rectangle.width()/2, current.y + rectangle.height()/2);

        animate.animate(0);

        animate.update();
    }

    public Point getPoint() {
        return current;
    }

    public void clear(){

    }
}






