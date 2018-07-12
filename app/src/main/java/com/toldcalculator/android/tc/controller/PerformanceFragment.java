package com.toldcalculator.android.tc.controller;


import android.content.Context;
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
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.model.MetarResponse;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.Runway;
import com.toldcalculator.android.tc.model.entity.TakeoffData;
import com.toldcalculator.android.tc.model.entity.TakeoffPowerN1;
import com.toldcalculator.android.tc.model.pojo.AirportAndRunways;
import com.toldcalculator.android.tc.service.MetarService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerformanceFragment extends Fragment {

  private TextView runwayRequired;
  private TextView takeoffN1;
  private TextView takeoffV1;
  private TextView takeoffVR;
  private TextView takeoffV2;
  private TextView aircraftInfo;
  private TextView weatherInfo;
  private Button saveButton;
  private ListView runwayList;

  private String airportIdent;
  private int temperature;
  private int altitude = -111;
  private int aircraftWeight;

  private ToldData database;

  private static final double hoursBeforeNow = 1.25;

  public PerformanceFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_performance, container, false);

    Bundle bundle = this.getArguments();
    if(bundle != null){
      airportIdent = bundle.getString("ICAO");
      aircraftWeight = bundle.getInt("WT");
      // TODO move and add more aircraft info
    }
    database = ToldData.getInstance(getActivity());

    SetupUI(view);

    // TODO Implement save function and move MetarTask.execute()
    saveButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
