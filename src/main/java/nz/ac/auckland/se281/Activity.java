package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.ActivityType;
import nz.ac.auckland.se281.Types.ReviewType;

public class Activity {

  private ActivityType type;
  private String name;
  private Operator parentOp;
  private String id;
  private ArrayList<Review> reviews;
  private double rating;

  public Activity(ActivityType type, String name, Operator parentOp, String id) {
    this.type = type;
    this.name = name;
    this.parentOp = parentOp;
    this.id = id;
    this.reviews = new ArrayList<>();
  }

  public ActivityType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public Operator getParentOp() {
    return parentOp;
  }

  public String getId() {
    return id;
  }

  public void addReview(Review review) {
    reviews.add(review);

    // sets the new average rating
    rating = calcRating();
  }

  public double getRating() {
    return rating;
  }

  private double calcRating() {
    // finds the average rating of activities

    double totalRating = 0;
    int rCount = 0;
    for (Review r : reviews) {
      if (r.getReviewType() != ReviewType.PRIVATE) {
        totalRating += r.getRating();
        rCount += 1;
      }
    }

    return (totalRating / rCount);
  }

  public ArrayList<Review> getReviews() {
    return reviews;
  }
}
