package com.toldcalculator.android.tc.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * This class is used for sky condition element. A maximum of four sky conditions can be returned
 * from a request.
 */
@Root(name = "sky_condition")
public class SkyCondition {

  @Attribute(name = "sky_cover")
  private String skyCover;

  @Attribute(name = "cloud_base_ft_agl", required = false)
  private String cloudBase;

  /**
   * Returns a string containing sky coverage information.
   * @return string containing sky coverage.
   */
  public String getSkyCover() {
    return skyCover;
  }
  /**
   * Sets the sky coverage.
   * @param skyCover sky coverage.
   */
  public void setSkyCover(String skyCover) {
    this.skyCover = skyCover;
  }
  /**
   * Returns a string containing cloud base information.
   * @return a string containing cloud base information.
   */
  public String getCloudBase() {
    return cloudBase;
  }
  /**
   * Sets clould base information.
   * @param cloudBase clould base information.
   */
  public void setCloudBase(String cloudBase) {
    this.cloudBase = cloudBase;
  }

}