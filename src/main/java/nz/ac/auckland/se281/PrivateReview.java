package nz.ac.auckland.se281;

public class PrivateReview extends Review {
  // includes: email, follow-up request, operator response on request, we have a method to resolve
  // the review

  public PrivateReview(
      String reviewerName, int rating, String reviewText, boolean anonymity, boolean visibility) {
    super(reviewerName, rating, reviewText, anonymity, visibility);
  }
}
