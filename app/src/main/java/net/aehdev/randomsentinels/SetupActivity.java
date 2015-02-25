/*
 * Copyright (c) 2015 Adam Howard <aeh AT aehdev DOT net>.
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class SetupActivity extends OptionsMenuActivity {

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
}
