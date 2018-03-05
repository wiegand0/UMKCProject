package com.example.wiegand.umkcproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by wiegand on 3/3/18.
 */

public class SceneObject implements GameObject {
    private int state;
    private Point loc;
    private Animator animate;
    private Bitmap idle;
    private Rect rect;
    private String anims;

    public SceneObject(Rect rect, int states, String anims){
        this.rect = rect;
        this.anims = anims;

    }

    @Override
    public void draw(Canvas canvas) {
        switch (state) {
            case 0:
               canvas.drawBitmap(idle, loc.x-idle.getWidth()/2, loc.y-idle.getHeight()/2, new Paint());
            case 1:
                //RETRIEVE ANIMATIONS BITMAP FROM RESOURCE FOLDER USING ASSET MANAGER

        }
    }

    @Override
    public void update() {

    }
}
