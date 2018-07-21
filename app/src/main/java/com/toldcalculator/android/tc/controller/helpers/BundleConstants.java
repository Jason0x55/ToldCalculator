package com.toldcalculator.android.tc.controller.helpers;

/**
 * This class contains constants that are used for keys to pass information between fragments using
 * bundles.
 * Probably not the best way to do this but this will do for now.
 */
public class BundleConstants {

  private BundleConstants() { }

  /**
   * Key for the users User Name.
   */
  public static final String USER_ID_KEY = "USER_NAME";
  /**
   * Key for airport identifier.
   */
  public static final String AIRPORT_IDENT_KEY = "ICAO_IDENT";
  /**
   * Key for aircraft profile name.
   */
  public static final String AIRCRAFT_NAME_KEY = "AIRCRAFT_NAME";
  /**
   * Key for the current aircraft weight.
   */
  public static final String AIRCRAFT_WEIGHT_KEY = "AIRCRAFT_WT";
  /**
   * Key for saved data id.
   */
  public static final String SAVED_ID_KEY = "SAVED_ID";

}
