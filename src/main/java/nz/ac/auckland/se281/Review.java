package nz.ac.auckland.se281;


abstract class Review {
  // needs: Reviewer Name, rating, comments, anonymity, visibility,

  String reviewerName;
  int rating;
  String reviewText;
  boolean anonymity;
  boolean visibility;

  public Review(
      String reviewerName, int rating, String reviewText, boolean anonymity, boolean visibility) {
    this.reviewerName = reviewerName;
    this.rating = rating;
    this.reviewText = reviewText;
    this.anonymity = anonymity;
    this.visibility = visibility;
  }
}
