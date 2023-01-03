import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 *
 */
public class RateableReader {

  public Rateable findRateable(File file, String rateableName, boolean isPerson) throws Exception {
    Rateable rateable;

    Scanner reader = new Scanner(file);

    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      ArrayList<String> attributes = new ArrayList<>(Arrays.asList(line.split(", ")));
      if (Objects.equals(attributes.get(0), rateableName)) {
        return new Rateable(attributes.get(0), parseInt(attributes.get(1)), parseInt(attributes.get(2)), isPerson);
      }
    }

    throw new Exception("Rateable not found.");
  }

  public ArrayList<Rateable> readRateables(File file, boolean isPerson) throws Exception {
    ArrayList<Rateable> rateables = new ArrayList<>();

    Scanner reader = new Scanner(file);

    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      ArrayList<String> attributes = new ArrayList<>(Arrays.asList(line.split(", ")));
      rateables.add(new Rateable(attributes.get(0), parseDouble(attributes.get(1)), parseInt(attributes.get(2)), isPerson));
    }

    return rateables;
  }

}
