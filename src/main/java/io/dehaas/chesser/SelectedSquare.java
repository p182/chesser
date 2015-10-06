// Copyright 2015 Roni Harel
//
// This file is part of Chesser.
//
// Chesser is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Chesser is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Chesser.  If not, see <http://www.gnu.org/licenses/>.

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
