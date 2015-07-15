package com.saymedia.chessgame;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

/**
 * Created by SayMedia on 14/07/2015.
 */
public class Utils {
    Activity activity;
    int  color = Game.color;

    public Utils(Activity a){
        activity = a;
    }


    private int dp(double dp) {

        float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int)(dp * density);
    }

    public RelativeLayout.LayoutParams getPlaceParams(int x, int y){

        final DisplayMetrics metrics;
        DisplayMetrics _metrics = activity.getApplicationContext().getResources().getDisplayMetrics();
        metrics = _metrics;

        float density = metrics.density;
        int width = (int)(metrics.widthPixels/density);
        int height = (int)(metrics.heightPixels/density);

        System.out.println(width+"      -----     "+height);



//        double d = 36.9;
        double d =(((width-32)*348.35/386)/8);

        double X;
//        if(color==1){X = 17.4;})
        if(color==1){X = ((width-32)*22/386);}
//        else {X = 273.9;}
        else {X = ((width-32)*22/386) +d*7;}

        double Y;
//        if(color==1){Y = 18.4;}
        if(color==1){Y = ((width-32)*23/388);}
        else {Y = ((width-32)*23/388) +d*7;}


        RelativeLayout.LayoutParams L = new RelativeLayout.LayoutParams(dp(38),dp(38));
        L.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        L.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        switch (x){
            case 1: L.leftMargin = dp(X) + 0*dp(d)*color; break;
            case 2: L.leftMargin = dp(X) + 1*dp(d)*color; break;
            case 3: L.leftMargin = dp(X) + 2*dp(d)*color; break;
            case 4: L.leftMargin = dp(X) + 3*dp(d)*color; break;
            case 5: L.leftMargin = dp(X) + 4*dp(d)*color; break;
            case 6: L.leftMargin = dp(X) + 5*dp(d)*color; break;
            case 7: L.leftMargin = dp(X) + 6*dp(d)*color; break;
            case 8: L.leftMargin = dp(X) + 7*dp(d)*color; break;
            case 0: L.leftMargin = 0; break;

        }

        switch (y){
            case 1: L.bottomMargin = dp(Y) + 0*dp(d)*color; break;
            case 2: L.bottomMargin = dp(Y) + 1*dp(d)*color; break;
            case 3: L.bottomMargin = dp(Y) + 2*dp(d)*color; break;
            case 4: L.bottomMargin = dp(Y) + 3*dp(d)*color; break;
            case 5: L.bottomMargin = dp(Y) + 4*dp(d)*color; break;
            case 6: L.bottomMargin = dp(Y) + 5*dp(d)*color; break;
            case 7: L.bottomMargin = dp(Y) + 6*dp(d)*color; break;
            case 8: L.bottomMargin = dp(Y) + 7*dp(d)*color; break;
            case 0: L.leftMargin = 0; break;

        }

        L.height=dp(d);
        L.width=dp(d);

        return L;
    }

    /** Get aPiece object by its coordinate String. */
    public Piece findPieceByCoordinates(String c){
        // Black pieces
        if(c.equals(Game.br1.c())){return Game.br1;}
        if(c.equals(Game.br2.c())){return Game.br2;}
        if(c.equals(Game.bn1.c())){return Game.bn1;}
        if(c.equals(Game.bn2.c())){return Game.bn2;}
        if(c.equals(Game.bb1.c())){return Game.bb1;}
        if(c.equals(Game.bb2.c())){return Game.bb2;}
        if(c.equals(Game.bk.c())){return Game.bk;}
        if(c.equals(Game.bq.c())){return Game.bq;}
        if(c.equals(Game.bp1.c())){return Game.bp1;}
        if(c.equals(Game.bp2.c())){return Game.bp2;}
        if(c.equals(Game.bp3.c())){return Game.bp3;}
        if(c.equals(Game.bp4.c())){return Game.bp4;}
        if(c.equals(Game.bp5.c())){return Game.bp5;}
        if(c.equals(Game.bp6.c())){return Game.bp6;}
        if(c.equals(Game.bp7.c())){return Game.bp7;}
        if(c.equals(Game.bp8.c())){return Game.bp8;}
        // White pieces
        if(c.equals(Game.wr1.c())){return Game.wr1;}
        if(c.equals(Game.wr2.c())){return Game.wr2;}
        if(c.equals(Game.wn1.c())){return Game.wn1;}
        if(c.equals(Game.wn2.c())){return Game.wn2;}
        if(c.equals(Game.wb1.c())){return Game.wb1;}
        if(c.equals(Game.wb2.c())){return Game.wb2;}
        if(c.equals(Game.wk.c())){return Game.wk;}
        if(c.equals(Game.wq.c())){return Game.wq;}
        if(c.equals(Game.wp1.c())){return Game.wp1;}
        if(c.equals(Game.wp2.c())){return Game.wp2;}
        if(c.equals(Game.wp3.c())){return Game.wp3;}
        if(c.equals(Game.wp4.c())){return Game.wp4;}
        if(c.equals(Game.wp5.c())){return Game.wp5;}
        if(c.equals(Game.wp6.c())){return Game.wp6;}
        if(c.equals(Game.wp7.c())){return Game.wp7;}
        if(c.equals(Game.wp8.c())){return Game.wp8;}
        else{return null;}
    }
}
