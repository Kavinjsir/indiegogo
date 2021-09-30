import java.util.Arrays;
import java.util.List;

public class Indiegogo {
  public static void main(String[] args) throws Exception {

    List<String> rawDataFilePath = Arrays.asList(
      "Indiegogo.csv",
      "Indiegogo001.csv",
      "Indiegogo002.csv"
    );

    String dataTargetPath = "output.csv";

    FileLoader fileLoader = new FileLoader(rawDataFilePath, dataTargetPath);

    fileLoader.mergeSourceDataIntoOneFile();

    String keyword = "Wine";

    Scanner dataScanner = new Scanner(dataTargetPath);
    List<Scanner.SearchResult> searchResults = dataScanner.findKeyword(keyword);
    searchResults.forEach(result -> dataScanner.printSearchResult(result));
  }
}
