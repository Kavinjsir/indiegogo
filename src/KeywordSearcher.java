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
    if (this.searchRecords.containsKey(keyword)) {
      return this.searchRecords.get(keyword);
    } else {
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
    Scanner reader = new Scanner(System.in);  // Reading from System.in
    System.out.println("Please enter a keyword: ");
    String keyword = reader.next();
    reader.close();
    return keyword;
  }
}
