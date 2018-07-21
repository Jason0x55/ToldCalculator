package com.toldcalculator.android.tc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.toldcalculator.android.tc.controller.helpers.BundleConstants;
import com.toldcalculator.android.tc.controller.NewFlightFragment;
import com.toldcalculator.android.tc.controller.SavedDataFragment;
import com.toldcalculator.android.tc.controller.SettingsFragment;
import com.toldcalculator.android.tc.controller.WeatherFragment;
import com.toldcalculator.android.tc.model.db.ToldData;

/**
 * This is the main activity that holds all fragments. Starts by launching the landing page
 * {@link NewFlightFragment}. Also contains navigation draw code.
 */
public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private static final String DEFAULT_AIRPORT = "KBTV";
  private static final String DEFAULT_AIRCRAFT = "N123AB";
  private static final String SHARED_PREF_NAME = "ToldCalculator";

  private String airportIdent;
  private String aircraftName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

    airportIdent = sharedPreferences.getString(BundleConstants.AIRPORT_IDENT_KEY, null);
    aircraftName = sharedPreferences.getString(BundleConstants.AIRCRAFT_NAME_KEY, null);
    if (airportIdent == null) {
      airportIdent = DEFAULT_AIRPORT;
    }
    if (aircraftName == null) {
      aircraftName = DEFAULT_AIRCRAFT;
    }


    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().add(R.id.main_container, new NewFlightFragment()).commit();

  }

  @Override
  protected void onStop() {
    ToldData.forgetInstance(this);
    super.onStop();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    TextView currentAirport;
    TextView currentAircraft;
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);

    currentAirport = (TextView) findViewById(R.id.current_airport);
    currentAircraft = (TextView) findViewById(R.id.current_aircraft_profile);
    currentAirport.setText(airportIdent);
    currentAircraft.setText(aircraftName);
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
      FragmentManager fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.main_container,
          new SettingsFragment()).commit();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();
    FragmentManager fragmentManager = getSupportFragmentManager();

    switch (id) {
      case R.id.nav_new_flight:
        fragmentManager.beginTransaction().replace(R.id.main_container,
            new NewFlightFragment()).commit();
        break;
      case R.id.nav_weather:
        fragmentManager.beginTransaction().replace(R.id.main_container,
            new WeatherFragment()).commit();
        break;
      case R.id.nav_saved:
        fragmentManager.beginTransaction().replace(R.id.main_container,
            new SavedDataFragment()).commit();
        break;

      default:
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

}