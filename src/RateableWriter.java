import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * Allows for reading of a rateable file.
 */
public class RateableWriter {

  /**
   * Asks the user for information for a rateable and then adds it to the relevant file.
   *
   * @param peopleFile The file of people which may have a person added to.
   * @param gameElementsFile The file of gameElements which may have a gameElement added to.
   */
  public void addRateables(File peopleFile, File gameElementsFile) throws Exception {
    while (true) {
      Scanner scanner = new Scanner(System.in);

      System.out.println("Is the rateable a person?(Y/N)(or enter nothing to play a game)");
      String input = scanner.nextLine();
      input = input.toLowerCase();
      boolean isPerson;
      if (input.equals("y")) {
        isPerson = true;
      } else if (input.equals("n")) {
        isPerson = false;
      } else {
        break;
      }

      System.out.println("Enter the name of the rateable(no commas please)");
      String name = scanner.nextLine();

      double rating;
      if (isPerson) {
        System.out.println("Enter the rating of the rateable");
        rating = parseInt(scanner.nextLine());
      } else {
        //A rating given to KeyForge decks by an online algorithm that acts as a good starting point for rating.
        System.out.println("Enter the SAS score of the deck");
        int sas = parseInt(scanner.nextLine());
        rating = 100 + 2 * (sas - 64);
      }

      Rateable rateable = new Rateable(name, rating, isPerson);

      if (isPerson) {
        writeRateable(peopleFile, rateable);
      } else {
        writeRateable(gameElementsFile, rateable);
      }
    }
  }

  /**
   * Adds one additional line to a rateable file.
   *
   * @param writer Used to write the line given to the file.
   * @param rateable The rateable which will have information about it written to the file.
   */
  public void writeRateableLine(FileWriter writer, Rateable rateable) throws IOException {
    writer.write(rateable.getName() + ", " + rateable.getRating() + ", " + rateable.getPlayedGames() + "\n");
  }

  /**
   * @param file     The file to be written to.
   * @param rateable The rateable to be written to the file.
   */
  public void writeRateable(File file, Rateable rateable) throws IOException {
    file.createNewFile();
    FileWriter writer = new FileWriter(file, true);
    writeRateableLine(writer, rateable);
    writer.close();
  }

  /**
   * Replaces a stored file with all the rateables given by the program.
   *
   * @param rateables The rateables to be written to the file.
   * @param file The rateable file to be written to.
   */
  public void updateRateables(ArrayList<Rateable> rateables, File file) throws IOException {
    //Deletes the contents of the file.
    PrintWriter printWriter = new PrintWriter(file);
    printWriter.close();

    FileWriter writer = new FileWriter(file);
    for (Rateable rateable : rateables) {
      writeRateableLine(writer, rateable);
    }
    writer.close();
  }
}
