package com.toldcalculator.android.tc.model;

import java.util.ArrayList;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * This class is used in the deserialization of the xml data received from the FAA Text Data Service.
 * Zero to 1000 metars can be returned in a request. Different elements can be returned based on
 * the station type and current condition. Not all elements available to be returned are in this class
 * at the moment as only a few are needed.
 */
@Root(strict = false)
public class Metar {

  @Element(name = "raw_text")
  private String rawText;

  @Element(name = "station_id")
  private String stationId;

  @Element(name = "observation_time")
  private String observationTime;

  @Element
  private String latitude;

  @Element
  private String longitude;

  @Element(name = "temp_c", required = false)
  private String tempC;

  @Element(name = "dewpoint_c", required = false)
  private String dewpointC;

  @Element(name = "wind_dir_degrees", required = false)
  private String windDirectionDegrees;

  @Element(name = "wind_speed_kt", required = false)
  private String windSpeedKt;

  @Element(name = "visibility_statute_mi", required = false)
  private String visibilityStatuteMile;

  @Element(name = "altim_in_hg", required = false)
  private String altimeterInHg;

  @Element(name = "sea_level_pressure_mb", required = false)
  private String seaLevelPressureMb;

  @Element(name = "quality_control_flags", required = false)
  private String qualityControlFlags;

  @Element(name = "auto_station", required = false)
  private boolean autoStation;

  @ElementList(inline = true, name = "sky_condition", required = false)
  private ArrayList<SkyCondition> skyCondition;

  @Element(name = "flight_category", required = false)
  private String flightCategory;

  @Element(name = "three_hr_pressure_tendency_mb", required = false)
  private boolean threeHrPressureTendencyMb;

  @Element(name = "metar_type", required = false)
  private String metarType;

  @Element(name = "elevation_m", required = false)
  private String elevationMeters;

