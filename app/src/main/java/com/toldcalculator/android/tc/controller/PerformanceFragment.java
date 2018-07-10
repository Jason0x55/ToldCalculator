package com.toldcalculator.android.tc.controller;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.model.MetarResponse;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.Runway;
import com.toldcalculator.android.tc.model.entity.TakeoffData;
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

  private String airportIdent;
  private int temperature;
  private int elevation;
  private int aircraftWeight;

  private static final int hoursBeforeNow = 1;

  public PerformanceFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_performance, container, false);

    SetupUI(view);

    Bundle bundle = this.getArguments();
    if(bundle != null){
      airportIdent = bundle.getString("ICAO");
      aircraftWeight = bundle.getInt("WT");
    }

    new MetarTask().execute();
    new RunwayTask().execute(getActivity());
    new GetTakeoffData().execute(getActivity());

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
  }

  private class GetTakeoffData extends AsyncTask<Context, Void, TakeoffData> {

    @Override
    protected void onPostExecute(TakeoffData takeoffData) {
      runwayRequired.setText(String.valueOf(takeoffData.getTakeoffDistance()));
      //TODO add N1 data
      takeoffN1.setText("95.3");
      takeoffV1.setText(String.valueOf(takeoffData.getTakeoffSpeedV1()));
      takeoffVR.setText(String.valueOf(takeoffData.getTakeoffSpeedVR()));
      takeoffV2.setText(String.valueOf(takeoffData.getTakeoffSpeedV2()));
    }

    @Override
    protected TakeoffData doInBackground(Context... contexts) {
      return ToldData.getInstance(contexts[0]).getTakeoffDataDao().select(elevation, aircraftWeight, temperature);
    }
  }

  private class RunwayTask extends AsyncTask<Context, Void, AirportAndRunways> {

    @Override
    protected void onPostExecute(AirportAndRunways airportAndRunways) {
      elevation = airportAndRunways.getAirport().getElevation();
      airportAndRunways.getRunway();

    }

    @Override
    protected AirportAndRunways doInBackground(Context... contexts) {
      return ToldData.getInstance(contexts[0]).getAirportDao().selectWithRunways(airportIdent);
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
      temperature = (int) Double.parseDouble(metarResponse.getData().get(0).getTempC());
    }
  }
}
