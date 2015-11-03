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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Initial UI.
 */
public class Welcome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        System.out.println("onCreate completed");
    }

    public void buttonOnClick2(View v){ startActivity(new Intent("chess.connectToServer")); }

    public void buttonOnClick3(View v){ startActivity(new Intent("chess.openServer")); }

    public void showInfo(View v){
        LinearLayout info = (LinearLayout)findViewById(R.id.linearLayout);
        info.bringToFront();

        if(info.getVisibility()==View.INVISIBLE) info.setVisibility(View.VISIBLE);
        else info.setVisibility(View.INVISIBLE);
    }

    public void closeInfo(View v){
        LinearLayout info = (LinearLayout)findViewById(R.id.linearLayout);
        info.setVisibility(View.INVISIBLE);
    }
}
