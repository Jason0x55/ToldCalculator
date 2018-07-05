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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.toldcalculator.android.tc.controller.NewFlightFragment;
import com.toldcalculator.android.tc.controller.WeatherFragment;
import com.toldcalculator.android.tc.model.MetarResponse;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.User;
import com.toldcalculator.android.tc.model.pojo.AirportAndRunways;
import com.toldcalculator.android.tc.service.MetarService;
import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private ToldData database;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    new AsyncTask<Context, Void, Void>() {

      @Override

      protected Void doInBackground(Context... contexts) {

        // Replace Attendance and getStudentDao with the relevant class & method names for your project.

        ToldData.getInstance(contexts[0]).getUserDao().select();

        return null;

      }

    }.execute(this);

    new MetarTask().execute();
//    new SetupTask().execute();
//    new AirportTask().execute();

    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().add(R.id.main_container, new NewFlightFragment()).commit();

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
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
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
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

    if (id == R.id.nav_new_flight) {
      fragmentManager.beginTransaction().replace(R.id.main_container, new NewFlightFragment())
          .commit();
    } else if (id == R.id.nav_weather) {
      fragmentManager.beginTransaction().replace(R.id.main_container, new WeatherFragment())
          .commit();
    } else if (id == R.id.nav_saved) {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  private class SetupTask extends AsyncTask<Void, Void, User> {

    @Override
    protected User doInBackground(Void... voids) {
      return ToldData.getInstance(MainActivity.this).getUserDao().select();
    }

    @Override
    protected void onPostExecute(User user) {
      Log.d("Database: ", user.getName());
    }
  }

  private class AirportTask extends AsyncTask<Void, Void, AirportAndRunways> {

    @Override
    protected AirportAndRunways doInBackground(Void... voids) {
      return ToldData.getInstance(MainActivity.this).getAirportDao().selectWithRunways(1);
    }

    @Override
    protected void onPostExecute(AirportAndRunways airportAndRunways) {
      Log.d("Database: ", airportAndRunways.getRunway().get(0).getRunwayId());
    }
  }

  private class MetarTask extends AsyncTask<Void, Void, MetarResponse> {

    @Override
    protected MetarResponse doInBackground(Void... voids) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://www.aviationweather.gov/adds/dataserver_current/")
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .build();

      MetarService client = retrofit.create(MetarService.class);
      Response<MetarResponse> response = null;

      {
        try {
          response = client.response("KABQ", 2).execute();
          System.out.println(response.body().getData().get(0).getStationId());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return response.body();
    }

    @Override
    protected void onPostExecute(MetarResponse metarResponse) {
      metarResponse.getData().get(0).getStationId();
    }
  }
}
