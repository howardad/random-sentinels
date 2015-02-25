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

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class SentinelsDatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "rsdata.db";
    private static final int DATABASE_VERSION = 1;

    public SentinelsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    @Override
//    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//        HeroTable.onUpgrade(database, oldVersion, newVersion);
//        VillainTable.onUpgrade(database, oldVersion, newVersion);
//        EnvironmentTable.onUpgrade(database, oldVersion, newVersion);
//    }
}
