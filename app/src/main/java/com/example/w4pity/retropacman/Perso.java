package com.example.w4pity.retropacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by W4pity on 30/04/2016.
 */
public class Perso {
    protected int x, y;
    protected int direction;
    protected int padding = 8;
    protected int size;///////////////////
    //public Rect rect;
    protected int speed = CustomView.sizeCell*8/100;
    protected int speedSave = CustomView.sizeCell*8/100;
    Paint color = new Paint(Paint.ANTI_ALIAS_FLAG);
    public Perso()
    {
        size = CustomView.sizeCell;
        x = 0;
        y = 0;

    }

    public void update(Cell[][] cells)
    {
        int a = this.x+padding;
        int b = this.y+padding;
        int a1 = this.x+size-padding;
        int b1 = this.y+size-padding;
        if(direction == 1 && a1+speed<CustomView.sizeCell*15 && !isCollision(cells))
            x+=speed;
        if(direction == 2 && b1+speed<CustomView.sizeCell*15&& !isCollision(cells))
            y+=speed;
        if(direction == 3 && a-speed>0&& !isCollision(cells))
            x-=speed;
        if(direction == 4 && b-speed >0&& !isCollision(cells))
            y-=speed;
        // size = MainActivity.width/15+padding;// MainActivity.width/15-(2*padding);
        //  x+=speed*directionX ;
        //y+=speed*directionY ;
        // rect = new Rect(posX,posY,posX+size,posY+size);
        // Log.d("sizepac", "Pacman "+posX+" "+posY+" "+ posX+size +" "+(posY+size));
    }

    public void draw(Canvas canvas)
    {
        canvas.drawRect(x+padding, y+padding, x+size-padding, y+size-padding, color);
    }

    public boolean isCollision(Cell[][] cells) {


        if (direction == 1) {
            int b = (this.y + padding) / CustomView.sizeCell;
            int a1 = (this.x + size - padding + speed) / CustomView.sizeCell;
            int b1 = (this.y + size - padding) / CustomView.sizeCell;
            if (cells[b][a1].isSolid() || cells[b1][a1].isSolid()) return true;
        }
        if (direction == 2) {
            int a = (this.x + padding) / CustomView.sizeCell;
            int a1 = (this.x + size - padding) / CustomView.sizeCell;
            int b1 = (this.y + size - padding + speed) / CustomView.sizeCell;
            if (cells[b1][a].isSolid() || cells[b1][a1].isSolid()) return true;
        }
        if (direction == 3) {
            int a = (this.x + padding - speed) / CustomView.sizeCell;
            int b1 = (this.y + size - padding) / CustomView.sizeCell;
            int b = (this.y + padding) / CustomView.sizeCell;
            if (cells[b1][a].isSolid() || cells[b][a].isSolid()) return true;
        }
        if (direction == 4) {

            int a = (this.x+padding)/CustomView.sizeCell;
            int a1 = (this.x+size-padding)/CustomView.sizeCell;
            int b = (this.y+padding-speed)/CustomView.sizeCell;
            if (cells[b][a].isSolid() || cells[b][a1].isSolid()) return true;
        }
        return false;

    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
