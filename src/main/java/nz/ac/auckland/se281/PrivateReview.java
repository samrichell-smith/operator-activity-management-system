package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.ReviewType;

public class PrivateReview extends Review {
  // includes: email, follow-up request, operator response on request, we have a method to resolve
  // the review

  private boolean resolved;
  private String email;
  private boolean followUpRequested;

  public PrivateReview(
      String reviewerName,
      int rating,
      String reviewText,
      String email,
      boolean followUpRequested,
      Activity parentActivity) {
    super(reviewerName, rating, reviewText, true, false, parentActivity);
    setReviewType(ReviewType.PRIVATE);
  }
}
