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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.Aircraft;
import com.toldcalculator.android.tc.model.pojo.UserInfo;
import java.util.ArrayList;
import java.util.List;


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

    setupUI(view);

    nextButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        airportIdent = airportText.getText().toString();

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        Bundle bundle = new Bundle();
        bundle.putString("ICAO", airportIdent);

        WeightFragment weightFragment = new WeightFragment();
        weightFragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.main_container, weightFragment)
            .commit();
      }
    });

    new SetupTask().execute(getActivity());
    return view;
  }

  private void setupUI(View view) {
    aircraftSpinner = (Spinner) view.findViewById(R.id.aircraft_profiles);
    airportText = (EditText) view.findViewById(R.id.airport_text);
    nextButton = (Button) view.findViewById(R.id.next_button);
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
}
