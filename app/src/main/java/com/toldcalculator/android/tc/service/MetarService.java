package com.toldcalculator.android.tc.service;

import com.toldcalculator.android.tc.model.MetarResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MetarService {

  @GET("httpparam?datasource=metars&requestType=retrieve&format=xml&mostRecentForEachStation=constraint&=1")
  Call<MetarResponse> response(@Query("stationString") String icaoId, @Query("hoursBeforeNow") double hour);

}
