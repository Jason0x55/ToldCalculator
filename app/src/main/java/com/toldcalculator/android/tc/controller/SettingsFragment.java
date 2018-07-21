package com.toldcalculator.android.tc.controller;


import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.controller.helpers.BundleConstants;

/**
 * This {@link Fragment} subclass is used to allow the user to set some settings using shared
 * preferences. Right now only setting the default airport works.
 */
public class SettingsFragment extends Fragment {

  private EditText defaultAirport;
  private EditText defaultAircraft;
  private Button saveDefaultsButton;
  private Editor editor;
  private SharedPreferences sharedPreferences;

  private static final String SHARED_PREF_NAME = "ToldCalculator";

  public SettingsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_settings, container, false);
    SetupUI(view);

    sharedPreferences = getContext().getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
    editor = sharedPreferences.edit();

    String airportIdent = sharedPreferences.getString(BundleConstants.AIRPORT_IDENT_KEY, null);
    String aircraftName = sharedPreferences.getString(BundleConstants.AIRCRAFT_NAME_KEY, null);

    defaultAirport.setText(airportIdent);

    return view;
  }

  private void SetupUI(View view) {
    defaultAirport = view.findViewById(R.id.default_airport_text);
    defaultAircraft = view.findViewById(R.id.default_aircraft_text);
    saveDefaultsButton = view.findViewById(R.id.save_defaults_button);
    saveDefaultsButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        editor.putString(BundleConstants.AIRPORT_IDENT_KEY, defaultAirport.getText().toString());
        editor.commit();
      }
    });
  }

}
