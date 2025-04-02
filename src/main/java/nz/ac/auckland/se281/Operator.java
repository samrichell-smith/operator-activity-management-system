package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.Location;

public class Operator {

  private Location location;

  private String companyName;

  private String opName;

  private String opCode;

  private ArrayList<Activity> activities;

  public Operator(Location inputLocation, String companyName, String opName, String opCode) {
    this.companyName = companyName;
    this.location = inputLocation;
    this.opName = opName;
    this.opCode = opCode;
    this.activities = new ArrayList<>();
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

  public String getOpCode() {
    return opCode;
  }

  public void addActivity(Activity activity) {
    activities.add(activity);
  }

  public ArrayList<Activity> getActivities() {
    return activities;
  }
}
