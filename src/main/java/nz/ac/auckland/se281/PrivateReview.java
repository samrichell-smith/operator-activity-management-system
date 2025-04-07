package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.ReviewType;

public class PrivateReview extends Review {
  // includes: email, follow-up request, operator response on request, we have a method to resolve
  // the review

  private String email;
  private boolean followUpRequested = false;

  public PrivateReview(
      String reviewerName,
      int rating,
      String reviewText,
      String email,
      boolean followUpRequested,
      Activity parentActivity) {
    super(reviewerName, rating, reviewText, false, false, parentActivity);
    setReviewType(ReviewType.PRIVATE);
    this.followUpRequested = followUpRequested;
    this.email = email;
  }

  public boolean getFollowUpRequested() {
    return followUpRequested;
  }

  public String getEmail() {
    return email;
  }
}
