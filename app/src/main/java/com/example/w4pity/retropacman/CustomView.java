package com.example.w4pity.retropacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by W4pity on 25/04/2016.
 */
public class CustomView extends View {
    public static int sizeCell;
    static boolean isLoose = false, isWin = false;
    private boolean started = false;
    private Paint red, bleu, green, black, white, yellow;
    Pacman pacman;
    boolean finish;
    static int nbChoosen = 1;
    long currentTime = System.nanoTime();
    long currentMilliseconds = System.currentTimeMillis();
    static long timeBonusold = 0, oldTimeUpdate = 0;
    double timeFPS = 0;
    int fps = 0, ups = 0;
    int touchX, touchY;
    int pointsOfLevel = 0;
    private boolean modeJeu = false;
    static boolean bonusPresent = false;
    private boolean buildFinish = false;
    private int a = 14, b = 14;//to animate the building
    private long oldTimeBuild = 0;
    private int speedBuild = 10;
    private int lvlNumber = 0;
    private int songID = R.raw.musique;
    private MediaPlayer md;
    private int songFant = R.raw.mouaha, songDeth = R.raw.mort;
    private  MediaPlayer mdM;
    
    Cell[][] cells = new Cell[15][15];

    fantome[] fantomes = new fantome[5];
   // fantome f = new fantome();
    public CustomView(Context c) {
        super(c);
    }
    public CustomView(Context c, AttributeSet as) {
        super(c, as);
        md = MediaPlayer.create(c, songID);
        mdM = MediaPlayer.create(c, songDeth);
        //mdF = MediaPlayer.create(c, songFant);



    }
    public CustomView(Context c, AttributeSet as, int default_style) {
        super(c, as, default_style);

    }

    public void loadLevel()
    {
        if(nbChoosen == 0)
            for(int i = 0; i<15; i++)
                for(int j = 0; j<15; j++)
                    Constructor.terrain[i][j] = Constructor.edit[i][j];
        if(nbChoosen == 1)
            for(int i = 0; i<15; i++)
                for(int j = 0; j<15; j++)
                    Constructor.terrain[i][j] = Constructor.level1[i][j];
    }

     int nbFantomeToInitialize = 4;

       Random ra = new Random();
       Random rb = new Random();
    public void init() {
        pointsOfLevel=0;
        Constructor.loadLevel();
        if(!md.isPlaying())
            md.start();
       // Constructor.terrain = Constructor.edit;
       // Log.d("starting init", "je suis la fonction init ");
        sizeCell = this.getWidth()/15;
        pacman = new Pacman();
        Log.d("taille ecran", "init "+sizeCell);
        loadPaint();
        for(int i = 0; i<15; i++)
            for(int j = 0; j<15; j++) {
                cells[i][j] = new Cell(j*sizeCell, i*sizeCell, true);

                if (Constructor.terrain[i][j] == 1) {
                    cells[i][j].setIsSolid(false);
                    pointsOfLevel++;

                }
            }
      // fantomes = {new fantome(), new fantome(), new fantome(), new fantome(), new fantome()};
        while(nbFantomeToInitialize>=0)
        {
            int a = ra.nextInt(14);
            int b = rb.nextInt(14);
            if(Constructor.terrain[a][b] == 1 /*&& nb !=0*/) {
                fantomes[nbFantomeToInitialize] = new fantome(false);
                fantomes[nbFantomeToInitialize].x =b *sizeCell;
                fantomes[nbFantomeToInitialize].y = a *sizeCell;

               // Log.d("fantome", "init "+fantomes[nb].posX );
                nbFantomeToInitialize--;
            }
           /* else if(Constructor.terrain[a][b] == 1)
            {
                fantomes[nb] = new fantome(true);
                fantomes[nb].x =0;
                fantomes[nb].y = 4 *sizeCell;
                fantomes[nb].setDirection(4);
                nb--;
            }*/

        }
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!started) {
            canvas.drawBitmap(MainActivity.intro, null, new Rect(0, 0, MainActivity.width,  MainActivity.width), null);

            invalidate();
        } else {

            if (modeJeu)
                drawGameMode(canvas);
            else {

                drawEdit(canvas);
            }
        }


    }




