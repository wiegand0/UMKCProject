package com.example.wiegand.umkcproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by wiegand on 3/4/18.
 */

public class Enemy implements GameObject {

    private Rect rect;
    private Animator animate;
    private Point current;
    private Rect aoe;

    private Animation idle;
    private Animation attack;

    public Enemy(Rect rect) {
        this.rect = rect;

        BitmapFactory bf = new BitmapFactory();

        //idle = new Animation(null/*TBD*/, 0.75f);
        //attack = new Animation(null/*TBD*/, 0.75f);

        //animate = new Animator(new Animation[]{idle, attack});
        current = new Point();
        aoe = new Rect(0,0,300,100);
    }

    public Boolean attack(Rect location) {
        if (aoe.contains(location))
            return true;
        else
            return false;
    }

    public void update(Point player, Rect location) {

        float speedx = Constants.SCREEN_WIDTH / 20000.0f;
        float dist = player.x - current.x;



        if(current.x != player.x) {
            current.x += dist * speedx;
        //    animate.animate(0);
        //    animate.update();
        } else {
        //    animate.animate(1);
        //    animate.update();
            attack(location);
        }
    }

    @Override
    public void update() { /*animate.update();*/ }

    @Override
    public void draw(Canvas canvas) {
        //animate.draw(canvas, rect, current);
    }

}
