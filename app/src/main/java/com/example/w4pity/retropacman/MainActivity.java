package com.example.w4pity.retropacman;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    static Context cont;
    static int nbCell = 30;
    static int width;
    static int height;
    static Bitmap pacmanLeft,pacmanDepth, pacmanWin, pacmanLeftClose, pacmanRight, pacmanRightClose, intro, pacmanBottom, pacmanBottomClose, pacmanTopClose, pacmanTop, mur, sol, fantome1, fantome2, fantome1blue, fantome2blue, fantomeV, fantomeV1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadRessources();

        fantomeV= fantome1;
        fantomeV1  = fantome2;

        Display d = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm= getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        //int height = d.getHeight();
        final CustomView c = (CustomView)findViewById(R.id.Cv);
        final Button StartLvlNb = (Button)findViewById(R.id.StartLvlNb);
        final EditText e = (EditText) findViewById(R.id.Number);
        ImageView imgV = (ImageView) findViewById(R.id.imageView);
        imgV.setImageResource(R.drawable.retro);
        e.setText("1");
        LinearLayout l = (LinearLayout) findViewById(R.id.ly);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.ly1);
        l1.setY(-60);
        l.setY(-20);
        cont = getApplicationContext();
        imgV.setY(-20);
        //l.setY(-height);
        c.setLayoutParams(new LinearLayout.LayoutParams(width, width+20));
        c.setY(-60);
        c.setBackgroundColor(Color.BLACK);
        //cEdit.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        //c.setY(-height);
        StartLvlNb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c.isModeJeu()) {
                    c.setModeJeu(false);
                    c.finish = false;
                    StartLvlNb.setText(R.string.play);
                    c.setA(14);
                    c.setB(14);
                    c.setBuildFinish(false);
                    c.setSpeedbuild();
                    c.isLoose=false;
                    c.isWin=false;
                }
                    else{
                    try {
                        CustomView.nbChoosen = Integer.parseInt(e.getText().toString());
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(cont, "Default level started: 1", Toast.LENGTH_SHORT).show();
                    }
                    c.setA(14);
                    c.setB(14);
                    c.setBuildFinish(false);
                    StartLvlNb.setText("Stop");
                    c.nbFantomeToInitialize = 4;
                    c.bonusPresent = false;
                    c.init();
                    c.setModeJeu(true);
                    c.setSpeedbuild();
                    c.isLoose = false;
                    c.isWin=false;
                }



            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadRessources()
    {
        int pacmanR = R.drawable.pacman_right;
        int pacmanRC = R.drawable.pacman_right_close;
        int pacmanB = R.drawable.pacman_bottom;
        int pacmanBC = R.drawable.pacman_bottom_close;
        int pacmanL = R.drawable.pacman_left;
        int pacmanLC = R.drawable.pacman_left_close;
        int pacmanT = R.drawable.pacman_top;
        int pacmanTC = R.drawable.pacman_top_close;
        int fantome1ID = R.drawable.fantome1;
        int fantome2ID = R.drawable.fantome2;
        int fantome1blueID = R.drawable.fantome1bleu;
        int fantome2blueID = R.drawable.fantome2bleu;
        int introID = R.drawable.intro;
        int pacmanDepthID = R.drawable.pacman_depth;
        int pacmanWinID = R.drawable.pacman_win;
        // int titreID = R.drawable.retro;
        pacmanWin = BitmapFactory.decodeResource(getResources(), pacmanWinID);
        intro = BitmapFactory.decodeResource(getResources(), introID);
       // int titreID = R.drawable.retro;
        pacmanDepth = BitmapFactory.decodeResource(getResources(), pacmanDepthID);
        fantome1 = BitmapFactory.decodeResource(getResources(), fantome1ID);
        fantome2 = BitmapFactory.decodeResource(getResources(), fantome2ID);
        fantome1blue = BitmapFactory.decodeResource(getResources(), fantome1blueID);
        fantome2blue = BitmapFactory.decodeResource(getResources(), fantome2blueID);
        pacmanRight = BitmapFactory.decodeResource(getResources(),pacmanR);
        pacmanRightClose = BitmapFactory.decodeResource(getResources(),pacmanRC);
        pacmanBottom = BitmapFactory.decodeResource(getResources(),pacmanB);
        pacmanBottomClose= BitmapFactory.decodeResource(getResources(),pacmanBC);
        pacmanLeft = BitmapFactory.decodeResource(getResources(),pacmanL);
        pacmanLeftClose = BitmapFactory.decodeResource(getResources(),pacmanLC);
        pacmanTop = BitmapFactory.decodeResource(getResources(),pacmanT);
        pacmanTopClose = BitmapFactory.decodeResource(getResources(),pacmanTC);
        //titre = BitmapFactory.decodeResource(getResources(), titreID);
        int murID = R.drawable.mur;
        mur = BitmapFactory.decodeResource(getResources(),murID);
        int solID = R.drawable.sol;
        sol = BitmapFactory.decodeResource(getResources(),solID);
    }
}
