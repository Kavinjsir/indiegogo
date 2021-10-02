import java.io.Console;
import java.util.Arrays;
import java.util.List;

public class Indiegogo {
  // FIXME: Set a global constant for store file paths to load.
  public static final List<String> RAW_DATA_FILE_PATH = Arrays.asList(
      "Indiegogo_2016.json",
      "Indiegogo_2017.json",
      "Indiegogo_2018.json",
      "Indiegogo_2019.json",
      "Indiegogo_2020.json",
      "Indiegogo_2021.json"
    );

  // FIXME: Set a global constant for file paths to output.
  public static final String DATA_TARGET_PATH = "output.json";

  public static final String EXIT_SELECTION = "q";
  public static final String SEARCH_SELECTION = "1";
  public static final String RECORD_SELECTION = "2";

  public static final String CLI_PREFIX = "[Indiegogo Searcher]";

  public static void displayMessageWithPrefix(String message) {
    System.out.printf("%s %s\n", CLI_PREFIX, message);
  }

  public static void displayInstruction() {
    System.out.printf("%s Select your choice: ", CLI_PREFIX);
    System.out.printf("'%s' for searching by keyword. ", SEARCH_SELECTION);
    System.out.printf("'%s' for check search records. ", RECORD_SELECTION);
    System.out.printf("'%s' for exit.\n", EXIT_SELECTION);
  }

  public static void main(String[] args) throws Exception {
    FileLoader fileLoader = new FileLoader(RAW_DATA_FILE_PATH, DATA_TARGET_PATH);
    KeywordSearcher searcher = new KeywordSearcher();
    JSONScanner jScaner = new JSONScanner();

    // Task 1: Merge source files into one file.
    displayMessageWithPrefix("Loading data...");
    fileLoader.mergeSourceDataIntoOneFile();
    displayMessageWithPrefix("Data loaded!");

    // Create a scanner to get data from standard input.
    Console console = System.console();

    // Define a string to store user selection.
    String selection;
    // Define a string to store user keyword.
    String keyword;

    // Display landing msg.
    displayMessageWithPrefix("Welcome!");

    while (true) {
      // Display instructions
      displayInstruction();

      selection = console.readLine().trim();

      // Case exit
      if (selection.equals(EXIT_SELECTION)) {
        displayMessageWithPrefix("See you again!");
        return;
      }

      // Case search keyword
      if (selection.equals(SEARCH_SELECTION)) {
        // Get keyword from user input.
        keyword = console.readLine(CLI_PREFIX + " Please input a keyword: ");
        // Search keyword in the single file
        jScaner.searchKeyword(keyword, DATA_TARGET_PATH);
        // Update search record
        searcher.updateSearchRecord(keyword);
      }
      // Case check search record
      else if ( selection.equals(RECORD_SELECTION)) {
        // Get keyword from user input.
        keyword = console.readLine(CLI_PREFIX + " Please input a keyword: ");
        // Print search record by the keyword
        searcher.printSearchRecord(keyword);
      }
      // Case wrong input
      else {
        displayMessageWithPrefix("Please input valid value for choice.");
      }
    }
  }
}
