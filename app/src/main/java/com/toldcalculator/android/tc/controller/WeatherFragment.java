package com.toldcalculator.android.tc.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.toldcalculator.android.tc.R;
import com.toldcalculator.android.tc.model.Metar;
import com.toldcalculator.android.tc.model.MetarResponse;
import com.toldcalculator.android.tc.model.db.ToldData;
import com.toldcalculator.android.tc.model.entity.Weather;
import com.toldcalculator.android.tc.service.MetarService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


/**
 * A simple {@link Fragment} subclass that displays weather.
 */
public class WeatherFragment extends Fragment {

  private RecyclerView recyclerView;
  private WeatherAdapter adapter;
  private FloatingActionButton addButton;
  private String weather;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_weather, container, false);
    recyclerView = view.findViewById(R.id.weather_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    addButton = view.findViewById(R.id.add_airport);
    addButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getActivity(), "Add airport", Toast.LENGTH_SHORT).show();
      }
    });

    new GetWeatherTask().execute();

    return view;
  }

  private class WeatherHolder extends RecyclerView.ViewHolder {

    private TextView weatherText;

    public WeatherHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.weather_row, parent, false));

      weatherText = itemView.findViewById(R.id.weather_text);
    }

    public void bind(Weather weather) {
      weatherText.setText(weather.getRawText());
    }
  }

  private class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder> {

    private List<Weather> weatherList;

    public WeatherAdapter(List<Weather> weather) {
      weatherList = weather;
    }

    @Override
    public WeatherHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
      return new WeatherHolder(layoutInflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(WeatherHolder weatherHolder, int postion) {
      Weather weather = weatherList.get(postion);
      weatherHolder.bind(weather);
    }

    @Override
    public int getItemCount() {
      return weatherList.size();
    }
  }

  private class GetWeatherTask extends AsyncTask<Void, Void, List<Weather>> {

    @Override
    protected void onPostExecute(List<Weather> weatherList) {
      if (weatherList.size() > 0) {
        adapter = new WeatherAdapter(weatherList);
        recyclerView.setAdapter(adapter);
      }
    }

    @Override
    protected List<Weather> doInBackground(Void... voids) {
      return ToldData.getInstance(getContext()).getWeatherDao().selectAll();
    }
  }
}
