package net.aehdev.randomsentinels;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class SetupActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        /* Set up spinner for choosing number of heroes */
        Spinner nHeroesSpinner = (Spinner) findViewById(R.id.spinner_num_players);
        ArrayAdapter<CharSequence> nHeroesSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.array_num_heroes,
                                                android.R.layout.simple_spinner_item);
        nHeroesSpinnerAdapter.setDropDownViewResource(android.R.layout
                                                              .simple_spinner_dropdown_item);
        nHeroesSpinner.setAdapter(nHeroesSpinnerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
