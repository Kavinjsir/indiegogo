import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class KeywordSearcher {
  public class SearchRecord {
    Integer frequency;
    Date lastTimestamp;

    public SearchRecord(Integer frequency, Date lastTimestamp) {
      this.frequency = frequency;
      this.lastTimestamp = lastTimestamp;
    }
  }

  private HashMap<String, SearchRecord> searchRecords;

  public KeywordSearcher() {
    this.searchRecords = new HashMap<String, SearchRecord>();
  }

  public SearchRecord getSearchRecord(String keyword) {
    // FIXME: is the following code block a good way to
    // get key/value from a HashMap?
    if (this.searchRecords.containsKey(keyword)) {
      return this.searchRecords.get(keyword);
    } else {
      // Return a record with 0 frequent.
      // FIXME: is this a good way to give a result when
      // key is not in the HashMap?
      return new SearchRecord(0, new Date());
    }
  }

  public void updateSearchRecord(String keyword) {
    SearchRecord sr;
    if (this.searchRecords.containsKey(keyword)) {
      sr = this.searchRecords.get(keyword);
      sr.frequency = sr.frequency + 1;
    } else {
      sr = new SearchRecord(1, new Date());
    }

    this.searchRecords.put(keyword, sr);
  }

  public String getKeywordFromInput() {
    // Create a scanner to get data from standard input.
    Scanner reader = new Scanner(System.in);
    System.out.println("Please enter a keyword: ");
    // Get user input, set it as a string.
    String keyword = reader.next();
    // Close scanner, avoid unexpected data piping.
    reader.close();
    return keyword;
  }

  public void printSearchRecord(String keyword) {
    // Get keyword search record.
    SearchRecord sr = this.getSearchRecord(keyword);
    // Task 5: Print the record in the required format.
    System.out.printf("%s, %d times\n", keyword, sr.frequency);
  }
}
