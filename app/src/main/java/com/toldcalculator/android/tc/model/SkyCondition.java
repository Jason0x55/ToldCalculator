package com.toldcalculator.android.tc.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "sky_condition")
public class SkyCondition {

  @Attribute(name = "sky_cover")
  private String skyCover;

  @Attribute(name = "cloud_base_ft_agl")
  private String cloudBase;

  public String getSkyCover() {
    return skyCover;
  }

  public void setSkyCover(String skyCover) {
    this.skyCover = skyCover;
  }

  public String getCloudBase() {
    return cloudBase;
  }

  public void setCloudBase(String cloudBase) {
    this.cloudBase = cloudBase;
  }

}