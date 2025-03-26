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

    if (keyword.equals("*")) {

      Boolean plural;
      if (operatorList.size() == 1) {
        plural = false;
      } else {
        plural = true;
      }

      if (plural) {
        MessageCli.OPERATORS_FOUND.printMessage("are", "" + operatorList.size(), "s", ":");
      } else {
        MessageCli.OPERATORS_FOUND.printMessage("is", "" + operatorList.size(), "", ":");
      }

      for (Operator op : operatorList) {

        MessageCli.OPERATOR_ENTRY.printMessage(
            op.getCompanyName(), op.getOpName(), op.getLocation().getFullName());
      }

    } else {
      MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
    }
  }

  public void createOperator(String operatorName, String location) {

    Location locationFound = Location.fromString(location);

    String locationAsString = locationFound.getFullName();

    String[] splitWords = operatorName.split(" ");

    ArrayList<String> initials = new ArrayList<>();

    for (String string : splitWords) {
      string = string.substring(0, 1).toUpperCase();
      initials.add(string);
    }

    String operatorAbbreviation = String.join("", initials);

    int operatorNumber = 1;
    String formattedOperatorNumber = String.format("%03d", operatorNumber);

    // ('WACT-AKL-001')

    String operatorInfoString =
        operatorAbbreviation
            + "-"
            + locationFound.getLocationAbbreviation()
            + "-"
            + formattedOperatorNumber;

    Operator newOperator = new Operator(locationFound, operatorName, operatorInfoString);

    operatorList.add(newOperator);

    MessageCli.OPERATOR_CREATED.printMessage(operatorName, operatorInfoString, locationAsString);
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
