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

  public WeatherFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    //new MetarTask().execute();

    View view = inflater.inflate(R.layout.fragment_weather, container, false);
    recyclerView = (RecyclerView) view.findViewById(R.id.weather_recycler_view);
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

  private class MetarTask extends AsyncTask<Void, Void, MetarResponse> {

    private static final String BASE_URL = "https://www.aviationweather.gov/adds/dataserver_current/";
    public static final String FAILED_TEXT = "Failed to retrieve weather!";
    private static final double hoursBeforeNow = 1.25;

    @Override
    protected MetarResponse doInBackground(Void... voids) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .build();

      MetarService client = retrofit.create(MetarService.class);
      Response<MetarResponse> response = null;

      {
        try {
          response = client.response("KABQ,KDEN,KPHX,KTUS", hoursBeforeNow).execute();
        } catch (IOException e) {
          // Do nothing for now.
        }
      }
      return response.body();
    }

    @Override
    protected void onPostExecute(MetarResponse metarResponse) {
      if (metarResponse.getData().size() > 0) {
        weather = metarResponse.getData().get(1).getRawText();
        List<Weather> exampleWeather = new ArrayList<>();
        List<Metar> metars = metarResponse.getData();

        for (Metar metar : metars) {
          Weather w = new Weather();
          w.setRawText(metar.getRawText());
          exampleWeather.add(w);
        }
//        for (int i = 0; i < 51; i++) {
//          Weather w = new Weather();
//          w.setRawText(weather);
//          exampleWeather.add(w);
//        }
        adapter = new WeatherAdapter(exampleWeather);
        recyclerView.setAdapter(adapter);
      } else {
        Toast.makeText(getActivity(), FAILED_TEXT, Toast.LENGTH_LONG).show();
      }
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
