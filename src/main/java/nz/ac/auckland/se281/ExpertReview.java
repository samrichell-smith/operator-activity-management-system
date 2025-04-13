package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.ReviewType;

public class ExpertReview extends Review {
  // includes: reccomendation, image upload

  private boolean reccomended;

  private ArrayList<String> images;

  public ExpertReview(
      String reviewerName,
      int rating,
      String reviewText,
      boolean reccomnded,
      Activity parentActivity) {

    super(reviewerName, rating, reviewText, false, true, parentActivity);
    setReviewType(ReviewType.EXPERT);
    this.reccomended = reccomnded;
    this.images = new ArrayList<>();
  }

  public void uploadImage(String imageName) {
    this.images.add(imageName);
  }

  public ArrayList<String> getImages() {
    if (images == null) {
      return null;
    }
    return this.images;
  }

  public boolean getReccomended() {
    return reccomended;
  }

  @Override
  public String getReviewerName() {

    // No need to check anonymity so can override parent method for (marginal) efficiency,
    // demonstrating polymorphism
    return super.getReviewerName();
  }
}
