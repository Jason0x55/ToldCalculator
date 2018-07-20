package com.toldcalculator.android.tc.controller;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.toldcalculator.android.tc.BundleConstants;
import com.toldcalculator.android.tc.MainActivity;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.Aircraft;

/**
 * This {@link Fragment} subclass allows the user to input weight information for the flight. The
 * next button moves the user to the {@link PerformanceFragment}.
 */
public class WeightFragment extends Fragment {

  private String airportIdent;
  private String aircraftID;

  public WeightFragment() {
    // Required empty public constructor
  }

  private EditText basicEmptyWeight;
  private EditText fuelWeight;
  private EditText paxWeight;
  private EditText bagsWeight;
  private EditText totalWeight;
  private Button nextButton;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_weight, container, false);

    Bundle bundle = this.getArguments();
    if(bundle != null){
      airportIdent = bundle.getString(BundleConstants.AIRPORT_IDENT_KEY);
      aircraftID = bundle.getString(BundleConstants.AIRCRAFT_NAME_KEY);
    }
    setupUI(view);
    return view;
  }

  private void setupUI(View view) {
    basicEmptyWeight = (EditText) view.findViewById(R.id.bew_input);
    fuelWeight = (EditText) view.findViewById(R.id.fuel_input);
    paxWeight = (EditText) view.findViewById(R.id.pax_input);
    bagsWeight = (EditText) view.findViewById(R.id.bags_input);
    totalWeight = (EditText) view.findViewById(R.id.total_input);
    nextButton = (Button) view.findViewById(R.id.next_button);
    editTextChangedSetup();
    setupNextButton();
    new SetupTask().execute(getActivity());
  }

  private void editTextChangedSetup() {
    basicEmptyWeight.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        totalWeight.setText(String.valueOf(totalWeight()));
      }
    });
    fuelWeight.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        totalWeight.setText(String.valueOf(totalWeight()));
      }
    });
    paxWeight.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        totalWeight.setText(String.valueOf(totalWeight()));
      }
    });
    bagsWeight.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        totalWeight.setText(String.valueOf(totalWeight()));
      }
    });
  }

  private void setupNextButton() {
    nextButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        // Close/hide soft keyboard.
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        Bundle bundle = new Bundle();
        bundle.putString(BundleConstants.AIRPORT_IDENT_KEY, airportIdent);
        bundle.putInt(BundleConstants.AIRCRAFT_WEIGHT_KEY, Integer.parseInt("0" + totalWeight.getText().toString()));
        bundle.putString(BundleConstants.AIRCRAFT_NAME_KEY, aircraftID);

        PerformanceFragment performanceFragment = new PerformanceFragment();
        performanceFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_container, performanceFragment)
            .commit();
      }
    });
  }

  private int totalWeight() {
    int total = 0;
    // Leading zero used in case TextView is empty.
    total += Integer.parseInt("0" + basicEmptyWeight.getText().toString());
    total += Integer.parseInt("0" + fuelWeight.getText().toString());
    total += Integer.parseInt("0" + paxWeight.getText().toString());
    total += Integer.parseInt("0" + bagsWeight.getText().toString());
    return total;
  }

  private class SetupTask extends AsyncTask<Context, Void, Aircraft> {

    @Override
    protected Aircraft doInBackground(Context... contexts) {
      return ToldData.getInstance(contexts[0]).getAircraftDao().select();
    }

    @Override
    protected void onPostExecute(Aircraft aircraft) {
      basicEmptyWeight.setText(String.valueOf(aircraft.getBasicEmptyWeight()));
      totalWeight.setText(String.valueOf(totalWeight()));
    }
  }

}
