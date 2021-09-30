import java.util.Arrays;
import java.util.List;

public class Indiegogo {
  // FIXME: Set a global constant for store file paths to load.
  public static List<String> rawDataFilePath = Arrays.asList(
      "Indiegogo.csv",
      "Indiegogo001.csv",
      "Indiegogo002.csv"
    );

  // FIXME: Set a global constant for file paths to output.
  public static String dataTargetPath = "output.csv";

  public static void main(String[] args) throws Exception {
    FileLoader fileLoader = new FileLoader(rawDataFilePath, dataTargetPath);
    KeywordSearcher searcher = new KeywordSearcher();
    DataScanner dataScanner = new DataScanner(dataTargetPath);

    // Task 1: Merge source files into one file.
    fileLoader.mergeSourceDataIntoOneFile();

    // Get keyword from user input.
    String keyword = searcher.getKeywordFromInput();

    // Print search record by the keyword
    searcher.printSearchRecord(keyword);

    // Search keyword in the single file
    List<DataScanner.SearchResult> searchResults = dataScanner.findKeyword(keyword);
    searchResults.forEach(result -> dataScanner.printSearchResult(result));

    // Update search record
    searcher.updateSearchRecord(keyword);
    searcher.printSearchRecord(keyword);
  }
}
