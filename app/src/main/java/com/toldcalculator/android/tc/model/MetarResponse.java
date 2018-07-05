package com.toldcalculator.android.tc.model;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class MetarResponse {

  @ElementList
  private List<Metar> data;

  public List<Metar> getData() {
    return data;
  }

  public void setData(List<Metar> data) {
    this.data = data;
  }
}
