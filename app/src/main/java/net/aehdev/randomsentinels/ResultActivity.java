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
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.aehdev.randomsentinels.data.SentinelsDataSource;
import net.aehdev.randomsentinels.model.Environment;
import net.aehdev.randomsentinels.model.GameElement;
import net.aehdev.randomsentinels.model.Hero;
import net.aehdev.randomsentinels.model.VengefulFive;
import net.aehdev.randomsentinels.model.Villain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ResultActivity extends ActionBarActivity {

    private Set<Hero> mHeroes;
    private int numHeroes;
    private Villain mVillain;
    private Set<Villain> mVengefulFive;
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

        pickHeroes();
        pickVillain();
        pickEnvironment();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(new CalligraphyContextWrapper(base));
    }

    private void pickHeroes() {
        new QueryDatabaseTask().execute(ElementType.HERO);
    }

    private void pickVillain() {
        new QueryDatabaseTask().execute(ElementType.VILLAIN);
    }

    private void pickEnvironment() {
        new QueryDatabaseTask().execute(ElementType.ENVIRONMENT);
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

    private enum ElementType {
        HERO, VILLAIN, ENVIRONMENT
    }

    private class QueryDatabaseTask extends AsyncTask<ElementType, Void,
                                                             List<? extends GameElement>> {

        private ElementType type;

        @Override
        protected List<? extends GameElement> doInBackground(ElementType... params) {
            type = params[0];
            switch (type) {
                case HERO:
                    mHeroes = new HashSet<>();
                    List<Hero> allHeroes = mDataSource.getHeroesByExpansion(expansions);
                    while (mHeroes.size() < numHeroes) {
                        mHeroes.add(allHeroes.get(sRandom.nextInt(allHeroes.size())));
                    }
                    return new ArrayList<>(mHeroes);
                case VILLAIN:
                    List<Villain> resultVillain = new ArrayList<>();
//                    List<Villain> villains = mDataSource.getVillainsByExpansion(expansions);
//                    mVillain = villains.get(sRandom.nextInt(villains.size()));
//
//                    /* The Vengeful Five are weird and require special handling */
//                    if (mVillain.getId() == VengefulFive.ID) {
                        mVillain = new VengefulFive(numHeroes);
//                    }

                    resultVillain.add(mVillain);
                    return resultVillain;
                case ENVIRONMENT:
                    List<Environment> resultEnv = new ArrayList<>();
                    List<Environment> environments =
                            mDataSource.getEnvironmentsByExpansion(expansions);
                    mEnvironment = environments.get(sRandom.nextInt(environments.size()));
                    resultEnv.add(mEnvironment);
                    return resultEnv;
                default:     // something has gone terribly wrong
                    return null;
            }
        }

        @Override
        protected void onPostExecute(List<? extends GameElement> result) {
            switch (type) {
                case HERO:
                    TextView hero1, hero2, hero3, hero4, hero5;
                    hero1 = (TextView) findViewById(R.id.result_hero_1);
                    hero1.setText(result.get(0).toString());
                    hero2 = (TextView) findViewById(R.id.result_hero_2);
                    hero2.setText(result.get(1).toString());
                    hero3 = (TextView) findViewById(R.id.result_hero_3);
                    hero3.setText(result.get(2).toString());
                    if (numHeroes > 3) {
                        hero4 = (TextView) findViewById(R.id.result_hero_4);
                        hero4.setText(result.get(3).toString());
                        hero4.setVisibility(View.VISIBLE);
                    }
                    if (numHeroes > 4) {
                        hero5 = (TextView) findViewById(R.id.result_hero_5);
                        hero5.setText(result.get(4).toString());
                        hero5.setVisibility(View.VISIBLE);
                    }
                    break;
                case VILLAIN:
                    if (result.get(0).getId() != VengefulFive.ID) {
                        TextView villainView = (TextView) findViewById(R.id.result_villain);
                        villainView.setText(result.get(0).toString());
                    } else {
                        TextView wrongVillain, vengeful1, vengeful2, vengeful3, vengeful4,
                                vengeful5;
                        wrongVillain = (TextView) findViewById(R.id.result_villain);
                        wrongVillain.setVisibility(View.GONE);
                        List<Villain> vengeful =
                                new ArrayList<>(((VengefulFive) result.get(0)).getVengefulOnes());
                        vengeful1 = (TextView) findViewById(R.id.result_vengeful_1);
                        vengeful1.setText(vengeful.get(0).toString());
                        vengeful1.setVisibility(View.VISIBLE);
                        vengeful2 = (TextView) findViewById(R.id.result_vengeful_2);
                        vengeful2.setText(vengeful.get(1).toString());
                        vengeful2.setVisibility(View.VISIBLE);
                        vengeful3 = (TextView) findViewById(R.id.result_vengeful_3);
                        vengeful3.setText(vengeful.get(2).toString());
                        vengeful3.setVisibility(View.VISIBLE);
                        if (numHeroes > 3) {
                            vengeful4 = (TextView) findViewById(R.id.result_vengeful_4);
                            vengeful4.setText(vengeful.get(3).toString());
                            vengeful4.setVisibility(View.VISIBLE);
                        }
                        if (numHeroes > 4) {
                            vengeful5 = (TextView) findViewById(R.id.result_vengeful_5);
                            vengeful5.setText(vengeful.get(4).toString());
                            vengeful5.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case ENVIRONMENT:
                    TextView envView = (TextView) findViewById(R.id.result_environment);
                    envView.setText(result.get(0).toString());
                    break;
                default:
            }
        }
    }
}
