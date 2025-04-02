package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.ActivityType;
import nz.ac.auckland.se281.Types.Location;

public class OperatorManagementSystem {

  private ArrayList<Operator> operatorList;

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {
    operatorList = new ArrayList<>();
  }

  public void searchOperators(String keyword) {

    if (keyword.trim().equals("*")) {

      Boolean plural;
      if (operatorList.size() == 1) {
        plural = false;
      } else {
        plural = true;
      }
      if (operatorList.size() == 0) {
        MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
      } else if (plural) {
        MessageCli.OPERATORS_FOUND.printMessage("are", "" + operatorList.size(), "s", ":");
      } else {
        MessageCli.OPERATORS_FOUND.printMessage("is", "" + operatorList.size(), "", ":");
      }

      for (Operator op : operatorList) {
        MessageCli.OPERATOR_ENTRY.printMessage(
            op.getCompanyName(), op.getOpName(), op.getLocation().getFullName());
      }
    } else {

      ArrayList<Operator> matches = new ArrayList<>();

      String cleanedKeyword = keyword.trim().toLowerCase();

      for (Operator op : operatorList) {
        if (op.getCompanyName().toLowerCase().contains(cleanedKeyword)
            || op.getLocation().getFullName().toLowerCase().contains(cleanedKeyword)
            || op.getOpName().toLowerCase().contains(cleanedKeyword)) {

          matches.add(op);
        }
      }

      // Prints num of operators found
      Boolean plural;
      if (matches.size() == 1) {
        plural = false;
      } else {
        plural = true;
      }
      if (matches.size() == 0) {
        MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
      } else if (plural) {
        MessageCli.OPERATORS_FOUND.printMessage("are", "" + matches.size(), "s", ":");
      } else {
        MessageCli.OPERATORS_FOUND.printMessage("is", "" + matches.size(), "", ":");
      }

      for (Operator match : matches) {
        MessageCli.OPERATOR_ENTRY.printMessage(
            match.getCompanyName(), match.getOpName(), match.getLocation().getFullName());
      }

      // trim keyword, set all lowercase, iterate through the operators and if keyword matches
      // a case insensitive substring of the name or location, increment found count by one and
      // add to new list to iterate through and print?

    }
  }

  public void createOperator(String operatorName, String location) {

    Location locationFound = Location.fromString(location);

    if (locationFound == null) {

      MessageCli.OPERATOR_NOT_CREATED_INVALID_LOCATION.printMessage(location);
      return;
    }

    if (operatorName.trim().length() < 3) {
      MessageCli.OPERATOR_NOT_CREATED_INVALID_OPERATOR_NAME.printMessage(operatorName);
      return;
    }

    String locationAsString = locationFound.getFullName();

    String[] splitWords = operatorName.split(" ");

    ArrayList<String> initials = new ArrayList<>();

    // Adds initials then joins them
    for (String string : splitWords) {
      string = string.substring(0, 1).toUpperCase();
      initials.add(string);
    }

    String operatorAbbreviation = String.join("", initials);

    // Finds, sets and formats operator number
    // if working with large datasets this wont scale well, as it searches the whole list for each
    // operator created, a dictionary of counts or sub arraylists per location would scale better
    boolean duplicate = false;

    int operatorNumber = 1;

    for (Operator op : operatorList) {
      // can compare since location is an enum
      if (op.getLocation() == locationFound) {
        operatorNumber += 1;
        if (op.getCompanyName().equals(operatorName)) {
          duplicate = true;
          break;
        }
      }
    }

    String formattedOperatorNumber = String.format("%03d", operatorNumber);

    String operatorInfoString =
        operatorAbbreviation
            + "-"
            + locationFound.getLocationAbbreviation()
            + "-"
            + formattedOperatorNumber;

    // if identical operator already exists we throw an error
    if (duplicate == false) {
      MessageCli.OPERATOR_CREATED.printMessage(operatorName, operatorInfoString, locationAsString);

      Operator newOperator =
          new Operator(locationFound, operatorName, operatorInfoString, operatorInfoString);

      operatorList.add(newOperator);
    } else if (duplicate == true) {
      MessageCli.OPERATOR_NOT_CREATED_ALREADY_EXISTS_SAME_LOCATION.printMessage(
          operatorName, locationAsString);
    }
  }

  public Operator matchOperator(String operatorId) {
    for (Operator op : operatorList) {
      if (op.getOpCode().equalsIgnoreCase(operatorId)) {
        return op;
      }
    }
    return null;
  }

