package com.toldcalculator.android.tc.model.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.model.dao.AircraftDao;
import com.toldcalculator.android.tc.model.dao.AirportDao;
import com.toldcalculator.android.tc.model.dao.RunwayDao;
import com.toldcalculator.android.tc.model.dao.SavedTakeoffDataDao;
import com.toldcalculator.android.tc.model.dao.TakeoffDataDao;
import com.toldcalculator.android.tc.model.dao.TakeoffPowerN1Dao;
import com.toldcalculator.android.tc.model.dao.UserDao;
import com.toldcalculator.android.tc.model.dao.WeatherDao;
import com.toldcalculator.android.tc.model.db.ToldData.DateTimeConverter;
import com.toldcalculator.android.tc.model.entity.Aircraft;
import com.toldcalculator.android.tc.model.entity.Airport;
import com.toldcalculator.android.tc.model.entity.Runway;
import com.toldcalculator.android.tc.model.entity.SavedTakeoffData;
import com.toldcalculator.android.tc.model.entity.TakeoffData;
import com.toldcalculator.android.tc.model.entity.TakeoffPowerN1;
import com.toldcalculator.android.tc.model.entity.User;
import com.toldcalculator.android.tc.model.entity.Weather;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * This is the database class for Room. It contains methods to get an instance of the database and
 * a callback to pre populate the database as well.
 */
@TypeConverters({DateTimeConverter.class})
@Database(entities = {Aircraft.class, Airport.class, Runway.class, User.class, Weather.class,
    TakeoffPowerN1.class, TakeoffData.class, SavedTakeoffData.class}, version = 1, exportSchema = true)
public abstract class ToldData extends RoomDatabase {

  private static final String DATABASE_NAME = "tc_db";

  private static ToldData instance = null;

  public abstract AircraftDao getAircraftDao();
  public abstract AirportDao getAirportDao();
  public abstract RunwayDao getRunwayDao();
  public abstract UserDao getUserDao();
  public abstract TakeoffDataDao getTakeoffDataDao();
  public abstract TakeoffPowerN1Dao getTakeoffPowerN1Dao();
  public abstract WeatherDao getWeatherDao();
  public abstract SavedTakeoffDataDao getSavedTakeoffDataDao();

  /**
   * This static method is used to get a instance of the database, ToldData.
   * If the instance is null it will create one and calls the callback to pre populate the database
   * if necessary.
   * @param context takes the current context.
   * @return an instance to the database.
   */
  public static ToldData getInstance(Context context) {
    if (instance == null) {
      instance = Room
          .databaseBuilder(context.getApplicationContext(), ToldData.class, DATABASE_NAME)
          .addCallback(new Callback(context))
          .build();
    }
    return instance;
  }

