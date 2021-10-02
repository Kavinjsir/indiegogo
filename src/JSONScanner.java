import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONScanner {
  public class DataFields {
    private String title;
    private Number fundsRaisedPercent;
    private String closeDate;

    public String getTitle() {
      return this.title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public Number getFundsRaisedPercent() {
      return this.fundsRaisedPercent;
    }

    public void setFundsRaisedPercent(Number fundsRaisedPercent) {
      this.fundsRaisedPercent = fundsRaisedPercent;
    }

    public String getCloseDate() {
      return this.closeDate;
    }

    public void setCloseDate(String closeDate) {
      this.closeDate = closeDate;
    }
  }

  public void searchKeyword(String keyword, String filePath) {
    List<DataFields> results = new ArrayList<DataFields>();
    // Scan through file, find lines containing keyword, fetch required fields.
    this.searchJSONFile(filePath, keyword, results);
    // Format Print JSON Array of results
    System.out.println(new JSONArray(results).toString(2));
  }

  private void searchJSONFile(String filePath, String keyword, List<DataFields> results) {
    try {
      // Create file reader to read data from source csv.
      File rawData = new File(filePath);
      // Create buffer to stream data to the file reader.
      BufferedReader br = new BufferedReader(new FileReader(rawData));
      // Convert the data from buffer to string.
      String st;
      while ((st = br.readLine()) != null) {
        if (st.contains(keyword)) {
          // Data streaming from source to the target.
          results.add(this.fetchDataFromJSON(st));
        }
      }
      // Ensure file is released when streaming finished.
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private DataFields fetchDataFromJSON(String jsonStr) {
    JSONObject dataObj = new JSONObject(jsonStr).getJSONObject("data");

    DataFields requiredFields = new DataFields();

    requiredFields.setTitle(dataObj.getString("title"));
    requiredFields.setFundsRaisedPercent(dataObj.getNumber("funds_raised_percent"));
    requiredFields.setCloseDate(dataObj.getString("close_date"));

    return requiredFields;
  }
}
