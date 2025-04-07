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
  }

  public void UploadImage(String ImageName) {}
}
