package com.toldcalculator.android.tc.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.toldcalculator.android.tc.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerformanceFragment extends Fragment {


  public PerformanceFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_performance, container, false);

    return view;
  }

}