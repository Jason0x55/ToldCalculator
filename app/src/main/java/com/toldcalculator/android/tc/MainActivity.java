package com.toldcalculator.android.tc;

import android.content.Context;
import android.os.AsyncTask;
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
import com.toldcalculator.android.tc.controller.NewFlightFragment;
import com.toldcalculator.android.tc.controller.WeatherFragment;
import com.toldcalculator.android.tc.model.db.ToldData;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private ToldData database;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    new AsyncTask<Context, Void, Void>() {

      @Override

      protected Void doInBackground(Context... contexts) {

        // Replace Attendance and getStudentDao with the relevant class & method names for your project.

        ToldData.getInstance(contexts[0]).getUserDao().select();

        return null;

      }

    }.execute(this);

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
  protected void onStart() {
    super.onStart();
    database = ToldData.getInstance(this);
  }

  @Override
  protected void onStop() {
    if (database != null) {
      database.forgetInstance(this);
      database = null;
    }
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
    currentAirport.setText("KABQ");
    currentAircraft.setText("N12345");
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
        break;

      default:
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

}
