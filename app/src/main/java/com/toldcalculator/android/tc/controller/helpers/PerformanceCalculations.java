package com.toldcalculator.android.tc.controller.helpers;

import com.toldcalculator.android.tc.model.entity.TakeoffData;
import com.toldcalculator.android.tc.model.entity.TakeoffPowerN1;
import java.util.List;

/**
 * This class performs all the necessary interpolations to get the takeoff numbers.
 * Based on aircraft weight, temperature, and elevation it can be necessary to interpolate
 * between eight different values to get the takeoff distance, V1, VR, V2 speeds.
 * The takeoff power N1 setting is based off elevation and temperature and it can be
 * necessary to interpolate between four different values.
 */
public class PerformanceCalculations {
  private List<TakeoffData> takeoffDataList;
  private List<TakeoffPowerN1> takeoffPowerN1List;
  private int altitude;
  private int aircraftWeight;
  private int temperature;

  private double takeoffDistance;
  private double takeoffSpeedV1;
  private double takeoffSpeedV2;
  private double takeoffSpeedVR;
  private double takeoffPowerN1;

  private static final int HIGHALT_HIGHWEIGHT_HIGHTEMP = 0;
  private static final int HIGHALT_HIGHWEIGHT_LOWTEMP = 1;
  private static final int HIGHALT_LOWWEIGHT_HIGHTEMP = 2;
  private static final int HIGHALT_LOWWEIGHT_LOWTEMP = 3;
  private static final int LOWALT_HIGHWEIGHT_HIGHTEMP = 4;
  private static final int LOWALT_HIGHWEIGHT_LOWTEMP = 5;
  private static final int LOWALT_LOWWEIGHT_HIGHTEMP = 6;
  private static final int LOWALT_LOWWEIGHT_LOWTEMP = 7;

  private static final int HIGHALT_HIGHTEMP = 0;
  private static final int HIGHALT_LOWTEMP = 1;
  private static final int LOWALT_HIGHTEMP = 2;
  private static final int LOWALT_LOWTEMP = 3;

  /**
   * This constructor sets up PerformanceCalculations class and calculates takeoff data
   * or takeoff power N1 based on the parameters passed. If takeoffPowerN1List is null then
   * takeoff data calculations are performed. If takeoffDataList is null takeoff power N1 calculations
   * are performed.
   * @param takeoffDataList list of {@link TakeoffData} results from query.
   * @param takeoffPowerN1List list of {@link TakeoffPowerN1} results from query.
   * @param altitude current airport elevation.
   * @param aircraftWeight current aircraft weight.
   * @param temperature current airport temperature.
   */
  public PerformanceCalculations(List<TakeoffData> takeoffDataList, List<TakeoffPowerN1> takeoffPowerN1List,
      int altitude, int aircraftWeight, int temperature) {
    this.takeoffDataList = takeoffDataList;
    this.takeoffPowerN1List = takeoffPowerN1List;
    this.altitude = altitude;
    this.aircraftWeight = aircraftWeight;
    this.temperature = temperature;
    if (takeoffPowerN1List == null) {
      calculateTakeoffData();
    } else {
      calculatePowerN1();
    }
  }

