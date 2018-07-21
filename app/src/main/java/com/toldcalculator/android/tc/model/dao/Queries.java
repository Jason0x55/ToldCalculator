package com.toldcalculator.android.tc.model.dao;

/**
 * This class holds queries for the TakeoffDataDao and TakeoffPowerN1 interfaces.
 */
public class Queries {

  /**
   * This query string finds results for greater than or equal altitude,
   * greater than or equal weight, greater than or equal temperature,
   * and limits the results to 1.
   */
  public static final String HIGHALT_HIGHWEIGHT_HIGHTEMP =
      "SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight >= :weight"
       + " AND temperature >= :temperature LIMIT 1";
  /**
   * This query string finds results for greater than or equal altitude,
   * greater than or equal weight, less than or equal temperature,
   * order by temperature descending and limits the results to 1.
   */
  public static final String HIGHALT_HIGHWEIGHT_LOWTEMP =
      "SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight >= :weight"
      + " AND temperature <= :temperature ORDER BY temperature DESC LIMIT 1";
  /**
   * This query string finds results for greater than or equal altitude,
   * less than or equal weight, greater than or equal temperature,
   * order by weight descending and limits the results to 1.
   */
  public static final String HIGHALT_LOWWEIGHT_HIGHTEMP =
      "SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight <= :weight"
       + " AND temperature >= :temperature ORDER BY weight DESC LIMIT 1";
  /**
   * This query string finds results for greater than or equal altitude,
   * less than or equal weight, less than or equal temperature,
   * order by temperature and weight descending and limits the results to 1.
   */
  public static final String HIGHALT_LOWWEIGHT_LOWTEMP =
      "SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight <= :weight"
       + " AND temperature <= :temperature ORDER BY weight DESC, temperature DESC LIMIT 1";
  /**
   * This query string finds results for less than or equal altitude,
   * greater than or equal weight, greater than or equal temperature,
   * order by altitude descending, weight and temperature ascending,
   * and limits the results to 1.
   */
  public static final String LOWALT_HIGHWEIGHT_HIGHTEMP =
      "SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight >= :weight"
      + " AND temperature >= :temperature ORDER BY altitude DESC, weight, temperature LIMIT 1";
  /**
   * This query string finds results for less than or equal altitude,
   * greater than or equal weight, less than or equal temperature,
   * order by temperature and altitude descending, and limits the results to 1.
   */
  public static final String LOWALT_HIGHWEIGHT_LOWTEMP =
      "SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight >= :weight"
      + " AND temperature <= :temperature ORDER BY temperature DESC, altitude DESC LIMIT 1";
  /**
   * This query string finds results for less than or equal altitude,
   * less than or equal weight, greater than or equal temperature,
   * order by altitude and temperature descending, and limits the results to 1.
   */
  public static final String LOWALT_LOWWEIGHT_HIGHTEMP =
      "SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight <= :weight"
      + " AND temperature >= :temperature ORDER BY altitude DESC, temperature LIMIT 1";
  /**
   * This query string finds results for less than or equal altitude,
   * less than or equal weight, less than or equal temperature,
   * order by altitude and temperature descending, and limits the results to 1.
   */
  public static final String LOWALT_LOWWEIGHT_LOWTEMP =
      "SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight <= :weight"
      + " AND temperature <= :temperature ORDER BY altitude DESC, temperature DESC LIMIT 1";

  /**
   * This query string finds results for greater than or equal altitude,
   * greater than or equal temperature, and limits the results to 1.
   */
  public static final String HIGHALT_HIGHTEMP =
      "SELECT * FROM takeoffPowerN1 WHERE altitude >= :altitude"
      + " AND temperature >= :temperature LIMIT 1";
  /**
   * This query string finds results for greater than or equal altitude,
   * less than or equal temperature, order by temperature descending,
   * altitude ascending, and limits the results to 1.
   */
  public static final String HIGHALT_LOWTEMP =
      "SELECT * FROM takeoffPowerN1 WHERE altitude >= :altitude"
      + " AND temperature <= :temperature ORDER BY temperature DESC, altitude LIMIT 1";
  /**
   * This query string finds results for less than or equal altitude,
   * greater than or equal temperature, order by temperature ascending,
   * altitude descending, and limits the results to 1.
   */
  public static final String LOWALT_HIGHTEMP =
      "SELECT * FROM takeoffPowerN1 WHERE altitude <= :altitude"
      + " AND temperature >= :temperature ORDER BY temperature, altitude DESC LIMIT 1";
  /**
   * This query string finds results for less than or equal altitude,
   * less than or equal temperature, order by temperature descending,
   * altitude descending, and limits the results to 1.
   */
  public static final String LOWALT_LOWTEMP =
      "SELECT * FROM takeoffPowerN1 WHERE altitude <= :altitude"
      + " AND temperature <= :temperature ORDER BY temperature DESC, altitude DESC LIMIT 1";
}
