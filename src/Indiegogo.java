import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Indiegogo {
  public static String getKeywordFromInput() {
    Scanner reader = new Scanner(System.in);  // Reading from System.in
    System.out.println("Please enter a keyword: ");
    String keyword = reader.next();
    reader.close();
    return keyword;
  }

  public static void main(String[] args) throws Exception {

    List<String> rawDataFilePath = Arrays.asList(
      "Indiegogo.csv",
      "Indiegogo001.csv",
      "Indiegogo002.csv"
    );

    String dataTargetPath = "output.csv";

    FileLoader fileLoader = new FileLoader(rawDataFilePath, dataTargetPath);

    fileLoader.mergeSourceDataIntoOneFile();

    String keyword = getKeywordFromInput();

    DataScanner dataScanner = new DataScanner(dataTargetPath);
    List<DataScanner.SearchResult> searchResults = dataScanner.findKeyword(keyword);
    searchResults.forEach(result -> dataScanner.printSearchResult(result));
  }
}
