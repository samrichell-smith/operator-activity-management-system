package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.ReviewType;

public class PublicReview extends Review {

  private boolean endorsement = false;

  public PublicReview(
      String reviewerName,
      int rating,
      String reviewText,
      boolean anonymity,
      boolean visibility,
      Activity parentActivity) {

    super(reviewerName, rating, reviewText, anonymity, visibility, parentActivity);
    setReviewType(ReviewType.PUBLIC);
  }

  public boolean getEndorsement() {
    return endorsement;
  }

  public void setEndorsement(boolean endorsement) {
    this.endorsement = endorsement;
  }
}
