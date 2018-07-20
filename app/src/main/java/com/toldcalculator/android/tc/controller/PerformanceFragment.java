package com.toldcalculator.android.tc.controller;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.toldcalculator.android.tc.BundleConstants;
import com.toldcalculator.android.tc.MainActivity;
import com.toldcalculator.android.tc.R;
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

  private ToldData database;

  public PerformanceFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_performance, container, false);

    Bundle bundle = this.getArguments();
    if (bundle != null) {
      savedAirportId = bundle.getLong(BundleConstants.SAVED_ID_KEY, -1l);
      if (savedAirportId != -1l) {
        loadSavedData = true;
      }
      airportIdent = bundle.getString(BundleConstants.AIRPORT_IDENT_KEY);
      aircraftWeight = bundle.getInt(BundleConstants.AIRCRAFT_WEIGHT_KEY);
      aircraftId = bundle.getString(BundleConstants.AIRCRAFT_NAME_KEY);
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
//        altitude = 3000;
//        temperature = 23;
//        aircraftWeight = 14000;
//        new GetTakeoffDataTask().execute(getActivity());
        }
      });
    }

    return view;
  }

  private void SetupUI(View view) {
    runwayRequired = (TextView) view.findViewById(R.id.runway_required_text);
    takeoffN1 = (TextView) view.findViewById(R.id.takeoff_n1_text);
    takeoffV1 = (TextView) view.findViewById(R.id.takeoff_v1_text);
    takeoffVR = (TextView) view.findViewById(R.id.takeoff_vr_text);
    takeoffV2 = (TextView) view.findViewById(R.id.takeoff_v2_text);
    aircraftInfo = (TextView) view.findViewById(R.id.aircraft_info_text);
    weatherInfo = (TextView) view.findViewById(R.id.weather_data_text);
    getNumbersButton = (Button) view.findViewById(R.id.get_numbers_button);
    runwayList = (ListView) view.findViewById(R.id.runway_listview);
    airportData = (TextView) view.findViewById(R.id.airport_data_label);

    airportData.setText(getContext().getString(R.string.airport_string, airportIdent, 0));
    aircraftInfo
        .setText(getContext().getString(R.string.aircraft_string, aircraftId, aircraftWeight));
  }



  /**
   * This AsyncTask queries the database for all high/low values based on aircraft weight, temperature,
   * and airport elevation and calculates takeoff numbers.
   */
  private class GetTakeoffDataTask extends AsyncTask<Void, Void, List<TakeoffData>> {

    private static final int HIGHALT_HIGHWEIGHT_HIGHTEMP = 0;
    private static final int HIGHALT_HIGHWEIGHT_LOWTEMP = 1;
    private static final int HIGHALT_LOWWEIGHT_HIGHTEMP = 2;
    private static final int HIGHALT_LOWWEIGHT_LOWTEMP = 3;
    private static final int LOWALT_HIGHWEIGHT_HIGHTEMP = 4;
    private static final int LOWALT_HIGHWEIGHT_LOWTEMP = 5;
    private static final int LOWALT_LOWWEIGHT_HIGHTEMP = 6;
    private static final int  LOWALT_LOWWEIGHT_LOWTEMP = 7;

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
      // Test case
      // altitude = 3500;
      // temperature = 21;
      // aircraftWeight = 14500;

      // This turned into a giant mess but it works for now.

      double altFactor;
      int highAltitude = takeoffData.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getAltitude();
      int lowAltitude = takeoffData.get(LOWALT_LOWWEIGHT_LOWTEMP).getAltitude();
      if (lowAltitude == highAltitude) {
        altFactor = 1;
      } else {
        altFactor = (altitude - lowAltitude) / (double) (highAltitude - lowAltitude);
      }

      double tempFactor;
      int highTemp = takeoffData.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getTemperature();
      int lowTemp = takeoffData.get(LOWALT_LOWWEIGHT_LOWTEMP).getTemperature();
      if (highTemp == lowTemp) {
        tempFactor = 1;
      } else {
        tempFactor = (temperature - lowTemp) / (double) (highTemp - lowTemp);
      }

      double weightFactor;
      int highWeight = takeoffData.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getWeight();
      int lowWeight = takeoffData.get(LOWALT_LOWWEIGHT_LOWTEMP).getWeight();
      if (highWeight == lowWeight) {
        weightFactor = 1;
      } else {
        weightFactor = (aircraftWeight - lowWeight) / (double) (highWeight - lowWeight);
      }

      double highV2 = takeoffData.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getTakeoffSpeedV2();
      double lowV2 = takeoffData.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedV2();

      double highVR = takeoffData.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getTakeoffSpeedVR();
      double lowVR = takeoffData.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedVR();

      double highV1;
      double lowV1;
      double highDistance;
      double lowDistance;
      //High Alt - High/Low weight - High Temp : Test case values - 123 - 117
      highV1 = (takeoffData.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getTakeoffSpeedV1()
          - takeoffData.get(HIGHALT_HIGHWEIGHT_LOWTEMP).getTakeoffSpeedV1())
          * weightFactor + takeoffData.get(HIGHALT_HIGHWEIGHT_LOWTEMP).getTakeoffSpeedV1();
      //High Alt - High/Low weight - Low Temp : Test case values - 119 - 113
      lowV1 = (takeoffData.get(HIGHALT_LOWWEIGHT_HIGHTEMP).getTakeoffSpeedV1()
          - takeoffData.get(HIGHALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedV1())
          * weightFactor + takeoffData.get(HIGHALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedV1();

      double highAltV1 = (highV1 - lowV1) * tempFactor + lowV1;

      highDistance =
          (takeoffData.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getTakeoffDistance()
              - takeoffData.get(HIGHALT_HIGHWEIGHT_LOWTEMP).getTakeoffDistance())
              * weightFactor + takeoffData.get(HIGHALT_HIGHWEIGHT_LOWTEMP).getTakeoffDistance();
      lowDistance =
          (takeoffData.get(HIGHALT_LOWWEIGHT_HIGHTEMP).getTakeoffDistance()
              - takeoffData.get(HIGHALT_LOWWEIGHT_LOWTEMP).getTakeoffDistance())
              * weightFactor + takeoffData.get(HIGHALT_LOWWEIGHT_LOWTEMP).getTakeoffDistance();

      double highAltDistance = (highDistance - lowDistance) * tempFactor + lowDistance;

      // Low Alt - High/Low weight - High temp : Test case values - 121 - 115
      highV1 = (takeoffData.get(LOWALT_HIGHWEIGHT_HIGHTEMP).getTakeoffSpeedV1()
          - takeoffData.get(LOWALT_HIGHWEIGHT_LOWTEMP).getTakeoffSpeedV1())
          * weightFactor + takeoffData.get(LOWALT_HIGHWEIGHT_LOWTEMP).getTakeoffSpeedV1();
      // Low Alt - High/Low weight - Low temp : Test case values - 118 - 112
      lowV1 = (takeoffData.get(LOWALT_LOWWEIGHT_HIGHTEMP).getTakeoffSpeedV1()
          - takeoffData.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedV1())
          * weightFactor + takeoffData.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedV1();

      double lowAltV1 = (highV1 - lowV1) * tempFactor + lowV1;

      highDistance =
          (takeoffData.get(LOWALT_HIGHWEIGHT_HIGHTEMP).getTakeoffDistance()
              - takeoffData.get(LOWALT_HIGHWEIGHT_LOWTEMP).getTakeoffDistance())
              * weightFactor + takeoffData.get(LOWALT_HIGHWEIGHT_LOWTEMP).getTakeoffDistance();
      lowDistance =
          (takeoffData.get(LOWALT_LOWWEIGHT_HIGHTEMP).getTakeoffDistance()
              - takeoffData.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffDistance())
              * weightFactor + takeoffData.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffDistance();

      double lowAltDistance = (highDistance - lowDistance) * tempFactor + lowDistance;

      double takeoffDistance = (highAltDistance - lowAltDistance) * altFactor + lowAltDistance;
      double takeoffSpeedV1 = (highAltV1 - lowAltV1) * altFactor + lowAltV1;
      double takeoffSpeedV2 = (highV2 - lowV2) * weightFactor + lowV2;
      double takeoffSpeedVR = (highVR - lowVR) * weightFactor + lowVR;

      runwayRequired.setText(String.valueOf(Math.round(takeoffDistance)));
      takeoffV1.setText(String.valueOf(Math.round(takeoffSpeedV1)));
      takeoffVR.setText(String.valueOf(Math.round(takeoffSpeedVR)));
      takeoffV2.setText(String.valueOf(Math.round(takeoffSpeedV2)));
      new SaveDataTask().execute();
    }
  }

  private class GetTakeoffPowerN1Task extends AsyncTask<Void, Void, List<TakeoffPowerN1>> {

    private static final int HIGHALT_HIGHTEMP = 0;
    private static final int HIGHALT_LOWTEMP = 1;
    private static final int LOWALT_HIGHTEMP = 2;
    private static final int LOWALT_LOWTEMP = 3;

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
      double altFactor;
      int highAltitude = takeoffPowerN1.get(HIGHALT_HIGHTEMP).getAltitude();
      int lowAltitude = takeoffPowerN1.get(LOWALT_LOWTEMP).getAltitude();
      if (highAltitude == lowAltitude) {
        altFactor = 1;
      } else {
        altFactor = (altitude - lowAltitude) / (double) (highAltitude - lowAltitude);
      }

      double tempFactor;
      int highTemp = takeoffPowerN1.get(HIGHALT_HIGHTEMP).getTemperature();
      int lowTemp = takeoffPowerN1.get(LOWALT_LOWTEMP).getTemperature();
      if (highTemp == lowTemp) {
        tempFactor = 1;
      } else {
        tempFactor = (temperature - lowTemp) / (double) (highTemp - lowTemp);
      }

      double highTempN1 =
          (takeoffPowerN1.get(HIGHALT_LOWTEMP).getTakeoffPowerN1()
              - takeoffPowerN1.get(HIGHALT_HIGHTEMP).getTakeoffPowerN1())
              * altFactor + takeoffPowerN1.get(HIGHALT_HIGHTEMP).getTakeoffPowerN1();
      double lowTempN1 =
          (takeoffPowerN1.get(LOWALT_LOWTEMP).getTakeoffPowerN1()
              - takeoffPowerN1.get(LOWALT_HIGHTEMP).getTakeoffPowerN1())
              * altFactor + takeoffPowerN1.get(LOWALT_HIGHTEMP).getTakeoffPowerN1();

      double powerN1 = (lowTempN1 - highTempN1) * tempFactor + highTempN1;

      takeoffN1.setText(String.format("%.1f", powerN1));
    }
  }

  private class MetarTask extends AsyncTask<Void, Void, MetarResponse> {

    private static final String BASE_URL = "https://www.aviationweather.gov/adds/dataserver_current/";
    private static final String FAILED_TEXT = "Failed to retrieve weather!";
    private static final double hoursBeforeNow = 1.25;

    @Override
    protected MetarResponse doInBackground(Void... voids) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .build();

      MetarService client = retrofit.create(MetarService.class);
      Response<MetarResponse> response = null;

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

  private class RunwayTask extends AsyncTask<Void, Void, AirportAndRunways> {

    @Override
    protected AirportAndRunways doInBackground(Void... voids) {
      return database.getAirportDao().selectWithRunways(airportIdent);
    }

    @Override
    protected void onPostExecute(AirportAndRunways airportAndRunways) {
      String[] runways = createRunwayList(airportAndRunways);
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
          android.R.layout.simple_list_item_1, runways);
      runwayList.setAdapter(adapter);
      Airport airport = airportAndRunways.getAirport();
      airportId = airport.getId();
      altitude = airport.getElevation();
      airportData.setText(getContext().getString(R.string.airport_string, airportIdent, altitude));
      if (!loadSavedData) {
        new GetTakeoffPowerN1Task().execute();
        new GetTakeoffDataTask().execute();
      }
    }

    private String[] createRunwayList(AirportAndRunways airportAndRunways) {
      List<Runway> runwaysList = airportAndRunways.getRunway();
      String[] runways = new String[runwaysList.size()];
      for (int i = 0; i < runwaysList.size(); i++) {
        char parallelIdentifier = ' ';
        String runway = airportAndRunways.getRunway().get(i).getRunwayId();
        int runwayIdent = Integer.parseInt(runway.substring(0, 2) + "0");
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
      return runways;
    }

  }

  private class SaveDataTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      // TODO save more fields.
      Weather weather = new Weather();
      weather.setRawText(metar.getRawText());
      weather.setAirportId(airportId);
      database.getWeatherDao().insert(weather);
      SavedTakeoffData savedTakeoffData = new SavedTakeoffData();
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
