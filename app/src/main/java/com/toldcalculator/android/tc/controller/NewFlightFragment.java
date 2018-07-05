package com.toldcalculator.android.tc.controller;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.model.MetarResponse;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.Aircraft;
import com.toldcalculator.android.tc.model.pojo.UserInfo;
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
public class NewFlightFragment extends Fragment {

  private Spinner aircraftSpinner;
  private EditText airportText;
  private Button nextButton;

  private String airportIdent;

  public NewFlightFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_new_flight, container, false);

    aircraftSpinner = (Spinner) view.findViewById(R.id.aircraft_profiles);
    airportText = (EditText) view.findViewById(R.id.airport_text);
    nextButton = (Button) view.findViewById(R.id.next_button);

    nextButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        airportIdent = airportText.getText().toString();
        //new MetarTask().execute();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_container, new WeightFragment())
            .commit();
      }
    });

    new SetupTask().execute(getActivity());
    return view;
  }

  private class SetupTask extends AsyncTask<Context, Void, UserInfo> {

    @Override
    protected UserInfo doInBackground(Context... contexts) {
      return ToldData.getInstance(contexts[0]).getUserDao().userInfo();
    }

    @Override
    protected void onPostExecute(UserInfo userInfo) {
      List<String> profiles = new ArrayList<>();
      for (Aircraft aircraft : userInfo.getAircraft()) {
        profiles.add(aircraft.getName());
      }

      ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, profiles);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      aircraftSpinner.setAdapter(adapter);
      airportText.setText(userInfo.getAirport().get(0).getIcaoId());
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
          response = client.response(airportIdent, 1).execute();
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
