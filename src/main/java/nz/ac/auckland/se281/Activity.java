package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.ActivityType;

public class Activity {

  private ActivityType type;
  private String name;
  private Operator parentOp;
  private String id;
  private ArrayList<Review> reviews;

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
  }

  public ArrayList<Review> getReviews() {
    return reviews;
  }
}
