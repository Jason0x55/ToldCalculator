package com.toldcalculator.android.tc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


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

//    spinner = (Spinner) view.findViewById(R.id.airport_list);
//    adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, airports);
//
//    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//    spinner.setAdapter(adapter);

    return view;
  }

}
