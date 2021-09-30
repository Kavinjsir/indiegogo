import java.util.Arrays;
import java.util.List;

public class Indiegogo {
  public static List<String> rawDataFilePath = Arrays.asList(
      "Indiegogo.csv",
      "Indiegogo001.csv",
      "Indiegogo002.csv"
    );

  public static String dataTargetPath = "output.csv";

  public static void main(String[] args) throws Exception {
    FileLoader fileLoader = new FileLoader(rawDataFilePath, dataTargetPath);
    KeywordSearcher searcher = new KeywordSearcher();
    DataScanner dataScanner = new DataScanner(dataTargetPath);

    // Task 1: Merge source files into one file.
    fileLoader.mergeSourceDataIntoOneFile();

    // Get keyword from user input.
    String keyword = searcher.getKeywordFromInput();

    // Get search record by the keyword
    KeywordSearcher.SearchRecord sr = searcher.getSearchRecord(keyword);
    // Print the frequency
    System.out.println(sr.frequency);

    // Search keyword in the single file
    List<DataScanner.SearchResult> searchResults = dataScanner.findKeyword(keyword);
    searchResults.forEach(result -> dataScanner.printSearchResult(result));

    // Update search record
    searcher.updateSearchRecord(keyword);
    sr = searcher.getSearchRecord(keyword);
    System.out.println(sr.frequency);
    System.out.println(sr.lastTimestamp);
  }
}
