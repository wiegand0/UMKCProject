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
    private int lifepoints;
    private int state;

    private Animation idle;
    private Animation moveleft;
    private Animation moveright;
    private Animation jump;
    private Animation die;

    public Player(Rect rect) {
        this.rect = rect;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap idl0 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_01);
        Bitmap idl1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_02);
        Bitmap idl2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_03);
        Bitmap idl3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_04);
//         FIGURE OUT PLAYER SCALING
     /*   System.out.println("(" + (idl0.getWidth())/(idl0.getHeight()) + ")");
        float whratio = ((float)(idl0.getWidth()/idl0.getHeight()));
        System.out.println("--" + whratio);
        int width = (int)(whratio * idl0.getWidth());
        int height = (int)(1/whratio * idl0.getHeight());
        System.out.println("( " + width + ", " + height + " )");
        idl0 = Bitmap.createScaledBitmap(idl0, width, height, true);
        idl1 = Bitmap.createScaledBitmap(idl1, width, height, true);
        idl2 = Bitmap.createScaledBitmap(idl2, width, height, true);
        idl3 = Bitmap.createScaledBitmap(idl3, width, height, true); */

        idle = new Animation(new Bitmap[]{idl0,idl1,idl2,idl3}, 0.75f);
//        moveleft = new Animation(null/*TBD*/, 0.75f);
//        moveright = new Animation(null/*TBD*/, 0.75f);
//        jump = new Animation(null/*TBD*/, 0.75f);
//        die = new Animation(null/*TBD*/, 0.75f);
        animate = new Animator(new Animation[]{idle/*, moveleft, moveright, jump, die*/});

        this.current = new Point();
        current.x = 1200;
        current.y = 670;
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

    public Rect getRect() { return rect; }

    public void update(Point point, Boolean hit) {

        if(lifepoints == 0)
            state = 4;
        float speedx = Constants.SCREEN_HEIGHT / 150000.0f;
        float dist = point.x - current.x;

        if(dist < 0 && lifepoints > 0)
            state = 1;
        else if(dist > 0 && lifepoints > 0)
            state = 2;
        else if(dist == 0 && lifepoints > 0)
            state = 0;

        current.x += dist * speedx;
        //UNTIL ANIMATIONS ARE DONE
        state = 0;
        animate.animate(state);

        if(hit)
            --lifepoints;

        animate.update();
    }

    public float getWidth() {
        return rect.width();
    }

    public float getHeight() {
        return rect.height();
    }
}
