import java.io.File;
import java.io.FileReader;

import com.opencsv.CSVReader;

public class Scanner {
  public static void main(String[] args) throws Exception {
    String keyword = "German";

    String targetFilePath = "output.csv";

    File f = new File(targetFilePath);

    CSVReader reader = new CSVReader(new FileReader(f));

    String[] nextLine;
    while( (nextLine = reader.readNext()) != null ) {

      Boolean containsKeyword = false;
      for(String content: nextLine) {
        if (content.contains(keyword)) {
          containsKeyword = true;
          break;
        }
      }

      if (containsKeyword) {
        System.out.printf(
          "Title: %s, funds_raised_percent: %s, close_date: %s\n",
          nextLine[23],
          nextLine[7],
          nextLine[4]
        );
      }
    }
  }
}