  /**
   * Sets the instance field to null for garbage collection.
   * @param context takes the current context.
   */
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
      new PrepopulateTask().execute(context);
    }

  }

  private static class PrepopulateTask extends AsyncTask<Context, Void, Void> {

    private static final String AIRPORT_NAME_KEY = "NAME";
    private static final String AIRPORT_ICAO_ID_KEY = "ICAO_ID";
    private static final String AIRPORT_IDENT_KEY = "IDENT";
    private static final String AIRPORT_ELEVATION_KEY = "ELEVATION";
    private static final String AIRPORT_GLOBAL_ID_KEY = "GLOBAL_ID";
    private static final String AIRPORT_ID_KEY = "AIRPORT_ID";
    private static final String RUNWAY_WIDTH_KEY = "WIDTH";
    private static final String RUNWAY_LENGTH_KEY = "LENGTH";
    private static final String RUNWAY_DESIGNATOR_KEY = "DESIGNATOR";
    private static final String TAKEOFF_DATA_ALT_KEY = "ALT";
    private static final String TAKEOFF_DATA_WT_KEY = "WT";
    private static final String TAKEOFF_DATA_TEMP_KEY = "TEMP";
    private static final String TAKEOFF_DATA_V1_KEY = "V1";
    private static final String TAKEOFF_DATA_DIST_KEY = "DIST";
    private static final String TAKEOFF_DATA_VR_KEY = "VR";
    private static final String TAKEOFF_DATA_V2_KEY = "V2";
    public static final int HIGHEST_ALTITUDE = 10000;

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Context... contexts) {
      ToldData db = getInstance(contexts[0]);
      // TODO Remove test data / create default user.
      // Setup a default user, and aircraft.
      //Aircraft
      Aircraft aircraft = new Aircraft();
      aircraft.setAircraftType("LR35A");
      aircraft.setName("N12345");
      aircraft.setBasicEmptyWeight(10500);
      long aircraftId = db.getAircraftDao().insert(aircraft);
      //Airport
      Airport airport = new Airport();
      airport.setName("Default Airport");
      airport.setIcaoId("KDEF");
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
      user.setName("Default");
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
        airport.setName(airportRecord.get(AIRPORT_NAME_KEY));
        if (airportRecord.get(AIRPORT_ICAO_ID_KEY).equals("")) {
          airport.setIcaoId("K" + airportRecord.get(AIRPORT_IDENT_KEY));
        } else {
          airport.setIcaoId(airportRecord.get(AIRPORT_ICAO_ID_KEY));
        }
        airport.setIdent(airportRecord.get(AIRPORT_IDENT_KEY));
        airport.setElevation((int) Double.parseDouble(airportRecord.get(AIRPORT_ELEVATION_KEY)));
        long airportId = db.getAirportDao().insert(airport);

        for (CSVRecord runwayRecord : runwayRecords) {
          if (airportRecord.get(AIRPORT_GLOBAL_ID_KEY).equals(runwayRecord.get(AIRPORT_ID_KEY))) {
            runway.setWidth(Integer.parseInt(runwayRecord.get(RUNWAY_WIDTH_KEY)));
            runway.setLength(Integer.parseInt(runwayRecord.get(RUNWAY_LENGTH_KEY)));
            runway.setRunwayId(runwayRecord.get(RUNWAY_DESIGNATOR_KEY));
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
        takeoffData.setAltitude(Integer.parseInt(takeDataRecord.get(TAKEOFF_DATA_ALT_KEY)));
        takeoffData.setWeight(Integer.parseInt(takeDataRecord.get(TAKEOFF_DATA_WT_KEY)));
        takeoffData.setTemperature(Integer.parseInt(takeDataRecord.get(TAKEOFF_DATA_TEMP_KEY)));
        takeoffData.setTakeoffSpeedV1(Integer.parseInt("0" + takeDataRecord.get(TAKEOFF_DATA_V1_KEY)));
        takeoffData.setTakeoffDistance(Integer.parseInt("0" + takeDataRecord.get(TAKEOFF_DATA_DIST_KEY)));
        takeoffData.setTakeoffSpeedVR(Integer.parseInt(takeDataRecord.get(TAKEOFF_DATA_VR_KEY)));
        takeoffData.setTakeoffSpeedV2(Integer.parseInt(takeDataRecord.get(TAKEOFF_DATA_V2_KEY)));
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
        int temp = Integer.parseInt(takePowerRecord.get(TAKEOFF_DATA_TEMP_KEY));
        takeoffPowerN1.setTemperature(temp);
        for (int i  = 0; i <= HIGHEST_ALTITUDE; i += 1000) {
          takeoffPowerN1.setAltitude(i);
          takeoffPowerN1.setTakeoffPowerN1(Float.parseFloat("0" + takePowerRecord.get(String.valueOf(i))));
          db.getTakeoffPowerN1Dao().insert(takeoffPowerN1);
        }
      }
    }

  }

  /**
   * This class is used to convert Date class to a long to be placed in database
   * and converts long back to Date.
   */
  public static class DateTimeConverter {

    @TypeConverter
    public static Date getDateTime(Long time) {
      return (time != null) ? new Date(time) : null;
    }

    @TypeConverter
    public static Long setDateTime(Date date) {
      return (date != null) ? date.getTime() : null;
    }

  }

}
