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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class AboutActivity extends AppCompatActivity {

    private StringBuilder oflBangers, calligraphy, butterKnife;
    @InjectView(R.id.about_text) TextView aboutTextView;
    @InjectView(R.id.text_bangers_license) TextView aboutBangers;
    @InjectView(R.id.text_calligraphy_license) TextView aboutCalligraphy;
    @InjectView(R.id.text_butter_knife_license) TextView aboutButterKnife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);

        aboutTextView.setText("Random Sentinels version " + getVersionName());

        new Thread(new Runnable() {
            public void run() {
                try {
                    parseInfo();
                    aboutBangers.setText(Html.fromHtml(oflBangers.toString()));
                    aboutCalligraphy.setText(Html.fromHtml(calligraphy.toString()));
                    aboutButterKnife.setText(Html.fromHtml(butterKnife.toString()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();


    }

    private String getVersionName() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    @OnClick({R.id.title_bangers_license, R.id.title_calligraphy_license,
            R.id.title_butter_knife_license})
    public void toggleText(TextView view) {
        switch (view.getId()) {
            case R.id.title_bangers_license:
                if (aboutBangers.getVisibility() == View.GONE) {
                    aboutBangers.setVisibility(View.VISIBLE);
                } else {
                    aboutBangers.setVisibility(View.GONE);
                }
                break;
            case R.id.title_calligraphy_license:
                if (aboutCalligraphy.getVisibility() == View.GONE) {
                    aboutCalligraphy.setVisibility(View.VISIBLE);
                } else {
                    aboutCalligraphy.setVisibility(View.GONE);
                }
                break;
            case R.id.title_butter_knife_license:
                if (aboutButterKnife.getVisibility() == View.GONE) {
                    aboutButterKnife.setVisibility(View.VISIBLE);
                } else {
                    aboutButterKnife.setVisibility(View.GONE);
                }
                break;
        }
    }

    @OnClick(R.id.about_text)
    public void gotoGithub() {
        Uri rsGithub = Uri.parse("https://github.com/howardad/random-sentinels");
        Intent intent = new Intent(Intent.ACTION_VIEW, rsGithub);
        startActivity(intent);
    }
}
