package com.toldcalculator.android.tc.controller;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.Aircraft;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeightFragment extends Fragment {

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

    setupUI(view);

    new SetupTask().execute(getActivity());

    nextButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        totalWeight.setText(String.valueOf(addWeight()));
      }
    });

    return view;
  }

  private void setupUI(View view) {
    basicEmptyWeight = (EditText) view.findViewById(R.id.bew_input);
    fuelWeight = (EditText) view.findViewById(R.id.fuel_input);
    paxWeight = (EditText) view.findViewById(R.id.pax_input);
    bagsWeight = (EditText) view.findViewById(R.id.bags_input);
    totalWeight = (EditText) view.findViewById(R.id.total_input);
    nextButton = (Button) view.findViewById(R.id.next_button);
  }

  private int addWeight() {
    int total = 0;
    total += Integer.parseInt(basicEmptyWeight.getText().toString());
    total += Integer.parseInt(fuelWeight.getText().toString());
    total += Integer.parseInt(paxWeight.getText().toString());
    total += Integer.parseInt(bagsWeight.getText().toString());
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
    }
  }

}
