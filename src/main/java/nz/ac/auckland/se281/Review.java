package nz.ac.auckland.se281;

abstract class Review {
  // needs: Reviewer Name, rating, comments, anonymity, visibility,

  private String reviewerName;
  private int rating;
  private String reviewText;
  private boolean anonymity;
  private boolean visibility;

  public Review(
      String reviewerName, int rating, String reviewText, boolean anonymity, boolean visibility) {
    this.reviewerName = reviewerName;
    this.rating = rating;
    this.reviewText = reviewText;
    this.anonymity = anonymity;
    this.visibility = visibility;
  }

  // add getters and setters as needed
}