  public void viewActivities(String operatorId) {
    Operator operatorMatch = matchOperator(operatorId);
    if (operatorMatch == null) {

      MessageCli.OPERATOR_NOT_FOUND.printMessage(operatorId);

      return;
    }

    ArrayList<Activity> activities = operatorMatch.getActivities();
    if (activities.size() == 0) {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
    } else if (activities.size() == 1) {
      MessageCli.ACTIVITIES_FOUND.printMessage("is", "" + activities.size(), "y", ":");
      MessageCli.ACTIVITY_ENTRY.printMessage(
          activities.get(0).getName(),
          activities.get(0).getId(),
          activities.get(0).getType().toString(),
          operatorMatch.getCompanyName());
    } else {
      MessageCli.ACTIVITIES_FOUND.printMessage("are", "" + activities.size(), "ies", ":");
      for (Activity activity : activities) {
        MessageCli.ACTIVITY_ENTRY.printMessage(
            activity.getName(),
            activity.getId(),
            activity.getType().toString(),
            operatorMatch.getCompanyName());
      }
    }
  }

  public void createActivity(String activityName, String activityType, String operatorId) {

    ActivityType activityTypeObj = ActivityType.fromString(activityType);

    if (activityName.trim().length() < 3) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_ACTIVITY_NAME.printMessage(activityName);
      return;
    }

    int activityNum = 1;

    ArrayList<Activity> matches = new ArrayList<>();
    Operator operatorMatch = null;

    operatorMatch = matchOperator(operatorId);

    if (operatorMatch == null) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_OPERATOR_ID.printMessage(operatorId);

      return;
    }

    for (Activity activitiy : operatorMatch.getActivities()) {
      // TODO: Add these to list if needed or compare, for now we are just getting a count
      activityNum += 1;
    }

    // I want to format activity num so its like 001, 3 digits
    String formattedActivityNum = String.format("%03d", activityNum);

    String activityID = operatorId + "-" + formattedActivityNum;

    Activity activity = new Activity(activityTypeObj, activityName, operatorId, activityID);
    operatorMatch.addActivity(activity);

    MessageCli.ACTIVITY_CREATED.printMessage(
        activityName, activityID, activityType, operatorMatch.getCompanyName());
  }

  public void searchActivities(String keyword) {
    ArrayList<Activity> allActivities = new ArrayList<>();

    for (Operator op : operatorList) {
      ArrayList<Activity> activities = op.getActivities();
      for (Activity activity : activities) {
        allActivities.add(activity);
      }
    }
    if (keyword.trim().equals("*")) {
      Boolean plural;
      if (allActivities.size() == 1) {
        plural = false;
      } else {
        plural = true;
      }

      if (allActivities.size() == 0) {
        MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
      } else if (plural) {
        MessageCli.ACTIVITIES_FOUND.printMessage("are", "" + allActivities.size(), "ies", ":");

      } else {
        MessageCli.ACTIVITIES_FOUND.printMessage("is", "" + allActivities.size(), "", ":");
      }

      for (Activity activity : allActivities) {
        MessageCli.ACTIVITY_ENTRY.printMessage(
            activity.getName(),
            activity.getId(),
            activity.getType().toString(),
            activity.getOpID());
      }

    } else {
      // iterate through all operators and their associated activities, first checking if the
      // keyword is
      // in the operators location, including english, te reo or abbreviation, if found printing all
      // activities associated with that operator
      // if not, iterate through all of that operators activities and check if the keyword is in the
      // activity name or type
      // all case insensitive

      ArrayList<Activity> matches = new ArrayList<>();
      String cleanedKeyword = keyword.trim().toLowerCase();

      for (Operator op : operatorList) {
        ArrayList<Activity> activities = op.getActivities();
        if (op.getLocation().getFullName().toLowerCase().contains(cleanedKeyword)
            || op.getLocation().getLocationAbbreviation().toLowerCase().contains(cleanedKeyword)
            || op.getLocation().getNameTeReo().toLowerCase().contains(cleanedKeyword)) {

          for (Activity activity : activities) {
            matches.add(activity);
          }
        } else {

          for (Activity activity : activities) {
            if (activity.getName().toLowerCase().contains(cleanedKeyword)
                || activity.getType().toString().toLowerCase().contains(cleanedKeyword)) {
              matches.add(activity);
            }
          }
        }
      }

      if (matches.size() == 0) {
        MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
      } else if (matches.size() == 1) {
        MessageCli.ACTIVITIES_FOUND.printMessage("is", "" + matches.size(), "y", ":");

      } else {
        MessageCli.ACTIVITIES_FOUND.printMessage("are", "" + matches.size(), "ies", ":");
      }

      for (Activity activity : matches) {
        MessageCli.ACTIVITY_ENTRY.printMessage(
            activity.getName(),
            activity.getId(),
            activity.getType().toString(),
            activity.getOpID());
      }
    }
  }

  public void addPublicReview(String activityId, String[] options) {
    // TODO implement
  }

  public void addPrivateReview(String activityId, String[] options) {
    // TODO implement
  }

  public void addExpertReview(String activityId, String[] options) {
    // TODO implement
  }

  public void displayReviews(String activityId) {
    // TODO implement
  }

  public void endorseReview(String reviewId) {
    // TODO implement
  }

  public void resolveReview(String reviewId, String response) {
    // TODO implement
  }

  public void uploadReviewImage(String reviewId, String imageName) {
    // TODO implement
  }

  public void displayTopActivities() {
    // TODO implement
  }
}
