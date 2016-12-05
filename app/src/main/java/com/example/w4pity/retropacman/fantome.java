package com.example.w4pity.retropacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.Random;

/**
 * Created by W4pity on 25/04/2016.
 */
public class fantome extends Perso{

    //aint color = new Paint(Paint.ANTI_ALIAS_FLAG);
    int saveX, saveY;
    double oldTime, time;
    int rnd = 0;
    Paint blue = new Paint( Paint.ANTI_ALIAS_FLAG);
    private boolean tagged = false;
    Random r = new Random();
    private int nbOfTheSamePosition = 0;
    private boolean isDead = false;
    private long oldTimeView = 0;
    private boolean switchview = false;
    private MediaPlayer md;
    private int songF;
    public fantome(boolean tag)
    {
        songF = R.raw.mouaha;
        md=MediaPlayer.create(MainActivity.cont, songF);
        tagged = tag;
        size = CustomView.sizeCell;
        color.setColor(Color.BLUE);
        if(tag)
            color.setColor(Color.GREEN);
        direction = 1;
        saveX = 0;
        padding = 4;


    }

    @Override
    public void update(Cell[][] cells)
    {
        super.update(cells);
        time = System.nanoTime();



        if((y+size-1)/size == 0 && (x)/size !=0 && x/size != 14)//top side
        {
            if(direction == 2 || direction == 4)
                direction = chooseBetween(1,3);
            if(direction == 1)
            {
               // if(isTagged())
                if(!cells[1][x/size].isSolid() && !cells[1][(x+size-1)/size].isSolid() && rnd == 1) {
                    direction++;
                }
            }
            if(direction == 3)
            {
               // if(isTagged())
                    if(!cells[1][x/size].isSolid() && !cells[1][(x+size-1)/size].isSolid() && rnd == 1) {
                        direction--;
                    }

            }
        }
        else if(x/size == 14 && y/size !=0 && y/size != 14) {//right side
            if(direction == 1 || direction == 3)
                direction = chooseBetween(2,4);
            if (direction == 2) {
                // if(isTagged())
                if (!cells[y / size][13].isSolid() && !cells[(y + size - 1) / size][13].isSolid() && rnd == 1) {
                    direction++;
                }
            }
            if (direction == 4) {
                // if(isTagged())
                if (!cells[y / size][13].isSolid() && !cells[(y + size - 1) / size][13].isSolid() && rnd == 1) {
                    direction--;
                }

            } else if (y / size == 14 && x / size != 0 && x / size != 14) {//bottom side
                if(direction == 2 || direction == 4)
                    direction = chooseBetween(1,3);
                if (direction == 1) {
                    // if(isTagged())
                    if (!cells[13][x / size].isSolid() && !cells[13][(x + size - 1) / size].isSolid() && rnd == 1) {
                        direction++;
                    }
                }
                if (direction == 3) {
                    // if(isTagged())
                    if (!cells[13][x / size].isSolid() && !cells[13][(x + size - 1) / size].isSolid() && rnd == 1) {
                        direction--;
                    }

                }
            } else if (x / size == 0 && y / size != 0 && y / size != 14) {//left side
                if(direction == 1 || direction == 3)
                    direction = chooseBetween(2,4);
                if (direction == 4) {
                    // if(isTagged())
                    if (!cells[y / size][1].isSolid() && !cells[(y + size - 1) / size][(1) / size].isSolid() && rnd == 1) {
                        direction++;
                    }
                }
                if (direction == 2) {
                    // if(isTagged())
                    if (!cells[y / size][1].isSolid() && !cells[(y + size - 1) / size][(1) / size].isSolid() && rnd == 1) {
                        direction--;
                    }

                }
            }
        }
         if(x == saveX && y == saveY)
        {
            if(r.nextInt(2)==1)
                direction++;
            else direction--;


        }

      /*  if(x != saveX && y != saveY) {

            Log.d("meme position", "update " + nbOfTheSamePosition);
            nbOfTheSamePosition = 0;
        }
        else
        {
            nbOfTheSamePosition++;
            if(nbOfTheSamePosition==60)
            {
                if(tagged)

                x=0;
                y=0;
                direction=1;
                nbOfTheSamePosition = 0;
            }
        }*/
        /*else if(x/size == 0 || y/size == 0 || x/size ==14 || y/size == 14)
        {
            if(direction == 1)
            {
               // if (!cells[(y+size)/size][x/size].isSolid() && rnd == 1)
                //{
                            direction++;
                //}
            }
        }*/
        if(1000000000/10<time-oldTime) {


            rnd = r.nextInt(2);//r.nextInt(2);
            if(isTagged() && rnd ==0)
                color.setColor(Color.WHITE);
            if(isTagged() && rnd ==1)
                color.setColor(Color.GREEN);
            oldTime = time;
        }
        if(direction>4)
        {
            direction=1;
        }
        if(direction<1)
        {
            direction=4;
        }

        saveX = x;
        saveY = y;
    }


    public int chooseBetween(int a, int b)
    {
        if(r.nextInt(2)==1)
            return a;
        else return b;
    }

    public boolean isTagged() {
        return tagged;
    }

    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public void draw(Canvas canvas)
    {

        Rect r = new Rect(x+padding, y+padding, x+size-padding, y+size-padding);
        if(!isDead) {
            if(switchview)
                canvas.drawBitmap(MainActivity.fantomeV, null, r, null);
            else
                canvas.drawBitmap(MainActivity.fantomeV1, null, r, null);
        }

        if(1000000000/4<System.nanoTime()-oldTimeView)
        {
            switchview = !switchview;
            oldTimeView = System.nanoTime();
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        if(isDead)
            md.start();
        this.isDead = isDead;
    }
}
