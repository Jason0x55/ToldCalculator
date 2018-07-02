package com.toldcalculator.android.tc.model.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.toldcalculator.android.tc.model.dao.AircraftDao;
import com.toldcalculator.android.tc.model.dao.AirportDao;
import com.toldcalculator.android.tc.model.dao.RunwayDao;
import com.toldcalculator.android.tc.model.dao.TakeoffPowerN1Dao;
import com.toldcalculator.android.tc.model.dao.UserDao;
import com.toldcalculator.android.tc.model.entity.Aircraft;
import com.toldcalculator.android.tc.model.entity.Airport;
import com.toldcalculator.android.tc.model.entity.Runway;
import com.toldcalculator.android.tc.model.entity.TakeoffPowerN1;
import com.toldcalculator.android.tc.model.entity.TakeoffData;
import com.toldcalculator.android.tc.model.entity.User;
import com.toldcalculator.android.tc.model.entity.Weather;

@Database(entities = {Aircraft.class, Airport.class, Runway.class, User.class, Weather.class, TakeoffPowerN1.class, TakeoffData.class }, version = 1, exportSchema = true)
public abstract class ToldData extends RoomDatabase {

  private static final String DATABASE_NAME = "tc_db";

  private static ToldData instance = null;

  public abstract AircraftDao getAircraftDao();
  public abstract AirportDao getAirportDao();
  public abstract RunwayDao getRunwayDao();
  public abstract UserDao getUserDao();
  public abstract TakeoffPowerN1Dao getTakeoffPowerN1Dao();

  public static ToldData getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(), ToldData.class, DATABASE_NAME)
          .addCallback(new Callback(context))
          .build();
    }
    return instance;
  }

  public static void forgetInstance(Context context) {
    instance = null;
  }

  private static class Callback extends RoomDatabase.Callback {

    private Context context;

    private Callback(Context context) {
      this.context = context;
    }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
    }

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      new PrepopulateTask().execute(context); // Call a task to pre-populate database.
    }

  }

  private static class PrepopulateTask extends AsyncTask<Context, Void, Void> {

    @Override
    protected Void doInBackground(Context... contexts) {
      ToldData db = getInstance(contexts[0]);
      //Aircraft
      Aircraft aircraft = new Aircraft();
      aircraft.setAircraftType("LR35A");
      aircraft.setName("N12345");
      aircraft.setBasicEmptyWeight(13500);
      long aircraftId = db.getAircraftDao().insert(aircraft);
      aircraft.setAircraftType("LR35A");
      aircraft.setName("N54321");
      aircraft.setBasicEmptyWeight(13300);
      db.getAircraftDao().insert(aircraft);
      //Airport
      Airport airport = new Airport();
      airport.setName("Sunport");
      airport.setIcaoId("KABQ");
      airport.setElevation(5355);
      long airportId = db.getAirportDao().insert(airport);
      //Runway
      Runway runway = new Runway();
      runway.setAirportId(airportId);
      runway.setRunwayId("3/21");
      runway.setLength(10000);
      runway.setWidth(150);
      db.getRunwayDao().insert(runway);
      runway.setRunwayId("8/26");
      runway.setLength(12000);
      runway.setWidth(150);
      db.getRunwayDao().insert(runway);
      //User
      User user = new User();
      user.setName("Jason");
      user.setAircraftId(aircraftId);
      user.setAirportId(airportId);
      db.getUserDao().insert(user);
      //N1
      TakeoffPowerN1 takeoffPowerN1 = new TakeoffPowerN1();
      takeoffPowerN1.setAltitude(5000);
      takeoffPowerN1.setTemperature(27);
      takeoffPowerN1.setTakeoffPowerN1(96.0f);
      takeoffPowerN1.setAircraftId(aircraftId);
      db.getTakeoffPowerN1Dao().insert(takeoffPowerN1);

      forgetInstance(contexts[0]);
      return null;
    }

  }

}