public void drawGameMode(Canvas canvas)
{

        /////////////Start
         Log.d("casecheck", "onDraw "+pacman.getY()/MainActivity.height+"   "+pacman.getX()/MainActivity.width);
        currentTime = System.nanoTime();
        currentMilliseconds = System.currentTimeMillis();
        //if (currentTime - oldTimeDraw > 1000000000 / 60) {
if(buildFinish) {
    if (!finish)
        if (!isWin() && !isLoose()) {

            update();
        }
}
        drawEntity(canvas);


        //canvas.save();

        //  invalidate();
        //   oldTimeDraw = System.nanoTime();
        // }
        fps++;

        // if (currentTime - oldTimeUpdate > 1000000000 / 60) {

        //   oldTimeDraw = System.nanoTime();
        //}
        if(currentTime - timeFPS >1000000000)
        {
            Log.d("fpssss", "onDraw " + fps);
            fps = 0;
            timeFPS = System.nanoTime();

        }
        //  canvas.restore();
        invalidate();

}

    public void update()
    {
        pacman.update(cells);
        for(fantome f:fantomes) {
            f.update(cells);
            if(pacman.isBoosted()) {
                MainActivity.fantomeV = MainActivity.fantome1blue;
                MainActivity.fantomeV1 = MainActivity.fantome2blue;
            }
            else
            {
                MainActivity.fantomeV = MainActivity.fantome1;
                MainActivity.fantomeV1 = MainActivity.fantome2;
            }
        }

        if(!md.isPlaying())
            md.start();
        if(10000<currentMilliseconds-timeBonusold)
            if(!bonusPresent)
            {
                boolean bonusNotSet = true;
                while(bonusNotSet) {
                    int a = ra.nextInt(14);
                    int b = rb.nextInt(14);
                    if (!cells[b][a].isSolid()) {
                        cells[b][a].setContainBonus(true);
                        bonusPresent = true;
                        bonusNotSet = false;
                        Log.d("bonus", "update "+a + " "+b);
                    }
                }

            }

    }




    public void drawEdit(Canvas canvas)
    {
        if(buildFinish)
        for(int i = 0; i<15; i++)
            for(int j = 0; j<15; j++) {
                if(Constructor.edit[i][j] == 1)
                    canvas.drawBitmap(MainActivity.sol, null,new Rect(j*CustomView.sizeCell, i*CustomView.sizeCell,j*CustomView.sizeCell+CustomView.sizeCell, i*CustomView.sizeCell+CustomView.sizeCell), null);
                else
                    canvas.drawBitmap(MainActivity.mur, null, new Rect(j * CustomView.sizeCell, i * CustomView.sizeCell, j * CustomView.sizeCell + CustomView.sizeCell, i * CustomView.sizeCell + CustomView.sizeCell), null);
                Log.d("Constructor.edittt", "drawConstructor.edit ");
            }
        else
        {
           buildingMap(canvas, Constructor.edit);
        }


       invalidate();
    }


    public void buildingMap(Canvas canvas, int[][]map)
    {
        for(int i = 14; i>=b+1; i--)
            for(int j = 14; j>=0; j--) {
                if(map[i][j] == 1)
                    canvas.drawBitmap(MainActivity.sol, null,new Rect(j*CustomView.sizeCell, i*CustomView.sizeCell,j*CustomView.sizeCell+CustomView.sizeCell, i*CustomView.sizeCell+CustomView.sizeCell), null);
                else
                    canvas.drawBitmap(MainActivity.mur, null, new Rect(j * CustomView.sizeCell, i * CustomView.sizeCell, j * CustomView.sizeCell + CustomView.sizeCell, i * CustomView.sizeCell + CustomView.sizeCell), null);
                Log.d("Constructor.edittt", "drawConstructor.edit ");

            }


        for(int i = 14; i>=b; i--)
            for(int j = 14; j>=a; j--) {
                if(map[i][j] == 1)
                    canvas.drawBitmap(MainActivity.sol, null,new Rect(j*CustomView.sizeCell, i*CustomView.sizeCell,j*CustomView.sizeCell+CustomView.sizeCell, i*CustomView.sizeCell+CustomView.sizeCell), null);
                else
                    canvas.drawBitmap(MainActivity.mur, null, new Rect(j * CustomView.sizeCell, i * CustomView.sizeCell, j * CustomView.sizeCell + CustomView.sizeCell, i * CustomView.sizeCell + CustomView.sizeCell), null);
                Log.d("Constructor.edittt", "drawConstructor.edit ");

            }

        if(1000000000/speedBuild<System.nanoTime()-oldTimeBuild)
        {
            a--;
            if(a == -1) {
                a = 14;
                b--;
            }
            if(b == -1)
                buildFinish = true;
            oldTimeBuild = System.nanoTime();
            speedBuild +=2;
        }
    }
    public void drawEntity(Canvas canvas)
    {
        if(buildFinish) {
            for (Cell[] t : cells)
                for (Cell c : t) {
                    c.draw(canvas);
                }
            pacman.draw(canvas);
            for (fantome f : fantomes)
                f.draw(canvas);
        }
        else
        {
            if(nbChoosen == 0)
                buildFinish = true;
            buildingMap(canvas, Constructor.terrain);
        }
    }


    public boolean isWin()
    {
        Log.d("poney", "isWin "+pointsOfLevel+ " et "+pacman.getNbOfPoints());
        if(pointsOfLevel == pacman.getNbOfPoints()) {
            Toast.makeText(MainActivity.cont, "You win", Toast.LENGTH_SHORT).show();
            finish = true;
            isWin = true;
            return true;
        }
        return false;
       // Log.d("level point", "isWin :pacman points: )" + pacman.getNbOfPoints() + " of a total : " + pointsOfLevel);

    }

    public boolean isLoose()
    {
        for(fantome f:fantomes)
        {
            int marge = MainActivity.width*20/540;
            if((contain(f, pacman.getX()+(f.padding+marge), pacman.getY()+(f.padding+marge))
                    || contain(f, pacman.getX()+pacman.getSize()-(f.padding+marge), pacman.getY()+(f.padding+marge))
                    || contain(f, pacman.getX()+(f.padding+marge), pacman.getY()+pacman.getSize()-(f.padding+marge))
                    || contain(f, pacman.getX()+pacman.getSize()-(f.padding+marge), pacman.getY()+pacman.getSize()-(f.padding+marge))) && !f.isDead())
                if(pacman.isBoosted()) {

                    f.setIsDead(true);

                }

                else {
                    Toast.makeText(MainActivity.cont, "You loose", Toast.LENGTH_SHORT).show();
                    finish = true;
                    mdM.start();
                    isLoose = true;
                    return true;



                }
        }
        return false;
    }

    public boolean contain(Perso p, int x, int y)
    {
        return p.x < x && p.y < y
                && p.x+p.size-1>x && p.y <y
                && p.x<x && p.y+p.size-1 >y
                && p.x+p.size-1> x && p.y+p.size-1>y;
        //Rect r = new Rect(p.x, p.y, p.x+p.size, p.y+p.size);
        //return r.contains(x, y);
    }



    
    public boolean onTouchEvent(MotionEvent event) {
        if (!started) {

            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                init();
                started = true;
            }
        } else if (modeJeu) {///gme mode
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                touchX = (int) event.getX();
                touchY = (int) event.getY();
                invalidate();
                return true;
            }
            if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                int dir = viewDirection((int) event.getX(), (int) event.getY(), touchX, touchY);
                if (dir == 1/* &&  !cells[(pacman.getY()+pacman.padding)*15/MainActivity.width][((pacman.getX()+pacman.getSize())*15/MainActivity.width)+1].isSolid()
                        &&  !cells[(pacman.getY()+pacman.getSize()-pacman.padding)*15/MainActivity.width][((pacman.getX()+pacman.getSize())*15/MainActivity.width)+1].isSolid()*/)
                    pacman.setDirection(1);
                //  else
                //   pacman.setAnticipationMvt(1);
                if (dir == 2/*&& !cells[(pacman.getY()+pacman.getSize())*15/MainActivity.width+1][((pacman.getX()+pacman.getSize()-pacman.padding)*15/MainActivity.width)].isSolid()
                        &&  !cells[(pacman.getY()+pacman.getSize())*15/MainActivity.width+1][((pacman.getX()+pacman.padding)*15/MainActivity.width)].isSolid()*/)
                    pacman.setDirection(2);
                if (dir == 3 /*&&  !cells[(pacman.getY()+pacman.padding)*15/MainActivity.width][((pacman.getX())*15/MainActivity.width)-1].isSolid()
                        &&  !cells[(pacman.getY()+pacman.getSize()-pacman.padding)*15/MainActivity.width][((pacman.getX())*15/MainActivity.width)-1].isSolid()*/)
                    pacman.setDirection(3);
                if (dir == 4/* && !cells[(pacman.getY())*15/MainActivity.width-1][((pacman.getX()+pacman.padding)*15/MainActivity.width)].isSolid()
                        &&  !cells[(pacman.getY())*15/MainActivity.width-1][((pacman.getX()+pacman.getSize()-pacman.padding)*15/MainActivity.width)].isSolid()*/)
                    pacman.setDirection(4);


                invalidate();
                return true;
            }
            return super.onTouchEvent(event);
        } else {//Constructor.edit Mode
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                touchX = (int) event.getX();
                touchY = (int) event.getY();

                if (Constructor.edit[touchY / CustomView.sizeCell][touchX / CustomView.sizeCell] == 0)
                    Constructor.edit[touchY / CustomView.sizeCell][touchX / CustomView.sizeCell] = 1;
                else if (touchY / CustomView.sizeCell != 0 && touchX / CustomView.sizeCell != 0)
                    Constructor.edit[touchY / CustomView.sizeCell][touchX / CustomView.sizeCell] = 0;
                Log.d("modejeu", "onTouchEvent apres");

                invalidate();

                return true;
            }
            return super.onTouchEvent(event);
        }
        return super.onTouchEvent(event);

    }
  
    public int viewDirection(int x, int y, int oldX, int oldY) {
        Log.d("resultaaa", "viewDirection " + x + " " + y + " " + oldX + " " + oldY + " ");
        if(x-oldX>0)
            if(oldY-y>0)
                return (oldY - y) >= (x - oldX)?4:1;
            else
                return (x - oldX) >= (y - oldY)?1:2;
        else
            if(oldY-y>0)
                return (oldX - x) >= (oldY - y)?3:4;
            else
                return (oldX - x) >= (y - oldY)?3:2;
    }

    private void loadPaint()
    {
        red = new Paint(Paint.ANTI_ALIAS_FLAG);
        green = new Paint(Paint.ANTI_ALIAS_FLAG);
        black = new Paint(Paint.ANTI_ALIAS_FLAG);
        white = new Paint(Paint.ANTI_ALIAS_FLAG);
        yellow = new Paint(Paint.ANTI_ALIAS_FLAG);
        bleu = new Paint(Paint.ANTI_ALIAS_FLAG);
        red.setColor(0xFFFF0000);
        green.setColor(0xFF00FF00);
        bleu.setColor(0xFF0000FF);
        black.setColor(Color.BLACK);
        yellow.setColor(Color.YELLOW);
        white.setColor(Color.WHITE);
    }


    public boolean isModeJeu() {
        return modeJeu;
    }

    public void setModeJeu(boolean modeJeu) {
        this.modeJeu = modeJeu;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public boolean isBuildFinish() {
        return buildFinish;
    }

    public void setBuildFinish(boolean buildFinish) {
        this.buildFinish = buildFinish;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setSpeedbuild()
    {
        speedBuild =10;
    }
}

