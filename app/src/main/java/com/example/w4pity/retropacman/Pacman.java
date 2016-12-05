package com.example.w4pity.retropacman;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by W4pity on 25/04/2016.
 */
public class Pacman extends Perso{

    //aint color = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int nbOfPoints = 0;
    private boolean boosted = false;
    private long oldTimeBoost;
    private long oldTimeView = 0;
    private boolean openMouth = true;
    private int anticipationMvt = 0;
    private MediaPlayer mdB;
    int songBonus = R.raw.hiha;

   // Paint white = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Pacman()
    {
        mdB = MediaPlayer.create(MainActivity.cont, songBonus);


        size = CustomView.sizeCell;
        color.setColor(Color.YELLOW);
        direction = 1;
        //size =15;// width/15-(2*padding);
        x = 0;
        y = 0;
        padding = 12*MainActivity.width/540;
    }

    /*
    w---15
    y

     */


    public void update(Cell[][] cells)
    {
        super.update(cells);





        if(!cells[(y+size/2)/size][(x+size/2)/size].isTaken()) {
            cells[(y + size / 2) / size][(x + size / 2) / size].setTaken(true);
            nbOfPoints++;
        }
        if(cells[(y+size/2)/size][(x+size/2)/size].isContainBonus()) {
            cells[(y+size/2)/size][(x+size/2)/size].setContainBonus(false);
            CustomView.timeBonusold = System.currentTimeMillis();
            CustomView.bonusPresent = false;
            boosted = true;
            mdB.start();
            oldTimeBoost = System.currentTimeMillis();
            speed+=2;
        }

        if(boosted && System.currentTimeMillis()-oldTimeBoost>5000)
        {
            boosted =false;
            speed=speedSave  ;

        }
    }


    @Override
    public void draw(Canvas canvas)
    {
        Rect r = new Rect(x + padding-4, y + padding-4, x + size - padding+4, y + size - padding+4);
        if(direction ==1&&!CustomView.isLoose && !CustomView.isWin) {
            if(openMouth)
                canvas.drawBitmap(MainActivity.pacmanRight, null,r, null);
            else
                canvas.drawBitmap(MainActivity.pacmanRightClose, null, r, null);
        }
        if(direction ==2&&!CustomView.isLoose&& !CustomView.isWin) {
            if (openMouth)
                canvas.drawBitmap(MainActivity.pacmanBottom, null, r, null);
            else
                canvas.drawBitmap(MainActivity.pacmanBottomClose, null, r, null);
        }
        if(direction ==3&&!CustomView.isLoose&& !CustomView.isWin) {
            if (openMouth)
                canvas.drawBitmap(MainActivity.pacmanLeft, null, r, null);
            else
                canvas.drawBitmap(MainActivity.pacmanLeftClose, null, r, null);
        }
        if(direction ==4&&!CustomView.isLoose&& !CustomView.isWin) {
            if (openMouth)
                canvas.drawBitmap(MainActivity.pacmanTop, null, r, null);
            else
                canvas.drawBitmap(MainActivity.pacmanTopClose, null, r, null);
        }
        if(CustomView.isLoose)
            canvas.drawBitmap(MainActivity.pacmanDepth, null, r, null);
        if(CustomView.isWin)
            canvas.drawBitmap(MainActivity.pacmanWin, null, r, null);
        if(1000000000/2/speed<System.nanoTime()-oldTimeView)
        {
            openMouth = !openMouth;
            oldTimeView = System.nanoTime();
        }
    }
    public int getNbOfPoints() {
        return nbOfPoints;
    }

    public boolean isBoosted() {
        return boosted;
    }

    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
    }

    public int getAnticipationMvt() {
        return anticipationMvt;
    }

    public void setAnticipationMvt(int anticipationMvt) {
        this.anticipationMvt = anticipationMvt;
    }
}
