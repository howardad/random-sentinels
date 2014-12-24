/*
 * Copyright (c) 2014 Adam Howard <aeh AT aehdev DOT net>.
 *
 * This file is part of Random Sentinels.
 *
 * Random Sentinels is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Random Sentinels is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Random Sentinels. If not, see <http://www.gnu.org/licenses/>.
 */

package net.aehdev.randomsentinels;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class SetupActivity extends ActionBarActivity {

    public static final String EXTRA_NUM_HEROES = "net.aehdev.randomsentinels.NUM_HEROES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        /* Set up spinner for choosing number of heroes */
        Spinner nHeroesSpinner = (Spinner) findViewById(R.id.spinner_num_players);
        ArrayAdapter<CharSequence> nHeroesSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.num_heroes,
                                                android.R.layout.simple_spinner_item);
        nHeroesSpinnerAdapter.setDropDownViewResource(android.R.layout
                                                              .simple_spinner_dropdown_item);
        nHeroesSpinner.setAdapter(nHeroesSpinnerAdapter);

        /* Set up button */
        Button goButton = (Button) findViewById(R.id.button_randomize);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner nHeroesSpinner = (Spinner) findViewById(R.id.spinner_num_players);
                String selection = (String) nHeroesSpinner.getSelectedItem();
                int nHeroes = Integer.parseInt(selection);
                Intent intent = new Intent(SetupActivity.this, ResultActivity.class);
                intent.putExtra(EXTRA_NUM_HEROES, nHeroes);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
