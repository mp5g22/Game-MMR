/**
 * Stores a rating.
 */
public class Rateable {

  //Used when asking the user to choose a rateable
  private final String name;
  //How good the rateable is, used for determining the handicap.
  private double rating;
  //Used for deciding how significantly to change the rating.
  private int playedGames;
  //True if the rateable is a person, false if the rateable is part of a game.
  private final boolean isPerson;

  public Rateable(String name, double rating, boolean isPerson) {
    this.name = name;
    this.rating = rating;
    this.playedGames = 0;
    this.isPerson = isPerson;
  }

  public Rateable(String name, double rating, int playedGames, boolean isPerson) {
    this.name = name;
    this.rating = rating;
    this.playedGames = playedGames;
    this.isPerson = isPerson;
  }

  public String getName() {
    return name;
  }

  public double getRating() {
    return rating;
  }

  public int getPlayedGames() {
    return playedGames;
  }

  public boolean isPerson() {
    return isPerson;
  }

  /**
   * Adjusts the rating of the rateable up or down based on whether the player won or lost, changes more significantly
   * the fewer games the rateable has played.
   *
   * @param didWin Whether the rateable won the game.
   */
  public void changeRating(boolean didWin) {
    double ratingChange;

    //The change in rating for the first game.
    double initialChange = 5;
    //The rate at which the change in rating changes, higher numbers mean a slower reduction in rating change.
    double rateOfChange = 2.34;
    ratingChange = initialChange - (Math.log(playedGames) / Math.log(rateOfChange));

    if (ratingChange <= 1) {
      ratingChange = 1;
    }

    if (!didWin) {
      ratingChange = ratingChange * -1;
    }

    rating += ratingChange;
    incrementPlayedGames();
  }

  public void incrementPlayedGames() {
    playedGames++;
  }
}
