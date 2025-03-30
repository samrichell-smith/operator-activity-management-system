package nz.ac.auckland.se281;

import java.util.ArrayList;
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

      Operator newOperator = new Operator(locationFound, operatorName, operatorInfoString);

      operatorList.add(newOperator);
    } else if (duplicate == true) {
      MessageCli.OPERATOR_NOT_CREATED_ALREADY_EXISTS_SAME_LOCATION.printMessage(
          operatorName, locationAsString);
    }
  }

  public void viewActivities(String operatorId) {
    // TODO implement
  }

  public void createActivity(String activityName, String activityType, String operatorId) {
    // TODO implement
  }

  public void searchActivities(String keyword) {
    // TODO implement
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
