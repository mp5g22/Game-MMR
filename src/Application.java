import java.io.File;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Math.round;

public class Application {
  private static ArrayList<Rateable> people = new ArrayList<>();
  private static final File peopleFile = new File("C:\\Users\\mikey\\IdeaProjects\\Game MMR\\src\\people");
  private static ArrayList<Rateable> gameElements = new ArrayList<>();
  private static final File gameElementsFile = new File("C:\\Users\\mikey\\IdeaProjects\\Game MMR\\src\\gameElements");
  private static final boolean addingMode = true;

  public static void main(String[] Args) {
    try {
      Application application = new Application();
      RateableWriter rateableWriter = new RateableWriter();

      if (addingMode) {
        rateableWriter.addRateables(peopleFile, gameElementsFile);
      }

      application.getRateables();
      ArrayList<Rateable> chosenRateables = application.chooseRateables();
      application.displayHandicap(chosenRateables);

      application.enterResult(chosenRateables);

      rateableWriter.updateRateables(people, peopleFile);
      rateableWriter.updateRateables(gameElements, gameElementsFile);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(Arrays.toString(e.getStackTrace()));
    }

  }

  public void getRateables() throws Exception {
    RateableReader fileReader = new RateableReader();
    people = fileReader.readRateables(peopleFile, true);
    gameElements = fileReader.readRateables(gameElementsFile, false);
  }

  public ArrayList<Rateable> chooseRateables() throws Exception {
    ArrayList<Rateable> chosenRateables = new ArrayList<>();
    ArrayList<String> peopleNames = new ArrayList<>();
    ArrayList<String> gameElementNames = new ArrayList<>();

    for (Rateable person : people) {
      peopleNames.add(person.getName());
    }
    chosenRateables.add(rateableFromName(getChoice(peopleNames, "person"), true));
    chosenRateables.add(rateableFromName(getChoice(peopleNames, "person"), true));

    for (Rateable gameElement : gameElements) {
      gameElementNames.add(gameElement.getName());
    }
    chosenRateables.add(rateableFromName(getChoice(gameElementNames, "gameElements"), false));
    chosenRateables.add(rateableFromName(getChoice(gameElementNames, "gameElements"), false));

    return chosenRateables;
  }

  public Rateable rateableFromName(String name, boolean isPerson) throws Exception {
    if (isPerson) {
      for (Rateable person : people) {
        if (Objects.equals(person.getName(), name)) {
          return person;
        }
      }
    } else {
      for (Rateable gameElement : gameElements) {
        if (Objects.equals(gameElement.getName(), name)) {
          return gameElement;
        }
      }
    }
    throw new Exception("rateable not found with that name");
  }

  public void displayHandicap(ArrayList<Rateable> chosenRateables) {
    double ratingDifference = chosenRateables.get(0).getRating() - chosenRateables.get(1).getRating();
    ratingDifference += chosenRateables.get(2).getRating() - chosenRateables.get(3).getRating();

    int chains = (int) round(ratingDifference / 5);
    String chosenPlayer;
    if (chains >= 0) {
      chosenPlayer = chosenRateables.get(0).getName();
    } else {
      chosenPlayer = chosenRateables.get(1).getName();
    }
    chains = Math.abs(chains);
    System.out.println(chosenPlayer + " should play with " + chains + " chains.");
  }

  public void enterResult(ArrayList<Rateable> chosenRateables) {
    ArrayList<String> peopleNames = new ArrayList<>();
    peopleNames.add(chosenRateables.get(0).getName());
    peopleNames.add(chosenRateables.get(1).getName());

    System.out.println("Choose the winner.");
    String chosenPersonName = getChoice(peopleNames, "person");
    if (Objects.equals(peopleNames.get(0), chosenPersonName)) {
      chosenRateables.get(0).changeRating(true);
      chosenRateables.get(2).changeRating(true);
      chosenRateables.get(1).changeRating(false);
      chosenRateables.get(3).changeRating(false);
    } else if (Objects.equals(peopleNames.get(1), chosenPersonName)) {
      chosenRateables.get(0).changeRating(false);
      chosenRateables.get(2).changeRating(false);
      chosenRateables.get(1).changeRating(true);
      chosenRateables.get(3).changeRating(true);
    }
  }

  /**
   * Requests the user to pick a value from a given choice.
   *
   * @param list      A list of values to pick from.
   * @param attribute The type of values that are being picked from.
   * @return The chosen value.
   */
  public String getChoice(ArrayList<String> list, String attribute) {
    Scanner scanner = new Scanner(System.in);

    //removes duplicate values.
    list = new ArrayList<>(new HashSet<>(list));

    for (String element : list) {
      System.out.println((list.indexOf(element) + 1) + ". " + element);
    }

    String chosenElement = "";
    int enteredNumber = 0;
    while (true) {
      System.out.println("Enter the number of the " + attribute + " you would like to choose.");

      //Ensure the input is a number and is within the correct range, otherwise ask for input again.
      try {
        enteredNumber = parseInt(scanner.nextLine());
        chosenElement = list.get(enteredNumber - 1);
        break;
      } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
      }

    }

    return chosenElement;
  }
}
