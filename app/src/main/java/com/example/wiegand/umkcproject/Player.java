package com.example.wiegand.umkcproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by wiegand on 2/10/18.
 */

public class Player implements GameObject {

    private Rect rect;
    private Point current;
    private Animator animate;
    private int color;

    private Animation idle;

    public Player(Rect rect) {
        this.rect = rect;
        this.color = color;


        BitmapFactory bf = new BitmapFactory();
        Bitmap idl0 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_01);
        Bitmap idl1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_02);
        Bitmap idl2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_03);
        Bitmap idl3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_04);

        idle = new Animation(new Bitmap[]{idl0,idl1,idl2,idl3}, 0.75f);

        animate = new Animator(new Animation[]{idle});

        this.current = new Point();
        current.x = 1200;
        current.y = 500;
    }

    @Override
    public void draw(Canvas canvas) {
        animate.draw(canvas, rect, current);
    }

    @Override
    public void update() {
        animate.update();
    }

    public Point getCurrent() {
        return current;
    }

    public void update(Point point) {

        float speedx = Constants.SCREEN_HEIGHT / 150000.0f;
        float dist = point.x - current.x;

        current.x += dist * speedx;

        animate.animate(0);

        animate.update();
    }
}
