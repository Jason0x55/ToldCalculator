package com.toldcalculator.android.tc.model.dao;

public class Queries {
  public static final String HIGHALT_HIGHWEIGHT_HIGHTEMP =
      "SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight >= :weight"
       + " AND temperature >= :temperature LIMIT 1";

  public static final String HIGHALT_HIGHWEIGHT_LOWTEMP =
      "SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight >= :weight"
      + " AND temperature <= :temperature ORDER BY temperature DESC LIMIT 1";

  public static final String HIGHALT_LOWWEIGHT_HIGHTEMP =
      "SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight <= :weight"
       + " AND temperature >= :temperature ORDER BY weight DESC LIMIT 1";

  public static final String HIGHALT_LOWWEIGHT_LOWTEMP =
      "SELECT * FROM takeOffData WHERE altitude >= :altitude AND weight <= :weight"
       + " AND temperature <= :temperature ORDER BY weight DESC, temperature DESC LIMIT 1";

  public static final String LOWALT_HIGHWEIGHT_HIGHTEMP =
      "SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight >= :weight"
      + " AND temperature >= :temperature ORDER BY altitude DESC, weight, temperature LIMIT 1";

  public static final String LOWALT_HIGHWEIGHT_LOWTEMP =
      "SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight >= :weight"
      + " AND temperature <= :temperature ORDER BY temperature DESC, altitude DESC LIMIT 1";

  public static final String LOWALT_LOWWEIGHT_HIGHTEMP =
      "SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight <= :weight"
      + " AND temperature >= :temperature ORDER BY altitude DESC, temperature LIMIT 1";

  public static final String LOWALT_LOWWEIGHT_LOWTEMP =
      "SELECT * FROM takeOffData WHERE altitude <= :altitude AND weight <= :weight"
      + " AND temperature <= :temperature ORDER BY altitude DESC, temperature DESC LIMIT 1";


  public static final String HIGHALT_HIGHTEMP =
      "SELECT * FROM takeoffPowerN1 WHERE altitude >= :altitude"
      + " AND temperature >= :temperature LIMIT 1";

  public static final String HIGHALT_LOWTEMP =
      "SELECT * FROM takeoffPowerN1 WHERE altitude >= :altitude"
      + " AND temperature <= :temperature ORDER BY temperature DESC, altitude LIMIT 1";

  public static final String LOWALT_HIGHTEMP =
      "SELECT * FROM takeoffPowerN1 WHERE altitude <= :altitude"
      + " AND temperature >= :temperature ORDER BY temperature, altitude DESC LIMIT 1";

  public static final String LOWALT_LOWTEMP =
      "SELECT * FROM takeoffPowerN1 WHERE altitude <= :altitude"
      + " AND temperature <= :temperature ORDER BY temperature DESC, altitude DESC LIMIT 1";

  // HIGHALT_HIGHWEIGHT_HIGHTEMP
  // HIGHALT_HIGHWEIGHT_LOWTEMP
  // HIGHALT_LOWWEIGHT_HIGHTEMP
  // HIGHALT_LOWWEIGHT_LOWTEMP

  // LOWALT_HIGHWEIGHT_HIGHTEMP
  // LOWALT_HIGHWEIGHT_LOWTEMP
  // LOWALT_LOWWEIGHT_HIGHTEMP
  // LOWALT_LOWWEIGHT_LOWTEMP

  // HIGHALT_HIGHTEMP
  // HIGHALT_LOWTEMP
  // LOWALT_HIGHTEMP
  // LOWALT_LOWTEMP






}
