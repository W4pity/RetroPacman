package com.example.w4pity.retropacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class CustomEdit extends View {


    int[][] terrain = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    Cell[][] cells = new Cell[MainActivity.nbCell][MainActivity.nbCell];

    public CustomEdit(Context c) {
        super(c);
    }
    public CustomEdit(Context c, AttributeSet as) {
        super(c, as);
        init();
    }
    public CustomEdit(Context c, AttributeSet as, int default_style) {
        super(c, as, default_style);

    }



    private void init() {
        this.setBackgroundColor(0xFFff00ff);
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

            invalidate();



    }


    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

            return true;
        }
        return super.onTouchEvent(event);

    }




}

