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
  private int altitude;
  private int aircraftWeight;

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

    SetupUI(view);

    // TODO Implement save function and move MetarTask.execute()
    saveButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        new MetarTask().execute();
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

  private class GetTakeoffDataTask extends AsyncTask<Context, Void, TakeoffData> {

    @Override
    protected void onPostExecute(TakeoffData takeoffData) {
      runwayRequired.setText(String.valueOf(takeoffData.getTakeoffDistance()));
      takeoffV1.setText(String.valueOf(takeoffData.getTakeoffSpeedV1()));
      takeoffVR.setText(String.valueOf(takeoffData.getTakeoffSpeedVR()));
      takeoffV2.setText(String.valueOf(takeoffData.getTakeoffSpeedV2()));
      new GetTakeoffPowerN1Task().execute(getActivity());
    }

    @Override
    protected TakeoffData doInBackground(Context... contexts) {
      return ToldData.getInstance(contexts[0]).getTakeoffDataDao().select(altitude, aircraftWeight, temperature);
    }
  }
  private class GetTakeoffPowerN1Task extends AsyncTask<Context, Void, TakeoffPowerN1> {

    @Override
    protected void onPostExecute(TakeoffPowerN1 takeoffPowerN1) {
      takeoffN1.setText(String.valueOf(takeoffPowerN1.getTakeoffPowerN1()));
    }

    @Override
    protected TakeoffPowerN1 doInBackground(Context... contexts) {
      return ToldData.getInstance(contexts[0]).getTakeoffPowerN1Dao().select(altitude, temperature);
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
      new RunwayTask().execute(getActivity());
    }
  }

  private class RunwayTask extends AsyncTask<Context, Void, AirportAndRunways> {

    @Override
    protected void onPostExecute(AirportAndRunways airportAndRunways) {
      altitude = airportAndRunways.getAirport().getElevation();
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
      new GetTakeoffDataTask().execute(getActivity());
    }

    @Override
    protected AirportAndRunways doInBackground(Context... contexts) {
      return ToldData.getInstance(contexts[0]).getAirportDao().selectWithRunways(airportIdent);
    }
  }

}
