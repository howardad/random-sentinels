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

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class AboutActivity extends Activity {

    private StringBuilder oflBangers, calligraphy, butterKnife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        new Thread(new Runnable() {
            public void run() {
                try {
                    parseInfo();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void parseInfo() throws IOException {
        final Resources resources = getResources();
        InputStream bangersStream = resources.openRawResource(R.raw.ofl_bangers);
        InputStream calligStream = resources.openRawResource(R.raw.calligraphy);
        InputStream butterStream = resources.openRawResource(R.raw.butter_knife);
        BufferedReader bangersReader = new BufferedReader(new InputStreamReader(bangersStream));
        BufferedReader calligReader = new BufferedReader(new InputStreamReader(calligStream));
        BufferedReader butterReader = new BufferedReader(new InputStreamReader(butterStream));
        String line;

        oflBangers = new StringBuilder(bangersStream.available());
        while ((line = bangersReader.readLine()) != null) {
            oflBangers.append(line + "\n");
        }

        calligraphy = new StringBuilder(calligStream.available());
        while ((line = calligReader.readLine()) != null) {
            calligraphy.append(line + "\n");
        }

        butterKnife = new StringBuilder(butterStream.available());
        while ((line = butterReader.readLine()) != null) {
            butterKnife.append(line + "\n");
        }
    }
}
