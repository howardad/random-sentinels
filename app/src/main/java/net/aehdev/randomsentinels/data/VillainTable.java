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

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class VillainTable {

    // Table description
    public static final String TABLE_VILLAINS = "villains";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DIFFICULTY = "difficulty";
    public static final String COLUMN_EXPANSION = "expansion";

    // Database creation statement
    public static final String DATABASE_CREATE = "create table "
                                                 + TABLE_VILLAINS
                                                 + "("
                                                 + COLUMN_ID +
                                                 " integer primary key autoincrement, "
                                                 + COLUMN_NAME + " text not null, "
                                                 + COLUMN_DIFFICULTY + " integer, "
                                                 + COLUMN_EXPANSION + " text not null"
                                                 + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(VillainTable.class.getName(),
              "Upgrading database from version " + oldVersion + " to " + newVersion +
              ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_VILLAINS);
        onCreate(database);
    }
}
