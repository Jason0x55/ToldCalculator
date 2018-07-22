package com.toldcalculator.android.tc.model;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * This class is used in the deserialization of the xml data received from the FAA Text Data Service.
 * All elements except Metar are ignored.
 */
@Root(strict = false)
public class MetarResponse {

  @ElementList
  private List<Metar> data;

  /**
   * Returns a list of metars that was received as a response to the request.
   * @return returns a list of {@link Metar} objects.
   */
  public List<Metar> getData() {
    return data;
  }
  /**
   * Sets a list of metars that was received as a response to the request.
   * @param data a list of metars that was received as a response to the request.
   */
  public void setData(List<Metar> data) {
    this.data = data;
  }
}
