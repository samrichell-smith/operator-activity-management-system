package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.ActivityType;
import nz.ac.auckland.se281.Types.Location;
import nz.ac.auckland.se281.Types.ReviewType;

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

  public Activity matchActivity(String activityId) {
    for (Operator op : operatorList) {
      ArrayList<Activity> activities = op.getActivities();
      for (Activity activity : activities) {
        if (activity.getId().equalsIgnoreCase(activityId)) {
          return activity;
        }
      }
    }

    return null;
  }

  public Review matchReview(String reviewId) {
    for (Operator op : operatorList) {
      for (Activity activity : op.getActivities()) {
        for (Review review : activity.getReviews()) {
          if (review.getReviewId().equals(reviewId)) {
            return review;
          }
        }
      }
    }
    return null;
  }

  public void viewActivities(String operatorId) {
    // checks if operator Id is valid
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
      // Iterates over multiple activities found and prints the entry
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

    activityName = activityName.trim();

    int activityNum = 1;

    Operator operatorMatch = null;

    operatorMatch = matchOperator(operatorId);

    if (operatorMatch == null) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_OPERATOR_ID.printMessage(operatorId);

      return;
    }

    for (Activity activitiy : operatorMatch.getActivities()) {

      activityNum += 1;
    }

    // I want to format activity num so its like 001, 3 digits
    String formattedActivityNum = String.format("%03d", activityNum);

    String activityId = operatorId + "-" + formattedActivityNum;

    Activity activity = new Activity(activityTypeObj, activityName, operatorMatch, activityId);
    operatorMatch.addActivity(activity);

    MessageCli.ACTIVITY_CREATED.printMessage(
        activityName, activityId, activity.getType().toString(), operatorMatch.getCompanyName());
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
            activity.getParentOp().getCompanyName());
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
            activity.getParentOp().getCompanyName());
      }
    }
  }

  public void addPublicReview(String activityId, String[] options) {

    // no matching activity found
    Activity activityFound = matchActivity(activityId);
    if (activityFound == null) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    // converting input string array into arguments

    String reviewerName = options[0];
    boolean anonymity = false;
    if (options[1].equals("n")) {
      anonymity = false;
    } else if (options[1].equals("y")) {
      anonymity = true;
    }

    int rating = Integer.valueOf(options[2]);
    if (rating > 5) {
      rating = 5;
    } else if (rating < 1) {
      rating = 1;
    }

    String reviewText = options[3];

    PublicReview reviewCreated =
        new PublicReview(reviewerName, rating, reviewText, anonymity, true, activityFound);

    activityFound.addReview(reviewCreated);

    MessageCli.REVIEW_ADDED.printMessage(
        reviewCreated.getReviewType().toString(),
        reviewCreated.getReviewId(),
        reviewCreated.getParentActivity().getName());
  }

  public void addPrivateReview(String activityId, String[] options) {
    // no matching activity found
    Activity activityFound = matchActivity(activityId);
    if (activityFound == null) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    // converting input string array into arguments

    String reviewerName = options[0];
    String email = options[1];

    int rating = Integer.valueOf(options[2]);
    if (rating > 5) {
      rating = 5;
    } else if (rating < 1) {
      rating = 1;
    }

    String reviewText = options[3];
    boolean followUpRequested = false;
    if (options[4].equals("n")) {
      followUpRequested = false;
    } else if (options[4].equals("y")) {
      followUpRequested = true;
    }

    PrivateReview reviewCreated =
        new PrivateReview(
            reviewerName, rating, reviewText, email, followUpRequested, activityFound);

    activityFound.addReview(reviewCreated);

    MessageCli.REVIEW_ADDED.printMessage(
        reviewCreated.getReviewType().toString(),
        reviewCreated.getReviewId(),
        reviewCreated.getParentActivity().getName());
  }

  public void addExpertReview(String activityId, String[] options) {
    // no matching activity found
    Activity activityFound = matchActivity(activityId);
    if (activityFound == null) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    // converting input string array into arguments

    String reviewerName = options[0];

    int rating = Integer.valueOf(options[1]);
    if (rating > 5) {
      rating = 5;
    } else if (rating < 1) {
      rating = 1;
    }

    String reviewText = options[2];
    boolean reccomended = false;
    if (options[3].equals("n")) {
      reccomended = false;
    } else if (options[3].equals("y")) {
      reccomended = true;
    }

    ExpertReview reviewCreated =
        new ExpertReview(reviewerName, rating, reviewText, reccomended, activityFound);

    activityFound.addReview(reviewCreated);

    MessageCli.REVIEW_ADDED.printMessage(
        reviewCreated.getReviewType().toString(),
        reviewCreated.getReviewId(),
        reviewCreated.getParentActivity().getName());
  }

  public void displayReviews(String activityId) {
    Activity activityMatch = matchActivity(activityId);

    if (activityMatch == null) {
      MessageCli.ACTIVITY_NOT_FOUND.printMessage(activityId);
      return;
    }
    ArrayList<Review> reviews = activityMatch.getReviews();

    // prints header
    if (reviews.size() == 0) {
      MessageCli.REVIEWS_FOUND.printMessage("are", "no", "s", activityMatch.getName());
    } else if (reviews.size() == 1) {
      MessageCli.REVIEWS_FOUND.printMessage("is", "" + reviews.size(), "", activityMatch.getName());
    } else {
      MessageCli.REVIEWS_FOUND.printMessage(
          "are", "" + reviews.size(), "s", activityMatch.getName());
    }

    // iterates through each review and per its type prints the correct output
    for (Review review : reviews) {
      if (review.getReviewType() == ReviewType.PUBLIC) {

        MessageCli.REVIEW_ENTRY_HEADER.printMessage(
            String.valueOf(review.getRating()),
            "5",
            review.getReviewType().toString(),
            review.getReviewId(),
            review.getReviewerName());

        MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(review.getReviewText());

        if (((PublicReview) review).getEndorsement()) {
          MessageCli.REVIEW_ENTRY_ENDORSED.printMessage();
        }
      } else if (review.getReviewType() == ReviewType.PRIVATE) {

        MessageCli.REVIEW_ENTRY_HEADER.printMessage(
            String.valueOf(review.getRating()),
            "5",
            review.getReviewType().toString(),
            review.getReviewId(),
            review.getReviewerName());

        MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(review.getReviewText());

        PrivateReview castReview = (PrivateReview) review;
        // checks type of review and casts to it so subclass methods can be called

        if (castReview.getFollowUpRequested()) {
          MessageCli.REVIEW_ENTRY_FOLLOW_UP.printMessage(castReview.getEmail());
        } else if (castReview.getResolved()) {
          MessageCli.REVIEW_ENTRY_RESOLVED.printMessage(castReview.getResolvedMessage());

        } else {
          MessageCli.REVIEW_ENTRY_RESOLVED.printMessage("-");
        }
      } else if (review.getReviewType() == ReviewType.EXPERT) {
        MessageCli.REVIEW_ENTRY_HEADER.printMessage(
            String.valueOf(review.getRating()),
            "5",
            review.getReviewType().toString(),
            review.getReviewId(),
            review.getReviewerName());

        MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(review.getReviewText());
        ExpertReview castReview = ((ExpertReview) review);
        if (castReview.getReccomended()) {
          MessageCli.REVIEW_ENTRY_RECOMMENDED.printMessage();
        }

        if (castReview.getImages() != null) {
          String[] imgsToPrint = new String[castReview.getImages().size()];
          for (int i = 0; i < castReview.getImages().size(); i++) {
            imgsToPrint[i] = castReview.getImages().get(i);
          }

          String imgPrintString = String.join(",", imgsToPrint);

          MessageCli.REVIEW_ENTRY_IMAGES.printMessage(imgPrintString);
        }
      }
    }
  }

  public void endorseReview(String reviewId) {
    Review reviewMatch = matchReview(reviewId);

    // checks we have a mathcing review
    if (reviewMatch == null) {
      MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
      return;
    }

    if (reviewMatch.getReviewType() != ReviewType.PUBLIC) {
      MessageCli.REVIEW_NOT_ENDORSED.printMessage(reviewId);
      return;
    }

    PublicReview castReview = (PublicReview) reviewMatch;

    castReview.setEndorsement(true);

    MessageCli.REVIEW_ENDORSED.printMessage(reviewId);
  }

  public void resolveReview(String reviewId, String response) {
    Review reviewFound = matchReview(reviewId);
    if (reviewFound == null) {
      MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
      return;
    }

    // verifies if it is a private review we want to resolve
    if (reviewFound.getReviewType() != ReviewType.PRIVATE) {
      MessageCli.REVIEW_NOT_RESOLVED.printMessage(reviewId);
      return;
    }
    PrivateReview privReview = ((PrivateReview) reviewFound);
    privReview.resolveReview(response);
    MessageCli.REVIEW_RESOLVED.printMessage(reviewId);
  }

  public void uploadReviewImage(String reviewId, String imageName) {
    Review reviewFound = matchReview(reviewId);
    if (reviewFound == null) {
      MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
      return;
    }

    // verifies it is an expert review
    if (reviewFound.getReviewType() != ReviewType.EXPERT) {
      MessageCli.REVIEW_IMAGE_NOT_ADDED_NOT_EXPERT.printMessage(reviewId);
      return;
    }

    ExpertReview castReview = (ExpertReview) reviewFound;

    castReview.uploadImage(imageName);
    MessageCli.REVIEW_IMAGE_ADDED.printMessage(
        castReview.getImages().get(castReview.getImages().size() - 1), castReview.getReviewId());
  }

  public void displayTopActivities() {

    // for each location, find operators in the location then find the highest rated activity and
    // print it
    for (Location l : Types.Location.values()) {
      Activity currTopRated = null;

      for (Operator op : operatorList) {
        if (op.getLocation() == l) {
          for (Activity a : op.getActivities()) {
            if (Double.isNaN(a.getRating())) {
              continue;
            }
            if (currTopRated == null) {
              currTopRated = a;
            }

            if (a.getRating() > currTopRated.getRating()) {
              currTopRated = a;
            }
          }
        }
      }

      if (currTopRated == null || currTopRated.getRating() == 0) {
        MessageCli.NO_REVIEWED_ACTIVITIES.printMessage(l.getFullName());

      } else {
        String formattedRating = String.format("%.1f", currTopRated.getRating());
        MessageCli.TOP_ACTIVITY.printMessage(
            l.getFullName(), currTopRated.getName(), formattedRating);
      }
    }
  }
}
