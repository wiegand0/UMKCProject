package com.example.wiegand.umkcproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by wiegand on 2/10/18.
 */

public class Animation {
    private Bitmap[] frames;
    private Boolean playing = false;
    private int currFrame;
    private float pauseTime;
    private long lastFrame;

    public Animation(Bitmap[] frames, float animTime) {
        this.frames = frames;
        currFrame = 0;
        pauseTime = animTime/frames.length;
        lastFrame = System.currentTimeMillis();
    }

    public Boolean status() {
        return playing;
    }

    public void play() {
        playing = true;
        currFrame = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void stop() {
        playing = false;
    }

    public void draw(Canvas canvas, Rect dest, Point location) {
        if(!playing)
            return;

        scaleRect(dest);
        canvas.drawBitmap(frames[currFrame], location.x - (frames[currFrame].getWidth()/2), location.y - (frames[currFrame].getHeight()/2), new Paint());
    }

    private void scaleRect(Rect rect) {
        float whRatio = (float)(frames[currFrame].getWidth()/frames[currFrame].getHeight());

        if(rect.width() > rect.height())
            rect.left = rect.right - (int)(rect.height() * whRatio);
        else
            rect.top = rect.bottom - (int)(rect.width() * (1/whRatio));

    }

    public void update() {
        if(!playing)
            return;

        if(System.currentTimeMillis() - lastFrame > pauseTime * 1000) {
            currFrame++;
            currFrame = currFrame >= frames.length ? 0 : currFrame;
            lastFrame = System.currentTimeMillis();
        }
    }
}
