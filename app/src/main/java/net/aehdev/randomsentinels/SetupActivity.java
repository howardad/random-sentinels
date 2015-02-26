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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SetupActivity extends OptionsMenuActivity {

    public static final String EXTRA_NUM_HEROES = "net.aehdev.randomsentinels.NUM_HEROES";

    @InjectView(R.id.spinner_num_players) Spinner mNumHeroesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        ButterKnife.inject(this);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        setupNumHeroesSpinner();
    }

    private void setupNumHeroesSpinner() {
        ArrayAdapter<CharSequence> nHeroesAdapter =
                ArrayAdapter.createFromResource(this, R.array.num_heroes,
                                                android.R.layout.simple_spinner_item);
        nHeroesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mNumHeroesSpinner.setAdapter(nHeroesAdapter);
    }

    @OnClick(R.id.button_randomize)
    public void onSentinelsButtonClick() {
        String selection = (String) mNumHeroesSpinner.getSelectedItem();
        int numHeroes = Integer.parseInt(selection);
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(EXTRA_NUM_HEROES, numHeroes);
        startActivity(intent);
    }
}
