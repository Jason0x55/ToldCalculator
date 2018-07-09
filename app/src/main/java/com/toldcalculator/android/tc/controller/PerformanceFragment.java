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
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.TakeoffData;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerformanceFragment extends Fragment {

  private TextView runwayRequired;
  private TextView takeoffN1;
  private TextView takeoffV1;
  private TextView takeoffVR;
  private TextView takeoffV2;

  public PerformanceFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_performance, container, false);
    SetupUI(view);

    new GetTakeoffData().execute(getActivity());

    return view;
  }

  private void SetupUI(View view) {
    runwayRequired = view.findViewById(R.id.runway_required_text);
    takeoffN1 = view.findViewById(R.id.takeoff_n1_text);
    takeoffV1 = view.findViewById(R.id.takeoff_v1_text);
    takeoffVR = view.findViewById(R.id.takeoff_vr_text);
    takeoffV2 = view.findViewById(R.id.takeoff_v2_text);
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
      return ToldData.getInstance(contexts[0]).getTakeoffDataDao().select(5000, 18000, 16);
    }
  }
}
