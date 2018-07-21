package com.toldcalculator.android.tc.controller;


import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.controller.helpers.BundleConstants;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.Aircraft;
import com.toldcalculator.android.tc.model.pojo.UserInfo;
import java.util.ArrayList;
import java.util.List;


/**
 * This {@link Fragment} subclass is the landing page for Told Calculator. This is were the user
 * can select a aircraft profile and airport. When the user presses the next button they move to
 * the {@link WeightFragment}.
 */
public class NewFlightFragment extends Fragment {

  private Spinner aircraftSpinner;
  private EditText airportText;
  private Button nextButton;

  private String airportIdent;
  private long userId;

  private static final String DEFAULT_AIRPORT = "KABQ";
  private static final String SHARED_PREF_NAME = "ToldCalculator";
  private static final String NO_PROFILE_SELECTED = "No profile selected.";
  private static final String INVALID_IDENTIFIER = "Identifer must be 3 or 4 characters. (KABQ, E95)";

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_new_flight, container, false);
    setupUI(view);
    return view;
  }

  private void setupUI(View view) {
    aircraftSpinner = view.findViewById(R.id.aircraft_profiles);
    airportText = view.findViewById(R.id.airport_text);
    nextButton = view.findViewById(R.id.next_button);
    SharedPreferences sharedPreferences = getContext().getApplicationContext()
        .getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
    String airportIdent = sharedPreferences.getString(BundleConstants.AIRPORT_IDENT_KEY, null);
    if (airportIdent == null) {
      airportIdent = DEFAULT_AIRPORT;
    }
    airportText.setText(airportIdent);

    nextButtonSetup();
    new SetupTask().execute();
  }

  private void nextButtonSetup() {
    nextButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (aircraftSpinner.getSelectedItem() == null) {
          Toast.makeText(getActivity(), NO_PROFILE_SELECTED, Toast.LENGTH_SHORT).show();
          return;
        }
        airportIdent = airportText.getText().toString();
        if (airportIdent.length() < 3 || airportIdent.length() > 4) {
          Toast.makeText(getActivity(), INVALID_IDENTIFIER, Toast.LENGTH_LONG).show();
        } else {
          // Close/hide soft keyboard.
          InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

          Bundle bundle = new Bundle();
          bundle.putString(BundleConstants.AIRPORT_IDENT_KEY, airportIdent);
          bundle.putString(BundleConstants.AIRCRAFT_NAME_KEY, (String) aircraftSpinner.getSelectedItem());
          bundle.putLong(BundleConstants.USER_ID_KEY, userId);

          WeightFragment weightFragment = new WeightFragment();
          weightFragment.setArguments(bundle);

          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

          fragmentManager.beginTransaction().replace(R.id.main_container, weightFragment)
              .commit();
        }
      }
    });
  }

  /*
   * This AsyncTask queries the database to get the current users information and populate the spinner.
   */
  private class SetupTask extends AsyncTask<Void, Void, UserInfo> {

    @Override
    protected UserInfo doInBackground(Void... voids) {
      return ToldData.getInstance(getContext()).getUserDao().userInfo();
    }

    @Override
    protected void onPostExecute(UserInfo userInfo) {
      if (userInfo != null) {
        List<String> profiles = new ArrayList<>();
        for (Aircraft aircraft : userInfo.getAircraft()) {
          profiles.add(aircraft.getName());
        }

        if (getActivity() != null) {
          ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, profiles);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          aircraftSpinner.setAdapter(adapter);
        }
        userId = userInfo.getUser().getId();
      } else {
        new SetupTask().execute();
      }
    }
  }
}