  /**
   * Returns a string containing all the METAR information.
   * @return a string containing all the METAR information.
   */
  public String getRawText() {
    return rawText;
  }
  /**
   * Sets METAR raw text as a string containing all METAR information.
   * @param rawText a string containing all the METAR information.
   */
  public void setRawText(String rawText) {
    this.rawText = rawText;
  }
  /**
   * Returns the station identifier.
   * @return the station identifier.
   */
  public String getStationId() {
    return stationId;
  }
  /**
   * Sets the station identifier.
   * @param stationId the station identifier.
   */
  public void setStationId(String stationId) {
    this.stationId = stationId;
  }
  /**
   * Returns a string containing the time of observation.
   * @return a string containing the time of observation.
   */
  public String getObservationTime() {
    return observationTime;
  }
  /**
   * Sets the time of observation.
   * @param observationTime the time of observation.
   */
  public void setObservationTime(String observationTime) {
    this.observationTime = observationTime;
  }
  /**
   * Returns the stations latitude.
   * @return the stations latitude.
   */
  public String getLatitude() {
    return latitude;
  }
  /**
   * Sets the stations latitude.
   * @param latitude the stations latitude.
   */
  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }
  /**
   * Returns the stations longitude.
   * @return the stations longitude.
   */
  public String getLongitude() {
    return longitude;
  }
  /**
   * Sets the stations longitude.
   * @param longitude the stations longitude.
   */
  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }
  /**
   * Returns the temperature in celsius.
   * @return the temperature in celsius.
   */
  public String getTempC() {
    return tempC;
  }
  /**
   * Sets the temperature. Should be in celsius.
   * @param tempC the temperature in celsius.
   */
  public void setTempC(String tempC) {
    this.tempC = tempC;
  }
  /**
   * Returns the dewpoint in celsius.
   * @return the dewpoint in celsius.
   */
  public String getDewpointC() {
    return dewpointC;
  }
  /**
   * Sets the dewpoint. Should be in celsius.
   * @param dewpointC the dewpoint in celsius.
   */
  public void setDewpointC(String dewpointC) {
    this.dewpointC = dewpointC;
  }
  /**
   * Returns the observed wind direction in degrees.
   * @return the observed wind direction in degrees.
   */
  public String getWindDirectionDegrees() {
    return windDirectionDegrees;
  }
  /**
   * Set the observed wind direction in degrees.
   * @param windDirectionDegrees the observed wind direction in degrees.
   */
  public void setWindDirectionDegrees(String windDirectionDegrees) {
    this.windDirectionDegrees = windDirectionDegrees;
  }
  /**
   * Returns the wind speed in knots.
   * @return the wind speed in knots.
   */
  public String getWindSpeedKt() {
    return windSpeedKt;
  }
  /**
   * Set the wind speed in knots.
   * @param windSpeedKt the wind speed in knots.
   */
  public void setWindSpeedKt(String windSpeedKt) {
    this.windSpeedKt = windSpeedKt;
  }
  /**
   * Returns the observed visibility in statute miles.
   * @return the observed visibility in statute miles.
   */
  public String getVisibilityStatuteMile() {
    return visibilityStatuteMile;
  }
  /**
   * Set the observed visibility in statute miles.
   * @param visibilityStatuteMile the observed visibility in statute miles.
   */
  public void setVisibilityStatuteMile(String visibilityStatuteMile) {
    this.visibilityStatuteMile = visibilityStatuteMile;
  }
  /**
   * Returns the observed altimeter setting in inches of mercury.
   * @return the observed altimeter setting in inches of mercury.
   */
  public String getAltimeterInHg() {
    return altimeterInHg;
  }
  /**
   * Sets the observed altimeter setting. Should be in inches of mercury.
   * @param altimeterInHg the observed altimeter setting in inches of mercury.
   */
  public void setAltimeterInHg(String altimeterInHg) {
    this.altimeterInHg = altimeterInHg;
  }
  /**
   * Returns the observed sea level pressure in millibars.
   * @return the observed sea level pressure in millibars.
   */
  public String getSeaLevelPressureMb() {
    return seaLevelPressureMb;
  }
  /**
   * Sets the observed sea level pressure. Should be in millibars.
   * @param seaLevelPressureMb the observed sea level pressure in millibars.
   */
  public void setSeaLevelPressureMb(String seaLevelPressureMb) {
    this.seaLevelPressureMb = seaLevelPressureMb;
  }
  /**
   * Returns quality control flags.
   * @return quality control flags.
   */
  public String getQualityControlFlags() {
    return qualityControlFlags;
  }
  /**
   * Sets quality control flags.
   * @param qualityControlFlags quality control flags.
   */
  public void setQualityControlFlags(String qualityControlFlags) {
    this.qualityControlFlags = qualityControlFlags;
  }
  /**
   * Returns if auto station or not.
   * @return if auto station or not.
   */
  public boolean isAutoStation() {
    return autoStation;
  }
  /**
   * Sets if auto station or not.
   * @param autoStation if auto station or not.
   */
  public void setAutoStation(boolean autoStation) {
    this.autoStation = autoStation;
  }
  /**
   * Returns list of sky conditions at different altitudes, if any.
   * @return list of sky conditions at different altitudes, if any.
   */
  public ArrayList<SkyCondition> getSkyCondition() {
    return skyCondition;
  }
  /**
   * Sets list of sky conditions at different altitudes.
   * @param skyCondition list of sky conditions at different altitudes.
   */
  public void setSkyCondition(
      ArrayList<SkyCondition> skyCondition) {
    this.skyCondition = skyCondition;
  }
  /**
   * Returns observed flight category.
   * @return observed flight category.
   */
  public String getFlightCategory() {
    return flightCategory;
  }
  /**
   * Sets flight category.
   * @param flightCategory flight category.
   */
  public void setFlightCategory(String flightCategory) {
    this.flightCategory = flightCategory;
  }
  /**
   * Returns the three hour pressure trend in millibars.
   * @return the three hour pressure trend in millibars.
   */
  public boolean isThreeHrPressureTendencyMb() {
    return threeHrPressureTendencyMb;
  }
  /**
   * Sets the three hour pressure trend in millibars.
   * @param threeHrPressureTendencyMb the three hour pressure trend in millibars.
   */
  public void setThreeHrPressureTendencyMb(boolean threeHrPressureTendencyMb) {
    this.threeHrPressureTendencyMb = threeHrPressureTendencyMb;
  }
  /**
   * Returns type of metar.
   * @return type of metar.
   */
  public String getMetarType() {
    return metarType;
  }
  /**
   * Sets the type of metar.
   * @param metarType type of metar.
   */
  public void setMetarType(String metarType) {
    this.metarType = metarType;
  }
  /**
   * Returns the station elevation in meters.
   * @return the station elevation in meters.
   */
  public String getElevationMeters() {
    return elevationMeters;
  }
  /**
   * Sets the station elevation. Should be in meters.
   * @param elevationMeters the station elevation in meters.
   */
  public void setElevationMeters(String elevationMeters) {
    this.elevationMeters = elevationMeters;
  }
}