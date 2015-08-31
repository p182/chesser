package io.dehaas.chesser;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Selected blue squares.
 */
public class SelectedSquare extends ImageView {
    public SelectedSquare(Context c, int X, int Y, int id){
        super(c);
        this.setImageResource(R.drawable.selected);
        this.setVisibility(View.VISIBLE);
        this.setId(id);

        x = X;
        y = Y;
    };


    public int x = 0;
    public int y = 0;


}
