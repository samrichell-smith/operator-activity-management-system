package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.ActivityType;

public class Activity {

  private ActivityType type;
  private String name;
  private String opID;
  private String id;

  public Activity(ActivityType type, String name, String operatorID, String id) {
    this.type = type;
    this.name = name;
    this.opID = operatorID;
    this.id = id;
  }
}
