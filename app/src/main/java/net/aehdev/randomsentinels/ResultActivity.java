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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.aehdev.randomsentinels.data.SentinelsDataSource;
import net.aehdev.randomsentinels.model.Environment;
import net.aehdev.randomsentinels.model.Hero;
import net.aehdev.randomsentinels.model.Villain;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ResultActivity extends ActionBarActivity {

    private Set<Hero> mHeroes;
    private int numHeroes;
    private Villain mVillain;
    private Environment mEnvironment;
    private SentinelsDataSource mDataSource;
    private String[] expansions;

    private static Random sRandom = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mDataSource = new SentinelsDataSource(this);
        mDataSource.open();

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> expansionsSet = mPreferences.getStringSet(SettingsActivity.KEY_PREF_EXPANSIONS,
                                                              new HashSet<String>());
        expansions = expansionsSet.toArray(new String[expansionsSet.size()]);

        Intent intent = getIntent();
        numHeroes = intent.getIntExtra(SetupActivity.EXTRA_NUM_HEROES, 3);

//        pickHeroes();
//        pickVillain();
//        pickEnvironment();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(new CalligraphyContextWrapper(base));
    }

    private void pickHeroes() {
        mHeroes = new HashSet<>();
        List<Hero> heroes = mDataSource.getHeroesByExpansion(expansions);
        while (mHeroes.size() < numHeroes) {
            mHeroes.add(heroes.get(sRandom.nextInt(heroes.size())));
        }
    }

    private void pickVillain() {
        List<Villain> villains = mDataSource.getVillainsByExpansion(expansions);
        mVillain = villains.get(sRandom.nextInt(villains.size()));
    }

    private void pickEnvironment() {
        List<Environment> environments = mDataSource.getEnvironmentsByExpansion(expansions);
        mEnvironment = environments.get(sRandom.nextInt(environments.size()));
    }

    @Override
    protected void onResume() {
        mDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mDataSource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
