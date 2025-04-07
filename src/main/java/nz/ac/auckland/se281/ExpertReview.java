package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.ReviewType;

public class ExpertReview extends Review {
  // includes: reccomendation, image upload

  private boolean reccomended;
  private String imageName;

  public ExpertReview(
      String reviewerName,
      int rating,
      String reviewText,
      boolean reccomnded,
      Activity parentActivity) {

    super(reviewerName, rating, reviewText, false, true, parentActivity);
    setReviewType(ReviewType.EXPERT);
    this.reccomended = reccomnded;
  }

  public void UploadImage(String ImageName) {}

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
