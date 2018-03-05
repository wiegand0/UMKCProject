package com.example.wiegand.umkcproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wiegand on 1/31/18.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
//HOLDS MAIN GAME SCREEN

    private MainThread thread;

    //keeps crosshair from following action_move
    private boolean moved;

    //for screen panning
    private int dragged = 0;

    //for screen focus
    private float currViewX = 0;
    private float currViewY = 0;

    private Cursor crosshair;
    private Player playerOne;
    private Point playerPoint;
    private WorldClock clock;
    private Enemy adversary;

    //for Panning
    /*
    private float sPositionX;
    private float sPositionY;
    private float sLastTouchX;
    private float sLastTouchY;*/

    private boolean START = false;

    //background
    private Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.background);
    private Bitmap fg = BitmapFactory.decodeResource(getResources(), R.drawable.foreground);

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        thread = new MainThread(getHolder(), this);

//        BitmapFactory.Options options = new BitmapFactory.Options();

//        options.inScaled = false;


        crosshair = new Cursor(new Rect(), Color.rgb(100, 0, 100));
        playerOne = new Player(new Rect());
        playerPoint = new Point();
        clock = new WorldClock();
        adversary = new Enemy(new Rect());
        //bgScaled = Bitmap.createScaledBitmap(bg, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, false);


        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Runnable time = new Runnable() {
            public void run() {
                clock.update();
            }
        };

        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(time, 0, 5, TimeUnit.SECONDS);

        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        START = true;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //sLastTouchX = event.getX();
                //sLastTouchY = event.getY();
                moved = true;
                break;
            }
            //Panning to be implemented
                /*
            case MotionEvent.ACTION_MOVE: {
                ++dragged;
                if(dragged > 15) {
                    moved = false;
                    float x = event.getX();
                    float y = event.getY();
                    if (currViewX + x < bg.getWidth()) {
                        System.out.println("SX, X( " + sPositionX + ", " + x + " )");
                        sPositionX = x - sLastTouchX;
                        dragged = 0;
                    }
                    if (currViewY + y < bg.getHeight()) {
                        System.out.println("SY, Y( " + sPositionY + ", " + y + " )");
                        sPositionY = y - sLastTouchY;
                        dragged = 0;
                    }
                }
                break;
            }*/
            case MotionEvent.ACTION_UP: {
                dragged = 0;
                break;
            }
        }


        if (moved) {
            playerPoint.set((int) event.getX(), (int) event.getY());
        }

        return (true);
    }

    public void update() {
        crosshair.update(playerPoint);
        playerOne.update(playerPoint, adversary.attack(playerOne.getRect()));
    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);
        System.out.println("( " + bg.getHeight() + ", " + bg.getWidth() + " )");

        int width = Constants.SCREEN_WIDTH;
        int height = (width*bg.getHeight())/bg.getWidth();

        bg = Bitmap.createScaledBitmap(bg, width, height, true);

        canvas.drawBitmap(bg,0, 0, null);
        if (START)
            crosshair.draw(canvas);

        height = (int)((width*playerOne.getHeight())/playerOne.getWidth());
        playerOne.draw(canvas);
        clock.draw(canvas);
        //canvas.drawBitmap(fg, -bg.getWidth()/2 + Constants.SCREEN_WIDTH/2, 100, null);

        //canvas.translate(moving.x, moving.y);
    }

}