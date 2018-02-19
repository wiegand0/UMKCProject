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

        crosshair = new Cursor(new Rect(100, 100, 200, 200), Color.rgb(100, 0, 100));
        playerOne = new Player(new Rect());
        playerPoint = new Point();

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
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
        playerOne.update(playerPoint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


//        System.out.println("( " + currViewX + ", " + currViewY + " )");
        canvas.drawBitmap(bg, -bg.getWidth() / 2 - Constants.SCREEN_WIDTH / 2, -bg.getHeight() / 2, null);
        if (START)
            crosshair.draw(canvas);
        playerOne.draw(canvas);
        canvas.drawBitmap(fg, -bg.getWidth() / 2, -bg.getHeight() / 2, null);

        //canvas.translate(moving.x, moving.y);
    }

}