//        new MetarTask().execute();
        altitude = 3500;
        temperature = 21;
        aircraftWeight = 14500;
        new GetTakeoffDataTask().execute(getActivity());
      }
    });

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
    saveButton = (Button) view.findViewById(R.id.save_button);
    runwayList = (ListView) view.findViewById(R.id.runway_listview);

    aircraftInfo.setText(String.valueOf(aircraftWeight));
  }

  private class GetTakeoffDataTask extends AsyncTask<Context, Void, List<TakeoffData>> {

    @Override
    protected List<TakeoffData> doInBackground(Context... contexts) {
      List<TakeoffData> takeoffData = new ArrayList<>();

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

      int highAltitude = takeoffData.get(0).getAltitude();
      int lowAltitude = takeoffData.get(7).getAltitude();
      double altFactor = (highAltitude - altitude) / (double) (highAltitude - lowAltitude);

      int highTemp = takeoffData.get(0).getTemperature();
      int lowTemp = takeoffData.get(7).getTemperature();
      double tempFactor = (highTemp - temperature) / (double) (highTemp - lowTemp);

      int highWeight = takeoffData.get(0).getWeight();
      int lowWeight = takeoffData.get(7).getWeight();
      double weightFactor = (highWeight - aircraftWeight) / (double) (highWeight - lowWeight);

      double highV2 = takeoffData.get(0).getTakeoffSpeedV2();
      double lowV2 = takeoffData.get(7).getTakeoffSpeedV2();

      double highVR = takeoffData.get(0).getTakeoffSpeedVR();
      double lowVR = takeoffData.get(7).getTakeoffSpeedVR();

      double highV1;
      double lowV1;
      double highDistance;
      double lowDistance;
      //High Alt - High/Low weight -High Temp
      // 123 - 117
      highV1 = (takeoffData.get(0).getTakeoffSpeedV1() - takeoffData.get(1).getTakeoffSpeedV1())
          * weightFactor + takeoffData.get(1).getTakeoffSpeedV1();
      //High Alt - High/Low weight - Low Temp
      // 119 - 113
      lowV1 = (takeoffData.get(2).getTakeoffSpeedV1() - takeoffData.get(3).getTakeoffSpeedV1())
          * weightFactor + takeoffData.get(3).getTakeoffSpeedV1();

      double highAltV1 = (highV1 - lowV1) * tempFactor + lowV1;

      highDistance = (takeoffData.get(0).getTakeoffDistance() - takeoffData.get(1).getTakeoffDistance())
          * weightFactor + takeoffData.get(1).getTakeoffDistance();
      lowDistance = (takeoffData.get(2).getTakeoffDistance() - takeoffData.get(3).getTakeoffDistance())
          * weightFactor + takeoffData.get(3).getTakeoffDistance();

      double highAltDistance = (highDistance - lowDistance) * tempFactor + lowDistance;

      // Low Alt - High/Low weight - High temp
      // 121 - 115
      highV1 = (takeoffData.get(4).getTakeoffSpeedV1() - takeoffData.get(5).getTakeoffSpeedV1())
          * weightFactor + takeoffData.get(5).getTakeoffSpeedV1();
      // Low Alt - High/Low weight - Low temp
      // 118 - 112
      lowV1 = (takeoffData.get(6).getTakeoffSpeedV1() - takeoffData.get(7).getTakeoffSpeedV1())
          * weightFactor + takeoffData.get(7).getTakeoffSpeedV1();

      double lowAltV1 = (highV1 - lowV1) * tempFactor + lowV1;

      highDistance = (takeoffData.get(4).getTakeoffDistance() - takeoffData.get(5).getTakeoffDistance())
          * weightFactor + takeoffData.get(5).getTakeoffDistance();
      lowDistance = (takeoffData.get(6).getTakeoffDistance() - takeoffData.get(7).getTakeoffDistance())
          * weightFactor + takeoffData.get(7).getTakeoffDistance();

      double lowAltDistance = (highDistance - lowDistance) * tempFactor + lowDistance;

      double takeoffDistance = (highAltDistance - lowAltDistance) * altFactor + lowAltDistance;
      double takeoffSpeedV1 = (highAltV1 - lowAltV1) * altFactor + lowAltV1;
      double takeoffSpeedV2 = (highV2 - lowV2) * weightFactor + lowV2;
      double takeoffSpeedVR = (highVR - lowVR) * weightFactor + lowVR;

      runwayRequired.setText(String.valueOf(Math.round(takeoffDistance)));
      takeoffV1.setText(String.valueOf(Math.round(takeoffSpeedV1)));
      takeoffVR.setText(String.valueOf(Math.round(takeoffSpeedVR)));
      takeoffV2.setText(String.valueOf(Math.round(takeoffSpeedV2)));
      new GetTakeoffPowerN1Task().execute();
    }
  }

  private class GetTakeoffPowerN1Task extends AsyncTask<Void, Void, TakeoffPowerN1> {

    @Override
    protected TakeoffPowerN1 doInBackground(Void... voids) {
      return database.getTakeoffPowerN1Dao().select(altitude, temperature);
    }

    @Override
    protected void onPostExecute(TakeoffPowerN1 takeoffPowerN1) {
      takeoffN1.setText(String.valueOf(takeoffPowerN1.getTakeoffPowerN1()));
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
          response = client.response(airportIdent, hoursBeforeNow).execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return response.body();
    }

    @Override
    protected void onPostExecute(MetarResponse metarResponse) {
      weatherInfo.setText(metarResponse.getData().get(0).getRawText());
      temperature = (int) Math.round(Double.parseDouble(metarResponse.getData().get(0).getTempC()));
      new RunwayTask().execute();
    }
  }

  private class RunwayTask extends AsyncTask<Void, Void, AirportAndRunways> {

    @Override
    protected AirportAndRunways doInBackground(Void... voids) {
      return database.getAirportDao().selectWithRunways(airportIdent);
    }

    @Override
    protected void onPostExecute(AirportAndRunways airportAndRunways) {
      // TODO Maybe change to RecyclerView
      List<Runway> runwaysList = airportAndRunways.getRunway();
      String[] runways = new String[runwaysList.size()];
      for (int i = 0; i < runwaysList.size(); i++) {
        // This is a mess
        int runwayIdent = Integer.parseInt(airportAndRunways.getRunway().get(i).getRunwayId().substring(0,2) + "0");
        runwayIdent = ((runwayIdent + 180) > 360) ? (runwayIdent + 180) % 360 / 10 : (runwayIdent + 180) / 10;
        runways[i] = airportAndRunways.getRunway().get(i).getRunwayId() + "/" + runwayIdent + " "
            + airportAndRunways.getRunway().get(i).getLength() + " "
            + airportAndRunways.getRunway().get(i).getWidth();
      }
      ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, runways);
      runwayList.setAdapter(adapter);

      altitude = airportAndRunways.getAirport().getElevation();
      new GetTakeoffDataTask().execute(getActivity());
    }

  }

}
