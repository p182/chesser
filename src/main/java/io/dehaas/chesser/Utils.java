package io.dehaas.chesser;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

/**
 * Collection of utility methods.
 */
public class Utils {
    Activity activity;

    public Utils(Activity a){
        activity = a;
    }


    public int dp(double dp) {

        float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int)(dp * density);
    }

    public RelativeLayout.LayoutParams getPlaceParams(int x, int y){
        int  color = Game.color;

        final DisplayMetrics metrics;
        DisplayMetrics _metrics = activity.getApplicationContext().getResources().getDisplayMetrics();
        metrics = _metrics;

        float density = metrics.density;
        int width = (int)(metrics.widthPixels/density);
        int height = (int)(metrics.heightPixels/density);


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

    /** Get a Piece object by its coordinate String. */
    public Piece findPieceByCoordinates(String c){
        int  color = Game.color;
        // If the player is white
        if(color==1) {
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
            else{return null;}
        }
        // If the player is black
        else {
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

    /** Get a Piece object by its ID int. */
    public Piece findPieceById(int id){
            System.out.println("find piece by ID");
            System.out.println("id:" + id);
        // Black pieces
        if(id == Game.br1.getId()){return Game.br1;}
        if(id == Game.br2.getId()){return Game.br2;}
        if(id == Game.bn1.getId()){return Game.bn1;}
        if(id == Game.bn2.getId()){return Game.bn2;}
        if(id == Game.bb1.getId()){return Game.bb1;}
        if(id == Game.bb2.getId()){return Game.bb2;}
        if(id == Game.bk.getId()){return Game.bk;}
        if(id == Game.bq.getId()){return Game.bq;}
        if(id == Game.bp1.getId()){return Game.bp1;}
        if(id == Game.bp2.getId()){return Game.bp2;}
        if(id == Game.bp3.getId()){return Game.bp3;}
        if(id == Game.bp4.getId()){return Game.bp4;}
        if(id == Game.bp5.getId()){return Game.bp5;}
        if(id == Game.bp6.getId()){return Game.bp6;}
        if(id == Game.bp7.getId()){return Game.bp7;}
        if(id == Game.bp8.getId()){return Game.bp8;}
        // White pieces
        if(id == Game.wr1.getId()){return Game.wr1;}
        if(id == Game.wr2.getId()){return Game.wr2;}
        if(id == Game.wn1.getId()){return Game.wn1;}
        if(id == Game.wn2.getId()){return Game.wn2;}
        if(id == Game.wb1.getId()){return Game.wb1;}
        if(id == Game.wb2.getId()){return Game.wb2;}
        if(id == Game.wk.getId()){return Game.wk;}
        if(id == Game.wq.getId()){return Game.wq;}
        if(id == Game.wp1.getId()){return Game.wp1;}
        if(id == Game.wp2.getId()){return Game.wp2;}
        if(id == Game.wp3.getId()){return Game.wp3;}
        if(id == Game.wp4.getId()){return Game.wp4;}
        if(id == Game.wp5.getId()){return Game.wp5;}
        if(id == Game.wp6.getId()){return Game.wp6;}
        if(id == Game.wp7.getId()){return Game.wp7;}
        if(id == Game.wp8.getId()){return Game.wp8;}
        // If canot find piece matching the id it is a pawn that promoted id.
        // Devide by 1000 and try again.
        else{
//            System.out.println("pawn id:" + id/1000);
//            return null;

            // Set the new image to the promoted pawn.
            if(id/1000!=0){
                if(id%1000==1){findPieceById(id/1000).setImageResource(R.drawable.wrook);}
                if(id%1000==2){findPieceById(id/1000).setImageResource(R.drawable.wknight);}
                if(id%1000==3){findPieceById(id/1000).setImageResource(R.drawable.wbishop);}
                if(id%1000==4){findPieceById(id/1000).setImageResource(R.drawable.wqueen);}
                if(id%1000==17){findPieceById(id/1000).setImageResource(R.drawable.brook);}
                if(id%1000==18){findPieceById(id/1000).setImageResource(R.drawable.bknight);}
                if(id%1000==19){findPieceById(id/1000).setImageResource(R.drawable.bbishop);}
                if(id%1000==20){findPieceById(id/1000).setImageResource(R.drawable.bqueen);}
            }

            return findPieceById(id/1000);
        }
    }

    /** Check if own king is in check change. */
    public boolean myKingInCheck(){

        boolean b = false;
        Piece[] allPieces = getAllPieces();

        for (Piece piece : allPieces){
//            Piece piece = findPieceById(i);
//            System.out.println(piece.moves()+" : "+i);
//            piece.movesCoor = "";

            String[] cmoves = piece.moves().split(",");
            for(String coor : cmoves){
//                System.out.println(coor);
                if((coor.equals(Game.wk.c())&&Game.color==1)||(coor.equals(Game.bk.c())&&Game.color==-1)){
                    b = true;
                    break;
                }
            }

//          System.out.println(b);

        }

        return b;
    }

    /** Check if opponent's king is in check. */
    public boolean opponentKingInCheck(){

        boolean b = false;
        Piece[] allPieces = getAllPieces();

        for (Piece piece : allPieces){
//            Piece piece = findPieceById(i);
            String[] cmoves = piece.moves().split(",");
            for(String coor : cmoves){
                if((coor.equals(Game.bk.c())&&Game.color==1)||(coor.equals(Game.wk.c())&&Game.color==-1)){
                    b = true;
                    break;
                }
            }
        }

        return b;
    }

    /** Returns an array of all the pieces. */
    public Piece[] getAllPieces(){
        Piece[] pieceArray = {
                Game.wr1,
                Game.wr2,
                Game.wn1,
                Game.wn2,
                Game.wb1,
                Game.wb2,
                Game.wk,
                Game.wq,
                Game.wp1,
                Game.wp2,
                Game.wp3,
                Game.wp4,
                Game.wp5,
                Game.wp6,
                Game.wp7,
                Game.wp8,

                Game.br1,
                Game.br2,
                Game.bn1,
                Game.bn2,
                Game.bb1,
                Game.bb2,
                Game.bk,
                Game.bq,
                Game.bp1,
                Game.bp2,
                Game.bp3,
                Game.bp4,
                Game.bp5,
                Game.bp6,
                Game.bp7,
                Game.bp8,
        };

        return pieceArray;
    }
}
