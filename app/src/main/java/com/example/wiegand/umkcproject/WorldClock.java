package com.example.wiegand.umkcproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by wiegand on 3/3/18.
 */

public class WorldClock implements GameObject {
    private int min;
    private int hrs;
    private String disphr;
    private String dispmn;

    public WorldClock() {

        hrs = 3;
        min = 0;
    }

    @Override
    public void update() {

        ++min;

        if(min > 60) {
            min = 0;
            ++hrs;
        }

        if(hrs > 12)
            hrs = 1;

        properTime();
    }

    private void properTime() {

        String propDisphrs = String.valueOf(hrs);
        String propDispmin = String.valueOf(min);
        if(hrs < 10)
            propDisphrs = "" + String.valueOf(0) + String.valueOf(hrs);
        if(min < 10)
            propDispmin = "" + String.valueOf(0) + String.valueOf(min);

        disphr = propDisphrs + ":";
        dispmn = propDispmin;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(100);

        canvas.drawText(disphr + dispmn, Constants.SCREEN_WIDTH/2 - 50, 115, paint);

    }
}
