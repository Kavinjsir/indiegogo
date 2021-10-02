import java.util.Date;
import java.util.HashMap;

public class KeywordSearcher {
  public static class SearchRecord {
    Integer frequency;
    Date lastTimestamp;

    public SearchRecord(Integer frequency, Date lastTimestamp) {
      this.frequency = frequency;
      this.lastTimestamp = lastTimestamp;
    }
  }

  private final HashMap<String, SearchRecord> searchRecords;

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

  public void printSearchRecord(String keyword) {
    // Get keyword search record.
    SearchRecord sr = this.getSearchRecord(keyword);
    // Task 5: Print the record in the required format.
    System.out.printf("[Indiegogo Searcher] [Keyword Record] [%s, %d times]\n",  keyword, sr.frequency);
  }
}
