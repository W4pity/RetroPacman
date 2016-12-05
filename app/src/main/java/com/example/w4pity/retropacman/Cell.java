package com.example.w4pity.retropacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by W4pity on 26/04/2016.
 */
public class Cell {
    private boolean isTaken = false;
    private boolean containBonus = false;
    private Paint red, bleu, white;
    private boolean isSolid;
    private int  x, y, size;


    public Cell(int x, int y, boolean isSolid)
    {
        this.isSolid = isSolid;
        this.x = x;
        this.y = y;
        size = CustomView.sizeCell;
        red = new Paint(Paint.ANTI_ALIAS_FLAG);
        bleu = new Paint(Paint.ANTI_ALIAS_FLAG);
        white = new Paint(Paint.ANTI_ALIAS_FLAG);
        white.setColor(Color.WHITE);
        red.setColor(0xFFFF0000);
        bleu.setColor(0xFF0000FF);

    }

    public void update()
    {

    }

    public void draw(Canvas canvas)
    {
        if(isSolid)
            canvas.drawBitmap(MainActivity.mur, null, new Rect(x, y, x + size, y + size), null);
        else
            canvas.drawBitmap(MainActivity.sol, null, new Rect(x, y, x + size, y + size), null);
        if (!isTaken() && !isSolid())
            canvas.drawCircle(getX()+CustomView.sizeCell/2, getY()+CustomView.sizeCell/2, 5, red);
        if(containBonus)
            canvas.drawCircle(x+size/2, y+size/2, 8, bleu);

    }

///////////////////getters and setters


    public boolean isSolid() {
        return isSolid;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTaken(boolean taken) {
        this.isTaken = taken;
    }

    public void setIsSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isContainBonus() {
        return containBonus;
    }

    public void setContainBonus(boolean containBonus) {
        this.containBonus = containBonus;
    }
}
