package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.ReviewType;

abstract class Review {
  // needs: Reviewer Name, rating, comments, anonymity, visibility,

  private String reviewerName;
  private int rating;
  private String reviewText;
  private boolean anonymity;
  private boolean visibility;
  private ReviewType reviewType;
  private Activity parentActivity;
  private String reviewId;

  public Review(
      String reviewerName,
      int rating,
      String reviewText,
      boolean anonymity,
      boolean visibility,
      Activity parentActivity) {
    this.reviewerName = reviewerName;
    this.rating = rating;
    this.reviewText = reviewText;
    this.anonymity = anonymity;
    this.visibility = visibility;
    this.parentActivity = parentActivity;

    int reviewNum = 1;
    for (Review review : parentActivity.getReviews()) {

      reviewNum++;
    }

    this.reviewId = parentActivity.getId() + "-R" + reviewNum;
  }

  public String getReviewerName() {
    return reviewerName;
  }

  public void setReviewType(ReviewType reviewType) {
    this.reviewType = reviewType;
  }

  public int getRating() {
    return rating;
  }

  public String getReviewText() {
    return reviewText;
  }

  public boolean isAnonymity() {
    return anonymity;
  }

  public boolean isVisibility() {
    return visibility;
  }

  public ReviewType getReviewType() {
    return reviewType;
  }

  public Activity getParentActivity() {
    return parentActivity;
  }

  public String getReviewId() {
    return reviewId;
  }

  // add getters and setters as needed
}
