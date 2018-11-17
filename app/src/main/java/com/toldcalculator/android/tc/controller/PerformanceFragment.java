package com.toldcalculator.android.tc.controller;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.controller.helpers.PerformanceCalculations;
import com.toldcalculator.android.tc.model.Metar;
import com.toldcalculator.android.tc.model.MetarResponse;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.Airport;
import com.toldcalculator.android.tc.model.entity.Runway;
import com.toldcalculator.android.tc.model.entity.SavedTakeoffData;
import com.toldcalculator.android.tc.model.entity.TakeoffData;
import com.toldcalculator.android.tc.model.entity.TakeoffPowerN1;
import com.toldcalculator.android.tc.model.entity.Weather;
import com.toldcalculator.android.tc.model.pojo.AirportAndRunways;
import com.toldcalculator.android.tc.service.MetarService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


/**
 * This {@link Fragment} subclass is where the user is presented with all relevant information.
 * The AsyncTask are used to query/insert the database and get weather information.
 */
public class PerformanceFragment extends Fragment {

  private TextView runwayRequired;
  private TextView takeoffN1;
  private TextView takeoffV1;
  private TextView takeoffVR;
  private TextView takeoffV2;
  private TextView aircraftInfo;
  private TextView weatherInfo;
  private Button getNumbersButton;
  private ListView runwayList;
  private TextView airportData;

  private String airportIdent;
  private Long airportId;
  private int temperature;
  private int altitude = -111;
  private int aircraftWeight;
  private String aircraftId;
  private Metar metar;

  private boolean loadSavedData = false;
  private long savedAirportId;
  private long userId;

  private ToldData database;

