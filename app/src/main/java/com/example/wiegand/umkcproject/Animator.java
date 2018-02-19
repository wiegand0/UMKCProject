package com.example.wiegand.umkcproject;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by wiegand on 2/10/18.
 */

public class Animator {
    private Animation[] animations;
    private int animIndex = 0;

    public Animator(Animation[] animations) {
        this.animations = animations;
    }

    public void animate(int index) {
        for(int i = 0; i < animations.length; i++) {
            if(i == index) {
                if (!animations[animIndex].status())
                    animations[i].play();
                    System.out.println("starting...");
                }
            else {
                animations[i].stop();
                System.out.println("...stopping");
            }
            System.out.println("animating...");
        }
        animIndex = index;
    }

    public void draw(Canvas canvas, Rect rect, Point location) {
        if(animations[animIndex].status())
            animations[animIndex].draw(canvas, rect, location);
    }

    public void update() {
        if(animations[animIndex].status())
            animations[animIndex].update();
    }

}
