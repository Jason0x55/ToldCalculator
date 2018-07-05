package com.toldcalculator.android.tc.model;

import java.util.ArrayList;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

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

  public String getRawText() {
    return rawText;
  }

  public void setRawText(String rawText) {
    this.rawText = rawText;
  }

  public String getStationId() {
    return stationId;
  }

  public void setStationId(String stationId) {
    this.stationId = stationId;
  }

  public String getObservationTime() {
    return observationTime;
  }

  public void setObservationTime(String observationTime) {
    this.observationTime = observationTime;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getTempC() {
    return tempC;
  }

  public void setTempC(String tempC) {
    this.tempC = tempC;
  }

  public String getDewpointC() {
    return dewpointC;
  }

  public void setDewpointC(String dewpointC) {
    this.dewpointC = dewpointC;
  }

  public String getWindDirectionDegrees() {
    return windDirectionDegrees;
  }

  public void setWindDirectionDegrees(String windDirectionDegrees) {
    this.windDirectionDegrees = windDirectionDegrees;
  }

  public String getWindSpeedKt() {
    return windSpeedKt;
  }

  public void setWindSpeedKt(String windSpeedKt) {
    this.windSpeedKt = windSpeedKt;
  }

  public String getVisibilityStatuteMile() {
    return visibilityStatuteMile;
  }

  public void setVisibilityStatuteMile(String visibilityStatuteMile) {
    this.visibilityStatuteMile = visibilityStatuteMile;
  }

  public String getAltimeterInHg() {
    return altimeterInHg;
  }

  public void setAltimeterInHg(String altimeterInHg) {
    this.altimeterInHg = altimeterInHg;
  }

  public String getSeaLevelPressureMb() {
    return seaLevelPressureMb;
  }

  public void setSeaLevelPressureMb(String seaLevelPressureMb) {
    this.seaLevelPressureMb = seaLevelPressureMb;
  }

  public String getQualityControlFlags() {
    return qualityControlFlags;
  }

  public void setQualityControlFlags(String qualityControlFlags) {
    this.qualityControlFlags = qualityControlFlags;
  }

  public boolean isAutoStation() {
    return autoStation;
  }

  public void setAutoStation(boolean autoStation) {
    this.autoStation = autoStation;
  }

  public ArrayList<SkyCondition> getSkyCondition() {
    return skyCondition;
  }

  public void setSkyCondition(
      ArrayList<SkyCondition> skyCondition) {
    this.skyCondition = skyCondition;
  }

  public String getFlightCategory() {
    return flightCategory;
  }

  public void setFlightCategory(String flightCategory) {
    this.flightCategory = flightCategory;
  }

  public boolean isThreeHrPressureTendencyMb() {
    return threeHrPressureTendencyMb;
  }

  public void setThreeHrPressureTendencyMb(boolean threeHrPressureTendencyMb) {
    this.threeHrPressureTendencyMb = threeHrPressureTendencyMb;
  }

  public String getMetarType() {
    return metarType;
  }

  public void setMetarType(String metarType) {
    this.metarType = metarType;
  }

  public String getElevationMeters() {
    return elevationMeters;
  }

  public void setElevationMeters(String elevationMeters) {
    this.elevationMeters = elevationMeters;
  }
}
