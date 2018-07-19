package com.toldcalculator.android.tc.controller;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.Airport;
import com.toldcalculator.android.tc.model.entity.Runway;
import com.toldcalculator.android.tc.model.entity.SavedTakeoffData;
import com.toldcalculator.android.tc.model.pojo.AirportAndRunways;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadSavedDataFragment extends Fragment {

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

  private long airportId;

  private SavedTakeoffData takeoffData;

  public LoadSavedDataFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_performance, container, false);
    Bundle bundle = this.getArguments();
    if(bundle != null) {
      airportId = bundle.getLong("SAVEDID");
    }
    SetupUI(view);
    new GetSavedDataTask().execute();
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
    getNumbersButton.setVisibility(View.INVISIBLE);
    runwayList = (ListView) view.findViewById(R.id.runway_listview);
    airportData = (TextView) view.findViewById(R.id.airport_data_label);
  }

  private class GetSavedDataTask extends AsyncTask<Void, Void, SavedTakeoffData> {

    @Override
    protected SavedTakeoffData doInBackground(Void... voids) {
      return ToldData.getInstance(getContext()).getSavedTakeoffDataDao().select(airportId);
    }

    @Override
    protected void onPostExecute(SavedTakeoffData savedTakeoffData) {
      if (savedTakeoffData != null) {
        takeoffData = savedTakeoffData;
        runwayRequired.setText(String.valueOf(savedTakeoffData.getRunwayRequired()));
        takeoffN1.setText(String.valueOf(savedTakeoffData.getTakeoffN1()));
        takeoffV1.setText(String.valueOf(savedTakeoffData.getTakeoffV1()));
        takeoffVR.setText(String.valueOf(savedTakeoffData.getTakeoffVR()));
        takeoffV2.setText(String.valueOf(savedTakeoffData.getTakeoffV2()));

        aircraftInfo.setText(savedTakeoffData.getAircraftName() + " " + String.valueOf(savedTakeoffData.getTakeoffWeight()));
        weatherInfo.setText(savedTakeoffData.getWeatherRawText());

        new RunwayTask().execute();
      }

    }
  }

  private class RunwayTask extends AsyncTask<Void, Void, AirportAndRunways> {

    @Override
    protected AirportAndRunways doInBackground(Void... voids) {
      return ToldData.getInstance(getContext()).getAirportDao().selectWithRunways(takeoffData.getAirportId());
    }

    @Override
    protected void onPostExecute(AirportAndRunways airportAndRunways) {
      String[] runways = createRunwayList(airportAndRunways);
      ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, runways);
      runwayList.setAdapter(adapter);
      Airport airport = airportAndRunways.getAirport();
      airportData.setText(takeoffData.getAirportId() + " - Elevation: " + airport.getElevation());
    }

    private String[] createRunwayList(AirportAndRunways airportAndRunways) {
      List<Runway> runwaysList = airportAndRunways.getRunway();
      String[] runways = new String[runwaysList.size()];
      for (int i = 0; i < runwaysList.size(); i++) {
        char parallelIdentifier = ' ';
        String runway = airportAndRunways.getRunway().get(i).getRunwayId();
        int runwayIdent = Integer.parseInt(runway.substring(0,2) + "0");
        runwayIdent = ((runwayIdent + 180) > 360) ? (runwayIdent + 180) % 360 / 10 : (runwayIdent + 180) / 10;
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

}
