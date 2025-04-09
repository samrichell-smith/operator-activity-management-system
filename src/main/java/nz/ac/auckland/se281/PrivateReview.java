package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.ReviewType;

public class PrivateReview extends Review {
  // includes: email, follow-up request, operator response on request, we have a method to resolve
  // the review

  private String email;
  private boolean followUpRequested = false;
  private String resolvedMessage;
  private boolean resolved;

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
    if (followUpRequested) {
      this.resolved = false;
    }
    this.email = email;
  }

  public boolean getFollowUpRequested() {
    return followUpRequested;
  }

  public boolean getResolved() {
    return resolved;
  }

  public void resolveReview(String message) {
    this.followUpRequested = false;
    this.resolvedMessage = message;
    this.resolved = true;
  }

  public String getResolvedMessage() {
    return this.resolvedMessage;
  }

  public String getEmail() {
    return email;
  }
}
