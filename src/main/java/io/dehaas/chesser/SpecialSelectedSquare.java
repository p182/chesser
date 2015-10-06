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

/**
 * Special selected green squares for castling and taking en-passant.
 */
public class SpecialSelectedSquare extends SelectedSquare {
    public SpecialSelectedSquare(Context c, int X, int Y, int id, int TYPE){
        super(c,X,Y,id);
        this.setImageResource(R.drawable.special_selected);
        type = TYPE;
    }

    /** 1=castling_rook_1 , 2=castling_rook_2 , 3=en_passant */
    int type;
}
