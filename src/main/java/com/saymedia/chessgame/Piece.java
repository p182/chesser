package com.saymedia.chessgame;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by SayMedia on 21/06/2015.
 */
public class Piece extends ImageView{
    public Piece(Context c, String pname, int id){
        super(c);

        switch (pname){
            case "bbishop": this.setImageResource(R.drawable.bbishop); break;
            case "bking": this.setImageResource(R.drawable.bking); break;
            case "bknight": this.setImageResource(R.drawable.bknight); break;
            case "bpawn": this.setImageResource(R.drawable.bpawn); break;
            case "bqueen": this.setImageResource(R.drawable.bqueen); break;
            case "brook": this.setImageResource(R.drawable.brook); break;
            case "wbishop": this.setImageResource(R.drawable.wbishop); break;
            case "wking": this.setImageResource(R.drawable.wking); break;
            case "wknight": this.setImageResource(R.drawable.wknight); break;
            case "wpawn": this.setImageResource(R.drawable.wpawn); break;
            case "wqueen": this.setImageResource(R.drawable.wqueen); break;
            case "wrook": this.setImageResource(R.drawable.wrook); break;
        }

        this.setVisibility(View.VISIBLE);
        this.setId(id);

    };




}
