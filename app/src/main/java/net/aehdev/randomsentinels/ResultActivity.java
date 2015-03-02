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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ResultActivity extends OptionsMenuActivity {

    private int numHeroes;
    private SentinelsDataSource mDataSource;
    private String[] expansions;

    private static Random sRandom = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mDataSource = new SentinelsDataSource(this);
        mDataSource.open();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> expansionsSet = prefs.getStringSet(SettingsActivity.KEY_PREF_EXPANSIONS,
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

    private enum ElementType {
        HERO, VILLAIN, ENVIRONMENT
    }

    protected class QueryDatabaseTask extends AsyncTask<ElementType, Void,
                                                             List<? extends GameElement>> {

        private ElementType type;

        @InjectViews({R.id.result_hero_1, R.id.result_hero_2, R.id.result_hero_3,
                      R.id.result_hero_4, R.id.result_hero_5})
        TextView[] mHeroes;

        @InjectViews({R.id.result_vengeful_1, R.id.result_vengeful_2, R.id.result_vengeful_3,
                      R.id.result_vengeful_4, R.id.result_vengeful_5})
        TextView[] mVengefuls;

        @InjectView(R.id.result_villain) TextView mVillain;
        @InjectView(R.id.result_environment) TextView mEnvironment;

        public QueryDatabaseTask() {
            ButterKnife.inject(this, ResultActivity.this);
        }

        @Override
        protected List<? extends GameElement> doInBackground(ElementType... params) {
            type = params[0];
            switch (type) {
                case HERO:
                    Set<Hero> heroes = new HashSet<>();
                    List<Hero> allHeroes = mDataSource.getHeroesByExpansion(expansions);
                    while (heroes.size() < numHeroes) {
                        heroes.add(allHeroes.get(sRandom.nextInt(allHeroes.size())));
                    }
                    return new ArrayList<>(heroes);
                case VILLAIN:
                    List<Villain> resultVillain = new ArrayList<>();
                    List<Villain> villains = mDataSource.getVillainsByExpansion(expansions);
                    Villain villain = villains.get(sRandom.nextInt(villains.size()));

                    /* The Vengeful Five are weird and require special handling */
                    if (villain.getId() == VengefulFive.ID) {
                        villain = new VengefulFive(numHeroes);
                    }

                    resultVillain.add(villain);
                    return resultVillain;
                case ENVIRONMENT:
                    List<Environment> resultEnv = new ArrayList<>();
                    List<Environment> environments =
                            mDataSource.getEnvironmentsByExpansion(expansions);
                    Environment environment =
                            environments.get(sRandom.nextInt(environments.size()));
                    resultEnv.add(environment);
                    return resultEnv;
                default:     // something has gone terribly wrong
                    return null;
            }
        }

        @Override
        protected void onPostExecute(List<? extends GameElement> result) {
            switch (type) {
                case HERO:
                    mHeroes[0].setText(result.get(0).toString());
                    mHeroes[1].setText(result.get(1).toString());
                    mHeroes[2].setText(result.get(2).toString());
                    if (numHeroes > 3) {
                        mHeroes[3].setText(result.get(3).toString());
                        mHeroes[3].setVisibility(View.VISIBLE);
                    }
                    if (numHeroes > 4) {
                        mHeroes[4].setText(result.get(4).toString());
                        mHeroes[4].setVisibility(View.VISIBLE);
                    }
                    break;
                case VILLAIN:
                    if (result.get(0).getId() != VengefulFive.ID) {
                        mVillain.setText(result.get(0).toString());
                    } else {
                        mVillain.setVisibility(View.GONE);
                        List<Villain> vengeful =
                                new ArrayList<>(((VengefulFive) result.get(0)).getVengefulOnes());
                        mVengefuls[0].setText(vengeful.get(0).toString());
                        mVengefuls[0].setVisibility(View.VISIBLE);
                        mVengefuls[1].setText(vengeful.get(1).toString());
                        mVengefuls[1].setVisibility(View.VISIBLE);
                        mVengefuls[2].setText(vengeful.get(2).toString());
                        mVengefuls[2].setVisibility(View.VISIBLE);
                        if (numHeroes > 3) {
                            mVengefuls[3].setText(vengeful.get(3).toString());
                            mVengefuls[3].setVisibility(View.VISIBLE);
                        }
                        if (numHeroes > 4) {
                            mVengefuls[4].setText(vengeful.get(4).toString());
                            mVengefuls[4].setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case ENVIRONMENT:
                    mEnvironment.setText(result.get(0).toString());
                    break;
                default:
            }
        }
    }
}
