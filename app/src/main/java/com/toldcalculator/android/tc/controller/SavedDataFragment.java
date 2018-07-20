package com.toldcalculator.android.tc.controller;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.toldcalculator.android.tc.MainActivity;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.SavedTakeoffData;
import com.toldcalculator.android.tc.model.entity.TakeoffData;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedDataFragment extends Fragment {

  private RecyclerView recyclerView;
  private SavedTakeoffDataAdapter adapter;

  public SavedDataFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_saved_data, container, false);
    recyclerView = (RecyclerView) view.findViewById(R.id.saved_recycler_view);
    DividerItemDecoration itemDecor = new DividerItemDecoration(getActivity(), LinearLayout.HORIZONTAL);
    recyclerView.addItemDecoration(itemDecor);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    new SetupListTask().execute();

    return view;
  }

  private class SavedTakeoffDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView savedText;
    private TextView savedTimestamp;

    private SavedTakeoffData savedTakeoffData;

    public SavedTakeoffDataHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.saved_row, parent, false));

      itemView.setOnClickListener(this);
      savedText = itemView.findViewById(R.id.saved_text);
      savedTimestamp = itemView.findViewById(R.id.saved_timestamp);
    }

    public void bind(SavedTakeoffData savedTakeoffData) {
      this.savedTakeoffData = savedTakeoffData;
      savedText.setText(savedTakeoffData.toString());
      savedTimestamp.setText(savedTakeoffData.getTimestamp().toString());
    }

    @Override
    public void onClick(View v) {
      Bundle bundle = new Bundle();
      bundle.putLong(MainActivity.SAVED_ID_KEY, savedTakeoffData.getId());
      bundle.putString(MainActivity.AIRPORT_IDENT_KEY, savedTakeoffData.getAirportId());
      bundle.putInt(MainActivity.AIRCRAFT_WEIGHT_KEY, savedTakeoffData.getTakeoffWeight());
      bundle.putString(MainActivity.AIRCRAFT_NAME_KEY, savedTakeoffData.getAircraftName());
      PerformanceFragment performanceFragment = new PerformanceFragment();
      performanceFragment.setArguments(bundle);
      FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.main_container, performanceFragment)
          .commit();
    }
  }

  private class SavedTakeoffDataAdapter extends RecyclerView.Adapter<SavedTakeoffDataHolder> {

    private List<SavedTakeoffData> savedTakeoffDataList;

    public SavedTakeoffDataAdapter(List<SavedTakeoffData> savedTakeoffData) {
      savedTakeoffDataList = savedTakeoffData;
    }

    @Override
    public SavedTakeoffDataHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
      return new SavedTakeoffDataHolder(layoutInflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(SavedTakeoffDataHolder savedTakeoffDataHolder, int postion) {
      SavedTakeoffData savedTakeoffData = savedTakeoffDataList.get(postion);
      savedTakeoffDataHolder.bind(savedTakeoffData);
    }

    @Override
    public int getItemCount() {
      return savedTakeoffDataList.size();
    }
  }

  private class SetupListTask extends AsyncTask<Void, Void, List<SavedTakeoffData>> {

    @Override
    protected List<SavedTakeoffData> doInBackground(Void... voids) {
      return ToldData.getInstance(getContext()).getSavedTakeoffDataDao().selectAll();
    }

    @Override
    protected void onPostExecute(List<SavedTakeoffData> savedTakeoffDataList) {
      List<String> data = new ArrayList<>();
      adapter = new SavedTakeoffDataAdapter(savedTakeoffDataList);
      recyclerView.setAdapter(adapter);
    }
  }

}
