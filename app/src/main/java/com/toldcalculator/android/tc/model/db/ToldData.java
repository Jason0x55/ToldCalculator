package com.toldcalculator.android.tc.model.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.model.dao.AircraftDao;
import com.toldcalculator.android.tc.model.dao.AirportDao;
import com.toldcalculator.android.tc.model.dao.RunwayDao;
import com.toldcalculator.android.tc.model.dao.TakeoffDataDao;
import com.toldcalculator.android.tc.model.dao.TakeoffPowerN1Dao;
import com.toldcalculator.android.tc.model.dao.UserDao;
import com.toldcalculator.android.tc.model.entity.Aircraft;
import com.toldcalculator.android.tc.model.entity.Airport;
import com.toldcalculator.android.tc.model.entity.Runway;
import com.toldcalculator.android.tc.model.entity.TakeoffData;
import com.toldcalculator.android.tc.model.entity.TakeoffPowerN1;
import com.toldcalculator.android.tc.model.entity.User;
import com.toldcalculator.android.tc.model.entity.Weather;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Database(entities = {Aircraft.class, Airport.class, Runway.class, User.class, Weather.class,
    TakeoffPowerN1.class, TakeoffData.class}, version = 1, exportSchema = true)
public abstract class ToldData extends RoomDatabase {

  private static final String DATABASE_NAME = "tc_db";

  private static ToldData instance = null;

  public abstract AircraftDao getAircraftDao();
  public abstract AirportDao getAirportDao();
  public abstract RunwayDao getRunwayDao();
  public abstract UserDao getUserDao();
  public abstract TakeoffDataDao getTakeoffDataDao();
  public abstract TakeoffPowerN1Dao getTakeoffPowerN1Dao();

  public static ToldData getInstance(Context context) {
    if (instance == null) {
      instance = Room
          .databaseBuilder(context.getApplicationContext(), ToldData.class, DATABASE_NAME)
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
      aircraft.setBasicEmptyWeight(10500);
      long aircraftId = db.getAircraftDao().insert(aircraft);
      //Airport
      Airport airport = new Airport();
      airport.setName("Jason Airpark");
      airport.setIcaoId("KRAF");
      airport.setElevation(4444);
      long airportId = db.getAirportDao().insert(airport);
      //Runway
      Runway runway = new Runway();
      runway.setAirportId(airportId);
      runway.setRunwayId("05");
      runway.setLength(10000);
      runway.setWidth(150);
      db.getRunwayDao().insert(runway);
      runway.setRunwayId("08");
      runway.setLength(12000);
      runway.setWidth(150);
      db.getRunwayDao().insert(runway);
      runway.setRunwayId("10");
      runway.setLength(6000);
      runway.setWidth(75);
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
      takeoffPowerN1.setTemperature(28);
      takeoffPowerN1.setTakeoffPowerN1(95.9f);
      takeoffPowerN1.setAircraftId(aircraftId);
      db.getTakeoffPowerN1Dao().insert(takeoffPowerN1);

      loadAirports(db, contexts[0]);
      loadTakeoffData(db, contexts[0], aircraftId);
      loadTakeoffDataN1(db, contexts[0], aircraftId);

      forgetInstance(contexts[0]);
      return null;
    }

    private void loadAirports(ToldData db, Context context) {
      Airport airport = new Airport();
      Runway runway = new Runway();
      Reader airportReader = new InputStreamReader(
          context.getResources().openRawResource(R.raw.airports));
      Reader runwayReader = new InputStreamReader(
          context.getResources().openRawResource(R.raw.runways));
      Iterable<CSVRecord> airportRecords = null;
      List<CSVRecord> runwayRecords = null;
      CSVParser runwayParser = null;

      try {
        airportRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(airportReader);
        runwayParser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(runwayReader);
        runwayRecords = runwayParser.getRecords();
      } catch (IOException e) {
        // Do nothing for now.
      }

      for (CSVRecord airportRecord : airportRecords) {
        airport.setName(airportRecord.get("NAME"));
        if (airportRecord.get("ICAO_ID").equals("")) {
          airport.setIcaoId(airportRecord.get("IDENT"));
        } else {
          airport.setIcaoId(airportRecord.get("ICAO_ID"));
        }

        airport.setElevation((int) Double.parseDouble(airportRecord.get("ELEVATION")));
        long airportId = db.getAirportDao().insert(airport);

        for (CSVRecord runwayRecord : runwayRecords) {
          if (airportRecord.get("GLOBAL_ID").equals(runwayRecord.get("AIRPORT_ID"))) {
            runway.setWidth(Integer.parseInt(runwayRecord.get("WIDTH")));
            runway.setLength(Integer.parseInt(runwayRecord.get("LENGTH")));
            runway.setRunwayId(runwayRecord.get("DESIGNATOR"));
            runway.setAirportId(airportId);
            db.getRunwayDao().insert(runway);
          }
        }
      }

    }

    private void loadTakeoffData(ToldData db, Context context, long aircraftId) {
      TakeoffData takeoffData = new TakeoffData();
      Reader takeoffDataReader = new InputStreamReader(
          context.getResources().openRawResource(R.raw.takeoffdata));
      Iterable<CSVRecord> takeoffDataRecords = null;
      try {
        takeoffDataRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(takeoffDataReader);
      } catch (IOException e) {
        // Do nothing for now.
      }

      for (CSVRecord takeDataRecord : takeoffDataRecords) {
        takeoffData.setAircraftId(aircraftId);
        takeoffData.setAltitude(Integer.parseInt(takeDataRecord.get("ALT")));
        takeoffData.setWeight(Integer.parseInt(takeDataRecord.get("WT")));
        takeoffData.setTemperature(Integer.parseInt(takeDataRecord.get("TEMP")));
        takeoffData.setTakeoffSpeedV1(Integer.parseInt("0" + takeDataRecord.get("V1")));
        takeoffData.setTakeoffDistance(Integer.parseInt("0" + takeDataRecord.get("DIST")));
        takeoffData.setTakeoffSpeedVR(Integer.parseInt(takeDataRecord.get("VR")));
        takeoffData.setTakeoffSpeedV2(Integer.parseInt(takeDataRecord.get("V2")));
        db.getTakeoffDataDao().insert(takeoffData);
      }

    }

    private void loadTakeoffDataN1(ToldData db, Context context, long aircraftId) {
      TakeoffPowerN1 takeoffPowerN1 = new TakeoffPowerN1();
      Reader takeoffPowerReader = new InputStreamReader(
          context.getResources().openRawResource(R.raw.takeoffpowern1));
      Iterable<CSVRecord> takeoffPowerRecords = null;

      try {
        takeoffPowerRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(takeoffPowerReader);
      } catch (IOException e) {
        // Do nothing for now.
      }

      for (CSVRecord takePowerRecord : takeoffPowerRecords) {
        takeoffPowerN1.setAircraftId(aircraftId);
        int temp = Integer.parseInt(takePowerRecord.get("TEMP"));
        takeoffPowerN1.setTemperature(temp);
        for (int i  = 0; i <= 10000; i += 1000) {
          takeoffPowerN1.setAltitude(i);
          takeoffPowerN1.setTakeoffPowerN1(Float.parseFloat("0" + takePowerRecord.get(String.valueOf(i))));
          db.getTakeoffPowerN1Dao().insert(takeoffPowerN1);
        }
      }
    }

  }

}
