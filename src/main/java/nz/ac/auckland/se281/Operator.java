package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.Location;

public class Operator {

  private Location location;

  private String companyName;

  private String opName;

  public Operator(Location inputLocation, String companyName, String opName) {
    this.companyName = companyName;
    this.location = inputLocation;
    this.opName = opName;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getOpName() {
    return opName;
  }

  public Location getLocation() {
    return location;
  }
}
