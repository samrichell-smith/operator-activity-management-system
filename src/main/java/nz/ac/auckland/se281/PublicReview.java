package nz.ac.auckland.se281;

public class PublicReview extends Review {
  // includes endorsement

  private boolean endorsement = false;

  public PublicReview(
      String reviewerName, int rating, String reviewText, boolean anonymity, boolean visibility) {

    super(reviewerName, rating, reviewText, anonymity, visibility);
  }

  public boolean getEndorsement() {
    return endorsement;
  }

  public void setEndorsement(boolean endorsement) {
    this.endorsement = endorsement;
  }
}
