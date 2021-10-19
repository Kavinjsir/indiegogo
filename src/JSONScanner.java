import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
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
    List<String> searchResult = new ArrayList<String>();
    try {
      // Create file reader to read data from source csv.
      File rawData = new File(filePath);
      // Create buffer to stream data to the file reader.
      BufferedReader br = new BufferedReader(new FileReader(rawData), 1024 * 8192);
      // Convert the data from buffer to string.
      String st;
      // Scan through file, find lines containing keyword, fetch required fields.
      Instant startV = Instant.now();
      while ((st = br.readLine()) != null) {
        if (st.contains(keyword)) {
          searchResult.add(st);

        }
      }
      Instant stopV = Instant.now();
      System.out.println("--Brute force--->" + Duration.between(startV, stopV).getNano());
      // Ensure file is released when streaming finished.
      br.close();


      // Display number of search results
      System.out.println("--Brute force--->" + searchResult.size() + " records");
      // Format print search result
      // this.displayResultInDetail(searchResult);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void displayResultInDetail(List<String> searchResult) {
      searchResult.forEach((String res) -> {
          // Data streaming from source to the target.
          DataFields dataFromFetch = this.fetchDataFromJSON(res);
          if (dataFromFetch != null) {
            // Format Print JSON Array of results
            System.out.println(new JSONObject(dataFromFetch).toString(2));
          }
      });
  }

  private DataFields fetchDataFromJSON(String jsonStr) {
    try {
      JSONObject dataObj = new JSONObject(jsonStr).getJSONObject("data");
      DataFields requiredFields = new DataFields();
      requiredFields.setTitle(dataObj.getString("title"));
      requiredFields.setFundsRaisedPercent(dataObj.getNumber("funds_raised_percent"));
      requiredFields.setCloseDate(dataObj.getString("close_date"));
      return requiredFields;
    } catch (JSONException e) {
      // FIXME: Avoid printing too much parsing error stacks.
      // e.printStackTrace();
      return null;
    }
  }
}
