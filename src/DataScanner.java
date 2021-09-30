import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class DataScanner {
  private String targetFilePath;

  private static int titleIdx = 23;
  private static int funds_raised_percent = 7;
  private static int close_date = 4;

  private Boolean containsKeyword(String[] csvLine, String keyword) {
    Boolean exists = false;
    for(String content: csvLine) {
      if (content.contains(keyword)) {
        exists = true;
        break;
      }
    }
    return exists;
  }

  private SearchResult createSearchResult(String[] csvLine) {
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
    List<SearchResult> searchResults = new ArrayList<SearchResult>();

    try {
      File f = new File(targetFilePath);
      CSVReader reader = new CSVReader(new FileReader(f));

      String[] nextLine;
      while( (nextLine = reader.readNext()) != null ) {
        if (!this.containsKeyword(nextLine, keyword)) {
          continue;
        }

        searchResults.add(this.createSearchResult(nextLine));
      }
      return searchResults;
    }
    catch (Exception e) {
      e.printStackTrace();
      return searchResults;
    }
  }

  public void printSearchResult(SearchResult result) {
    System.out.printf(
      "Title: %s, funds_raised_percent: %s, close_date: %s\n",
      result.title,
      result.fundsRaisedPercent,
      result.closeDate
    );
  }
}
