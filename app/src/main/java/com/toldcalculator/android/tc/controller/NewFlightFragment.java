package com.toldcalculator.android.tc.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.toldcalculator.android.tc.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFlightFragment extends Fragment {


  public NewFlightFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_new_flight, container, false);
    String[] profiles = new String[]{"N1234", "N2345", "N3456"};
    String[] airports = new String[]{"KABQ", "KAPA", "KPHX"};

    Spinner spinner = (Spinner) view.findViewById(R.id.aircraft_profiles);

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, profiles);

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);

    Button nextButton = view.findViewById(R.id.next_button);
    nextButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_container, new WeightFragment()).commit();
      }
    });

    return view;
  }

}
