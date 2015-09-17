package io.dehaas.chesser;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Helps generate a new game state.
 */
public class NewState{
    String s;
    Activity activity;
    Utils u;

    public NewState(String string, Activity a){
        activity = a;
        s=string;
    }

    /** Moves a piece by its cId. */
    void movePiece(String cId){

        Vibrator v = (Vibrator) activity.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        // If vibrate is on
        if(Game.vibrate) {
            // Vibrate for 500 milliseconds
            v.vibrate(50);
        }

        RelativeLayout rl = (RelativeLayout)activity.findViewById(R.id.fragment);

        String[] cIdArray = cId.split(":");

        int x = cIdArray[0].charAt(0) - 48;
        int y = cIdArray[0].charAt(1) - 48;

        Piece piece = u.findPieceById(Integer.parseInt(cIdArray[1]));
        piece.setId(Integer.parseInt(cIdArray[1]));


        // If the id is not standart then
/*        if(piece.getId()/100!=0){
            if(piece.getId()%1000==1){piece.setImageResource(R.drawable.wrook);}
            if(piece.getId()%1000==2){piece.setImageResource(R.drawable.wknight);}
            if(piece.getId()%1000==3){piece.setImageResource(R.drawable.wbishop);}
            if(piece.getId()%1000==4){piece.setImageResource(R.drawable.wqueen);}
            if(piece.getId()%1000==17){piece.setImageResource(R.drawable.brook);}
            if(piece.getId()%1000==18){piece.setImageResource(R.drawable.bknight);}
            if(piece.getId()%1000==19){piece.setImageResource(R.drawable.bbishop);}
            if(piece.getId()%1000==20){piece.setImageResource(R.drawable.bqueen);}
        }
*/

/*
        // To be able to load a game from a game where pieces have been removed.
        rl.removeView(piece);
        rl.addView(piece);
*/
        piece.x = x;
        piece.y = y;
        if(x==0&&y==0){
//            rl.removeView(piece);
            piece.setVisibility(View.INVISIBLE);
        }
        else {
            piece.setLayoutParams(u.getPlaceParams(x, y));
            piece.setVisibility(View.VISIBLE);
        }
    }

    void createNewState(){

        RelativeLayout rl = (RelativeLayout) activity.findViewById(R.id.fragment);
        for (int id = 100; id < 6700; id += 100) {
            rl.removeView(activity.findViewById(id));
        }

        TextView turnNotifier = (TextView)activity.findViewById(R.id.turnNotifier);

        u= new Utils(activity);
//        System.out.println("movesCoor: " + s);

        String[] state = s.split(";");

        if(state[1].equals("white")){
            Game.color = 1;
        }
        if(state[1].equals("black")){
            Game.color = -1;
        }

        if(state[2].equals("myTurn")){
            Game.myTurn = true;
        }
        if(state[2].equals("opTurn")){
            Game.myTurn = false;
        }

//        System.out.println(array[0]);
/*
        // If both players a well synchronized
        if(array[0].equals(Game.getWPCIDs() +","+ Game.getBPCIDs())){
            movePiece(array[1]);
        }
        else{
            final AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("Error").setMessage("Opponent game not synchronized.").show();
        }
*/
        String[] array = state[0].split(",");
//        System.out.println(state[0]);

        for (int i = 0; i < array.length; i++) {
            movePiece(array[i]);
//            System.out.println(array[i]);
        }

        // Notify turn and in-check status.
        if(Game.myTurn) {
            if (u.myKingInCheck()) {
                // Player's king in check - show that is player's turn and that the king is in check.
                Game.kingInCheck = true;
                if (Game.color == 1) {
                    turnNotifier.setText(R.string.white_you_in_check);
                } else {
                    turnNotifier.setText(R.string.black_you_in_check);
                }
            } else {
                // Player's king not in check - show that it is player's turn.
                turnNotifier.setText(R.string.player_turn);
            }
        }
        else {
            // Notify opponent's turn and whether opponent is in check.
            if (u.opponentKingInCheck()) {
                Game.kingInCheck = true;
                if (Game.color == 1) {
                    turnNotifier.setText(R.string.black_opponent_in_check);
                } else {
                    turnNotifier.setText(R.string.white_opponent_in_check);
                }
            } else {
                // Opponent not in check.
                turnNotifier.setText(R.string.opponent_turn);
            }
        }

        // Run the OpponentKingInCheckmate thread in case its chake mate
        MyKingInCheckmateThread myKingInCheckmateThreadThread = new MyKingInCheckmateThread(activity);
        myKingInCheckmateThreadThread.start();


/*
        // If its a turn received and not a game that is being loaded.
        if(state[1].equals("null")&&state[2].equals("null")) {
            // Run the OpponentKingInCheckmate thread in case its chake mate
            OpponentKingInCheckmateThread opponentKingInCheckmateThreadThread = new OpponentKingInCheckmateThread(activity);
            opponentKingInCheckmateThreadThread.start();
        }
*/

    }
}