  private void calculateTakeoffData() {
    double altFactor;
    int highAltitude = takeoffDataList.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getAltitude();
    int lowAltitude = takeoffDataList.get(LOWALT_LOWWEIGHT_LOWTEMP).getAltitude();
    if (lowAltitude == highAltitude) {
      altFactor = 1;
    } else {
      altFactor = (altitude - lowAltitude) / (double) (highAltitude - lowAltitude);
    }

    double tempFactor;
    int highTemp = takeoffDataList.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getTemperature();
    int lowTemp = takeoffDataList.get(LOWALT_LOWWEIGHT_LOWTEMP).getTemperature();
    if (highTemp == lowTemp) {
      tempFactor = 1;
    } else {
      tempFactor = (temperature - lowTemp) / (double) (highTemp - lowTemp);
    }

    double weightFactor;
    int highWeight = takeoffDataList.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getWeight();
    int lowWeight = takeoffDataList.get(LOWALT_LOWWEIGHT_LOWTEMP).getWeight();
    if (highWeight == lowWeight) {
      weightFactor = 1;
    } else {
      weightFactor = (aircraftWeight - lowWeight) / (double) (highWeight - lowWeight);
    }

    double highV2 = takeoffDataList.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getTakeoffSpeedV2();
    double lowV2 = takeoffDataList.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedV2();

    double highVR = takeoffDataList.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getTakeoffSpeedVR();
    double lowVR = takeoffDataList.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedVR();

    double highV1;
    double lowV1;
    double highDistance;
    double lowDistance;

    // Initial test values.
    // altitude = 3500;
    // temperature = 21;
    // aircraftWeight = 14500;
    // High Alt - High/Low weight - High Temp : Test case values - 123 - 117
    highV1 = (takeoffDataList.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getTakeoffSpeedV1()
        - takeoffDataList.get(HIGHALT_HIGHWEIGHT_LOWTEMP).getTakeoffSpeedV1())
        * weightFactor + takeoffDataList.get(HIGHALT_HIGHWEIGHT_LOWTEMP).getTakeoffSpeedV1();
    // High Alt - High/Low weight - Low Temp : Test case values - 119 - 113
    lowV1 = (takeoffDataList.get(HIGHALT_LOWWEIGHT_HIGHTEMP).getTakeoffSpeedV1()
        - takeoffDataList.get(HIGHALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedV1())
        * weightFactor + takeoffDataList.get(HIGHALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedV1();

    double highAltV1 = (highV1 - lowV1) * tempFactor + lowV1;

    highDistance =
        (takeoffDataList.get(HIGHALT_HIGHWEIGHT_HIGHTEMP).getTakeoffDistance()
            - takeoffDataList.get(HIGHALT_HIGHWEIGHT_LOWTEMP).getTakeoffDistance())
            * weightFactor + takeoffDataList.get(HIGHALT_HIGHWEIGHT_LOWTEMP).getTakeoffDistance();
    lowDistance =
        (takeoffDataList.get(HIGHALT_LOWWEIGHT_HIGHTEMP).getTakeoffDistance()
            - takeoffDataList.get(HIGHALT_LOWWEIGHT_LOWTEMP).getTakeoffDistance())
            * weightFactor + takeoffDataList.get(HIGHALT_LOWWEIGHT_LOWTEMP).getTakeoffDistance();

    double highAltDistance = (highDistance - lowDistance) * tempFactor + lowDistance;

    // Low Alt - High/Low weight - High temp : Test case values - 121 - 115
    highV1 = (takeoffDataList.get(LOWALT_HIGHWEIGHT_HIGHTEMP).getTakeoffSpeedV1()
        - takeoffDataList.get(LOWALT_HIGHWEIGHT_LOWTEMP).getTakeoffSpeedV1())
        * weightFactor + takeoffDataList.get(LOWALT_HIGHWEIGHT_LOWTEMP).getTakeoffSpeedV1();
    // Low Alt - High/Low weight - Low temp : Test case values - 118 - 112
    lowV1 = (takeoffDataList.get(LOWALT_LOWWEIGHT_HIGHTEMP).getTakeoffSpeedV1()
        - takeoffDataList.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedV1())
        * weightFactor + takeoffDataList.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffSpeedV1();

    double lowAltV1 = (highV1 - lowV1) * tempFactor + lowV1;

    highDistance =
        (takeoffDataList.get(LOWALT_HIGHWEIGHT_HIGHTEMP).getTakeoffDistance()
            - takeoffDataList.get(LOWALT_HIGHWEIGHT_LOWTEMP).getTakeoffDistance())
            * weightFactor + takeoffDataList.get(LOWALT_HIGHWEIGHT_LOWTEMP).getTakeoffDistance();
    lowDistance =
        (takeoffDataList.get(LOWALT_LOWWEIGHT_HIGHTEMP).getTakeoffDistance()
            - takeoffDataList.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffDistance())
            * weightFactor + takeoffDataList.get(LOWALT_LOWWEIGHT_LOWTEMP).getTakeoffDistance();

    double lowAltDistance = (highDistance - lowDistance) * tempFactor + lowDistance;

    takeoffDistance = (highAltDistance - lowAltDistance) * altFactor + lowAltDistance;
    takeoffSpeedV1 = (highAltV1 - lowAltV1) * altFactor + lowAltV1;
    takeoffSpeedV2 = (highV2 - lowV2) * weightFactor + lowV2;
    takeoffSpeedVR = (highVR - lowVR) * weightFactor + lowVR;
  }

  private void calculatePowerN1() {
    double altFactor;
    int highAltitude = takeoffPowerN1List.get(HIGHALT_HIGHTEMP).getAltitude();
    int lowAltitude = takeoffPowerN1List.get(LOWALT_LOWTEMP).getAltitude();
    if (highAltitude == lowAltitude) {
      altFactor = 1;
    } else {
      altFactor = (altitude - lowAltitude) / (double) (highAltitude - lowAltitude);
    }

    double tempFactor;
    int highTemp = takeoffPowerN1List.get(HIGHALT_HIGHTEMP).getTemperature();
    int lowTemp = takeoffPowerN1List.get(LOWALT_LOWTEMP).getTemperature();
    if (highTemp == lowTemp) {
      tempFactor = 1;
    } else {
      tempFactor = (temperature - lowTemp) / (double) (highTemp - lowTemp);
    }

    double highTempN1 =
        (takeoffPowerN1List.get(HIGHALT_LOWTEMP).getTakeoffPowerN1()
            - takeoffPowerN1List.get(HIGHALT_HIGHTEMP).getTakeoffPowerN1())
            * altFactor + takeoffPowerN1List.get(HIGHALT_HIGHTEMP).getTakeoffPowerN1();
    double lowTempN1 =
        (takeoffPowerN1List.get(LOWALT_LOWTEMP).getTakeoffPowerN1()
            - takeoffPowerN1List.get(LOWALT_HIGHTEMP).getTakeoffPowerN1())
            * altFactor + takeoffPowerN1List.get(LOWALT_HIGHTEMP).getTakeoffPowerN1();

    takeoffPowerN1 = (lowTempN1 - highTempN1) * tempFactor + highTempN1;
  }

  /**
   * Getter that returns the calculated takeoff distance as a string.
   * @return the calculated takeoff distance.
   */
  public String getTakeoffDistance() {
    return String.valueOf(Math.round(takeoffDistance));
  }

  /**
   * Getter that returns the calculated takeoff speed V1 as a string.
   * @return the calculated takeoff V1 speed.
   */
  public String getTakeoffSpeedV1() {
    return String.valueOf(Math.round(takeoffSpeedV1));
  }

  /**
   * Getter that returns the calculated takeoff speed V2 as a string.
   * @return the calculated takeoff V2 speed.
   */
  public String getTakeoffSpeedV2() {
    return String.valueOf(Math.round(takeoffSpeedV2));
  }

  /**
   * Getter that returns the calculated takeoff speed VR as a string.
   * @return the calculated takeoff VR speed.
   */
  public String getTakeoffSpeedVR() {
    return String.valueOf(Math.round(takeoffSpeedVR));
  }

  /**
   * Getter that returns the calculated takeoff power N1 setting as a string.
   * @return the calculated takeoff power N1 setting.
   */
  public double getTakeoffPowerN1() {
    return takeoffPowerN1;
  }
}
