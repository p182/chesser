package com.saymedia.chessgame;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by SayMedia on 24/06/2015.
 */
public class SelectedSquare extends ImageView {
    public SelectedSquare(Context c, int id){
        super(c);
        this.setImageResource(R.drawable.selected);
        this.setVisibility(View.VISIBLE);
        this.setId(id);
    };

//    Context c =

}
