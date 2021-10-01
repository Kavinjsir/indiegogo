import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class DataScanner {
  private String targetFilePath;

  // Define constant value to store index of the required attributes in csv.
  private static int titleIdx = 23;
  private static int funds_raised_percent = 7;
  private static int close_date = 4;

  private Boolean containsKeyword(String[] csvLine, String keyword) {
    Boolean exists = false;
    for(String content: csvLine) {
      if (content.contains(keyword)) {
        // Once an element contains the keyword, stop the loop, return true instantly.
        exists = true;
        break;
      }
    }
    return exists;
  }

  private SearchResult createSearchResult(String[] csvLine) {
    // Manally create the result object.
    // FIXME: Is there a better way to create object?
    // (Since this is more like a JSON object)
    SearchResult result = new SearchResult();
    result.title = csvLine[titleIdx];
    result.fundsRaisedPercent = csvLine[funds_raised_percent];
    result.closeDate = csvLine[close_date];
    return result;
  }

  public class SearchResult {
    String title;
    String fundsRaisedPercent;
    String closeDate;
  }

  public DataScanner(String path) {
    this.targetFilePath = path;
  }

  public List<SearchResult> findKeyword(String keyword) {
    // Store search results in a collection.
    List<SearchResult> searchResults = new ArrayList<SearchResult>();

    try {
      File f = new File(targetFilePath);
      // Create a CSV reader similar as buffer to load lines in csv.
      CSVReader reader = new CSVReader(new FileReader(f));
      // The CSV reader parses each line and split attributes into a string array.
      String[] nextLine;
      while( (nextLine = reader.readNext()) != null ) {
        // Check each element for a single csv line for the keyword.
        if (this.containsKeyword(nextLine, keyword)) {
          // If the csv line contains the keyword, store the required attributes.
          searchResults.add(this.createSearchResult(nextLine));
        }
      }
      return searchResults;
    }
    catch (Exception e) {
      // FIXME: What is the proper to handle errors here?
      // 1. Is it good to throw directly so as to drop the try/catch?
      // 2. If throwing error is not recommended, how we let the user know
      //    and avoid the program continue?
      e.printStackTrace();
      return searchResults;
    }
  }

  public void printSearchResult(SearchResult result) {
    System.out.printf(
      "[Indiegogo Searcher] [Search Result] Title: %s, funds_raised_percent: %s, close_date: %s\n",
      result.title,
      result.fundsRaisedPercent,
      result.closeDate
    );
  }
}