  private static final long DEFAULT_VALUE = -1L;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_performance, container, false);

    Bundle bundle = this.getArguments();
    if (bundle != null) {
      savedAirportId = bundle.getLong(getString(R.string.saved_id_key), DEFAULT_VALUE);
      if (savedAirportId != DEFAULT_VALUE) {
        loadSavedData = true;
      }
      airportIdent = bundle.getString(getString(R.string.airport_ident_key));
      aircraftWeight = bundle.getInt(getString(R.string.aircraft_weight_key));
      aircraftId = bundle.getString(getString(R.string.aircraft_name_key));
      userId = bundle.getLong(getString(R.string.user_id_key));
    }
    database = ToldData.getInstance(getActivity());

    SetupUI(view);

    if (loadSavedData) {
      new GetSavedDataTask().execute();
      getNumbersButton.setVisibility(View.INVISIBLE);
    } else {
      getNumbersButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          new MetarTask().execute();
        }
      });
    }

    return view;
  }

  private void SetupUI(View view) {
    runwayRequired = view.findViewById(R.id.runway_required_text);
    takeoffN1 = view.findViewById(R.id.takeoff_n1_text);
    takeoffV1 = view.findViewById(R.id.takeoff_v1_text);
    takeoffVR = view.findViewById(R.id.takeoff_vr_text);
    takeoffV2 = view.findViewById(R.id.takeoff_v2_text);
    aircraftInfo = view.findViewById(R.id.aircraft_info_text);
    weatherInfo = view.findViewById(R.id.weather_data_text);
    getNumbersButton = view.findViewById(R.id.get_numbers_button);
    runwayList = view.findViewById(R.id.runway_listview);
    airportData = view.findViewById(R.id.airport_data_label);

    airportData.setText(getContext().getString(R.string.airport_string, airportIdent, 0));
    aircraftInfo
        .setText(getContext().getString(R.string.aircraft_string, aircraftId, aircraftWeight));
  }


  /*
   * This AsyncTask queries the database for all high/low values based on aircraft weight, temperature,
   * and airport elevation and calls {@link PerformanceCalculations} to calculates takeoff numbers.
   */
  private class GetTakeoffDataTask extends AsyncTask<Void, Void, List<TakeoffData>> {

    @Override
    protected List<TakeoffData> doInBackground(Void... voids) {
      List<TakeoffData> takeoffData = new ArrayList<>();

      // Eight total queries needed to get all possible values depending on aircraft weight,
      // temperature, and airport elevation.

      // High Alt - High/Low weight - High temp
      takeoffData.add(database.getTakeoffDataDao()
          .selectHighAll(altitude, aircraftWeight, temperature));
      takeoffData.add(database.getTakeoffDataDao()
          .selectHighAltLowWTHighTemp(altitude, aircraftWeight, temperature));

      // High Alt - High/Low weight - Low temp
      takeoffData.add(database.getTakeoffDataDao()
          .selectHighAltHighWTLowTemp(altitude, aircraftWeight, temperature));
      takeoffData.add(database.getTakeoffDataDao()
          .selectHighAltLowWtLowTemp(altitude, aircraftWeight, temperature));

      // Low Alt - High/Low weight - High temp
      takeoffData.add(database.getTakeoffDataDao()
          .selectLowAltHighWTHighTemp(altitude, aircraftWeight, temperature));
      takeoffData.add(database.getTakeoffDataDao()
          .selectLowAltLowWTHighTemp(altitude, aircraftWeight, temperature));

      // Low Alt - High/Low weight - Low temp
      takeoffData.add(database.getTakeoffDataDao()
          .selectLowAltHighWTLowTemp(altitude, aircraftWeight, temperature));
      takeoffData.add(database.getTakeoffDataDao()
          .selectLowAll(altitude, aircraftWeight, temperature));
      return takeoffData;
    }

    @Override
    protected void onPostExecute(List<TakeoffData> takeoffData) {

      PerformanceCalculations performanceCalculations =
          new PerformanceCalculations(takeoffData,
              null, altitude, aircraftWeight, temperature);

      runwayRequired.setText(performanceCalculations.getTakeoffDistance());
      takeoffV1.setText(performanceCalculations.getTakeoffSpeedV1());
      takeoffVR.setText(performanceCalculations.getTakeoffSpeedVR());
      takeoffV2.setText(performanceCalculations.getTakeoffSpeedV2());
      new SaveDataTask().execute();
    }
  }

  /*
   * This AsyncTask queries the database for all high/low values based on temperature and
   * airport elevation then calls {@link PerformanceCalculations} to calculates takeoff power N1
   * numbers.
   */
  private class GetTakeoffPowerN1Task extends AsyncTask<Void, Void, List<TakeoffPowerN1>> {

    @Override
    protected List<TakeoffPowerN1> doInBackground(Void... voids) {
      List<TakeoffPowerN1> takeoffPowerN1 = new ArrayList<>();
      takeoffPowerN1
          .add(database.getTakeoffPowerN1Dao().selectHighAltHighTemp(altitude, temperature));
      takeoffPowerN1
          .add(database.getTakeoffPowerN1Dao().selectLowAltHighTemp(altitude, temperature));
      takeoffPowerN1
          .add(database.getTakeoffPowerN1Dao().selectHighAltLowTemp(altitude, temperature));
      takeoffPowerN1
          .add(database.getTakeoffPowerN1Dao().selectLowAltLowTemp(altitude, temperature));
      return takeoffPowerN1;
    }

    @Override
    protected void onPostExecute(List<TakeoffPowerN1> takeoffPowerN1) {
      PerformanceCalculations performanceCalculations =
          new PerformanceCalculations(null, takeoffPowerN1,
              altitude, aircraftWeight, temperature);
      takeoffN1.setText(String.format(Locale.US,"%.1f", performanceCalculations.getTakeoffPowerN1()));
    }
  }

  /*
   * This AsyncTask sends a request to the FAA Text Data Service to get weather information.
   */
  private class MetarTask extends AsyncTask<Void, Void, MetarResponse> {

    private static final String BASE_URL = "https://www.aviationweather.gov/adds/dataserver_current/";
    private static final String FAILED_TEXT = "Failed to retrieve weather!";
    private static final double hoursBeforeNow = 1.25;

    private Response<MetarResponse> response;

    @Override
    protected MetarResponse doInBackground(Void... voids) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .build();

      MetarService client = retrofit.create(MetarService.class);


      {
        try {
          response = client.response(airportIdent, hoursBeforeNow).execute();
        } catch (IOException e) {
          // Do nothing for now.
        }
      }
      return response.body();
    }

    @Override
    protected void onPostExecute(MetarResponse metarResponse) {
      if (metarResponse.getData().size() > 0) {
        metar = metarResponse.getData().get(0);
        weatherInfo.setText(metar.getRawText());
        temperature = (int) Math.round(Double.parseDouble(metar.getTempC()));
        new RunwayTask().execute();
      } else {
        Toast.makeText(getActivity(), FAILED_TEXT, Toast.LENGTH_LONG).show();
      }
    }
  }

  /*
   * This AsyncTask queries the database to get airport and runway information.
   */
  private class RunwayTask extends AsyncTask<Void, Void, AirportAndRunways> {

    private static final String TEMPERATURE_NOT_USABLE = "Temperature off the charts: Refer to AFM";
    private static final String AIRPORT_QUERY_FAILED = "Failed to locate airport record.";
    private static final int MAX_TEMP = 38;
    private static final int MIN_TEMP = -18;

    @Override
    protected AirportAndRunways doInBackground(Void... voids) {
      return database.getAirportDao().selectWithRunways(airportIdent);
    }

    @Override
    protected void onPostExecute(AirportAndRunways airportAndRunways) {
      if (airportAndRunways != null) {
        createRunwayList(airportAndRunways);
        Airport airport = airportAndRunways.getAirport();
        airportId = airport.getId();
        altitude = airport.getElevation();
        if (altitude < 0) {
          altitude = 0;
        }
        airportData.setText(getContext().getString(R.string.airport_string, airportIdent, altitude));
        if (!loadSavedData) {
          if (temperature > MAX_TEMP || temperature < MIN_TEMP) {
            Toast toast = Toast.makeText(getActivity(), TEMPERATURE_NOT_USABLE, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
          } else {
            new GetTakeoffPowerN1Task().execute();
            new GetTakeoffDataTask().execute();
          }
        }
      } else {
        Toast.makeText(getActivity(), AIRPORT_QUERY_FAILED, Toast.LENGTH_LONG).show();
      }
    }

    private void createRunwayList(AirportAndRunways airportAndRunways) {
      List<Runway> runwaysList = airportAndRunways.getRunway();
      String[] runways = new String[runwaysList.size()];
      for (int i = 0; i < runwaysList.size(); i++) {
        char parallelIdentifier = ' ';
        String runway = airportAndRunways.getRunway().get(i).getRunwayId();
        // No runway identifier. Maybe handle this differently.
        if (runway.length() < 1 || !Character.isDigit(runway.charAt(0))) {
          runways[i] = runway;
          continue;
        }
        if (runway.length() < 2) {
          runways[i] = runway;
          continue;
        }
        int runwayIdent = Integer.parseInt(runway.substring(0, 2) + "0");
        // TODO Constants...
        runwayIdent =
            ((runwayIdent + 180) > 360) ? (runwayIdent + 180) % 360 / 10 : (runwayIdent + 180) / 10;
        if (runway.length() > 2) {
          // Add the left right identifier for parallel runways
          switch (runway.charAt(2)) {
            case 'R':
              parallelIdentifier = 'L';
              break;
            case 'L':
              parallelIdentifier = 'R';
              break;
            default:
              parallelIdentifier = runway.charAt(2);
          }
        }
        runways[i] = "Runway: " + runway + "/" + runwayIdent + parallelIdentifier
            + " - Length: " + airportAndRunways.getRunway().get(i).getLength()
            + " - Width: " + airportAndRunways.getRunway().get(i).getWidth();
      }
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
          android.R.layout.simple_list_item_1, runways);
      runwayList.setAdapter(adapter);
    }

  }

  /*
   * This AsyncTask saves the weather data and takeoff information to the database.
   */
  private class SaveDataTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      // TODO save more weather fields.
      Weather weather = new Weather();
      weather.setRawText(metar.getRawText());
      weather.setAirportId(airportId);
      database.getWeatherDao().insert(weather);
      SavedTakeoffData savedTakeoffData = new SavedTakeoffData();
      savedTakeoffData.setUserId(userId);
      savedTakeoffData.setAircraftName(aircraftId);
      savedTakeoffData.setAirportId(airportIdent);
      savedTakeoffData.setRunwayRequired(Integer.parseInt(runwayRequired.getText().toString()));
      savedTakeoffData.setTakeoffV1(Integer.parseInt(takeoffV1.getText().toString()));
      savedTakeoffData.setTakeoffVR(Integer.parseInt(takeoffVR.getText().toString()));
      savedTakeoffData.setTakeoffV2(Integer.parseInt(takeoffV2.getText().toString()));
      savedTakeoffData.setTakeoffN1(Float.parseFloat(takeoffN1.getText().toString()));
      savedTakeoffData.setTakeoffWeight(aircraftWeight);
      savedTakeoffData.setWeatherRawText(metar.getRawText());
      database.getSavedTakeoffDataDao().insert(savedTakeoffData);
      return null;
    }
  }

  /*
   * This AsyncTask is used to query the database for saved TOLD and display it to the user.
   */
  private class GetSavedDataTask extends AsyncTask<Void, Void, SavedTakeoffData> {

    @Override
    protected SavedTakeoffData doInBackground(Void... voids) {
      return ToldData.getInstance(getContext()).getSavedTakeoffDataDao().select(savedAirportId);
    }

    @Override
    protected void onPostExecute(SavedTakeoffData savedTakeoffData) {
      if (savedTakeoffData != null) {
        runwayRequired.setText(String.valueOf(savedTakeoffData.getRunwayRequired()));
        takeoffN1.setText(String.valueOf(savedTakeoffData.getTakeoffN1()));
        takeoffV1.setText(String.valueOf(savedTakeoffData.getTakeoffV1()));
        takeoffVR.setText(String.valueOf(savedTakeoffData.getTakeoffVR()));
        takeoffV2.setText(String.valueOf(savedTakeoffData.getTakeoffV2()));
        weatherInfo.setText(savedTakeoffData.getWeatherRawText());

        new RunwayTask().execute();
      }

    }
  }

}
