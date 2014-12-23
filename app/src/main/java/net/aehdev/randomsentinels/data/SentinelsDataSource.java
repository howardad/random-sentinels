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

package net.aehdev.randomsentinels.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.aehdev.randomsentinels.model.Environment;
import net.aehdev.randomsentinels.model.Hero;
import net.aehdev.randomsentinels.model.Villain;

import java.util.ArrayList;
import java.util.List;

public class SentinelsDataSource {

    private SQLiteDatabase database;
    private SentinelsDatabaseHelper dbHelper;
    private String[] allHeroColumns = {HeroTable.COLUMN_ID, HeroTable.COLUMN_NAME,
                                       HeroTable.COLUMN_COMPLEXITY, HeroTable.COLUMN_EXPANSION};
    private String[] allVillainColumns = {VillainTable.COLUMN_ID, VillainTable.COLUMN_NAME,
                                          VillainTable.COLUMN_DIFFICULTY,
                                          VillainTable.COLUMN_EXPANSION};
    private String[] allEnvironmentColumns = {EnvironmentTable.COLUMN_ID,
                                              EnvironmentTable.COLUMN_NAME,
                                              EnvironmentTable.COLUMN_EXPANSION};

    public SentinelsDataSource(Context context) {
        dbHelper = new SentinelsDatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Hero> getHeroesByExpansion(String[] expansions) {
        List<Hero> heroes = new ArrayList<>();

        // something like SELECT * FROM heroes WHERE expansion IN (expansions) SORT BY expansion
        Cursor cursor = database.query(HeroTable.TABLE_HEROES, allHeroColumns,
                                       "expansion IN (" + makePlaceholders(expansions.length) + ")",
                                       expansions, null, null, "expansion");
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            heroes.add(cursorToHero(cursor));
            cursor.moveToNext();
        }

        cursor.close();

        return heroes;
    }

    public List<Villain> getVillainsByExpansion(String[] expansions) {
        List<Villain> villains = new ArrayList<>();

        Cursor cursor = database.query(VillainTable.TABLE_VILLAINS, allVillainColumns,
                                       "expansion IN (" + makePlaceholders(expansions.length) + ")",
                                       expansions, null, null, "expansion");
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            villains.add(cursorToVillain(cursor));
            cursor.moveToNext();
        }

        cursor.close();

        return villains;
    }

    public List<Environment> getEnvironmentsByExpansion(String[] expansions) {
        List<Environment> environments = new ArrayList<>();

        Cursor cursor = database.query(EnvironmentTable.TABLE_ENVIRONMENTS, allEnvironmentColumns,
                                       "expansion IN (" + makePlaceholders(expansions.length) + ")",
                                       expansions, null, null, "expansion");
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            environments.add(cursorToEnvironment(cursor));
            cursor.moveToNext();
        }

        cursor.close();

        return environments;
    }

    private String makePlaceholders(int len) {
        if (len < 1) {
            // The query would be invalid anyway
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    private Hero cursorToHero(Cursor cursor) {
        long id = cursor.getLong(0);
        String name = cursor.getString(1);
        int complexity = cursor.getInt(2);
        String expansion = cursor.getString(3);
        return new Hero(id, name, complexity, expansion);
    }

    private Villain cursorToVillain(Cursor cursor) {
        long id = cursor.getLong(0);
        String name = cursor.getString(1);
        int difficulty = cursor.getInt(2);
        String expansion = cursor.getString(3);
        return new Villain(id, name, difficulty, expansion);
    }

    private Environment cursorToEnvironment(Cursor cursor) {
        long id = cursor.getLong(0);
        String name = cursor.getString(1);
        String expansion = cursor.getString(2);
        return new Environment(id, name, expansion);
    }
}
